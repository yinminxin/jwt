package com.course.ymx.jwt.aop.security;

import com.course.ymx.jwt.aop.LogInfo;
import com.course.ymx.jwt.common.constant.RedisTag;
import com.course.ymx.jwt.common.exception.DefaultException;
import com.course.ymx.jwt.common.result.ResultCode;
import com.course.ymx.jwt.vo.entity.IpLoggerVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static sun.plugin.util.ProgressMonitor.get;

/**
 * @author yinminxin
 * @description AOP配置类
 * @date 2020/5/27 16:02
 */
@Aspect
@Component
public class AopReplayAttack {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopReplayAttack.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 切点-针对类
     */
//    @Pointcut("execution(public * com.course.ymx.jwt.controller.*.*(..))")
    @Pointcut("@within(com.course.ymx.jwt.aop.security.ReplayAttack)")
    public void classPointCut(){
    }

    /**
     * 切点-针对方法
     */
    @Pointcut("@annotation(com.course.ymx.jwt.aop.security.ReplayAttack)")
    public void methodPointCut() {
    }

    /**
     * 前置方法(当类或者方法上有《ReplayAttack》注解时，表示存在重放攻击)
     */
    @Before(value = "classPointCut() || methodPointCut()", argNames = "joinPoint")
    public void doMoreMethodBefore(JoinPoint joinPoint) {
        LOGGER.info("单独前置方法Before******************进入Aop方法业务之前！");

        ReplayAttack replayAttack = hasReplayAttackAnnotation(joinPoint);

        // 获取request请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取IP和请求地址
        String ip = request.getRemoteHost();
        StringBuffer url = request.getRequestURL();

        LOGGER.info(url + "====>接口为重放攻击接口");
        // 有注解,获取注解参数
        // 用户连续访问最高阀值，超过该值则认定为恶意操作的IP，进行限制
        int threshold = replayAttack.threshold();
        // 用户访问最小安全时间(秒)，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
        int time = replayAttack.time();

        // 判断用户是否在黑名单
        if (redisTemplate.hasKey(RedisTag.IP_FORBIDDEN_LIST + ip)) {
            // 在黑名单
            LOGGER.info("当前IP访问过于频繁已被限制;IP ===> " + ip + " | 剩余时间(单位秒) ===> " + redisTemplate.getExpire(RedisTag.IP_FORBIDDEN_LIST + ip));
            throw new DefaultException(ResultCode.FORBIDDEN);
        }
        // 不在黑名单，判断IP访问是否达到限制
        // 获取当前IP日志对象
        List<IpLoggerVo> voList = (List<IpLoggerVo>) redisTemplate.opsForHash().get(RedisTag.IP_LOGGER, ip);
        if (voList !=null) {
            Optional<IpLoggerVo> first = voList.stream().filter(v -> url.toString().equals(v.getUrl())).findFirst();
            if (first.isPresent()) {
                IpLoggerVo vo = first.get();
                List<LocalDateTime> voTime = vo.getTime();
                if (voTime.size() >= threshold && voTime.size() >= threshold && Duration.between(voTime.get(voTime.size() - threshold), LocalDateTime.now()).toMillis() <= time * 1000) {
                    // 当前接口访问次数达到阈值且时间低于最小安全时间, IP加入黑名单
                    redisTemplate.opsForValue().set(RedisTag.IP_FORBIDDEN_LIST + ip, 1, 60, TimeUnit.MINUTES);

                    LOGGER.info("当前IP访问过于频繁已被限制;IP ===> " + ip + " | 剩余时间(单位秒) ===> " + redisTemplate.getExpire(RedisTag.IP_FORBIDDEN_LIST + ip));
                    throw new DefaultException(ResultCode.FORBIDDEN);
                }else {
                    LOGGER.info("当前IP第" + voTime.size() + "次访问此接口：IP ===> " + ip + " | 接口 ===> " + url);
                    voTime.add(LocalDateTime.now());
//                    vo.setTime(voTime);
                }
            }else {
                LOGGER.info("当前IP第一次访问此接口：IP ===> " + ip + " | 接口 ===> " + url);
                List<LocalDateTime> localDateTimes = new ArrayList<>();
                localDateTimes.add(LocalDateTime.now());
                IpLoggerVo ipLoggerVo = new IpLoggerVo(url.toString(), localDateTimes);
                voList.add(ipLoggerVo);
            }

        }else {
            LOGGER.info("当前IP第一次访问服务：IP ===> " + ip);
            voList = new ArrayList<>();
            List<LocalDateTime> localDateTimes = new ArrayList<>();
            localDateTimes.add(LocalDateTime.now());
            IpLoggerVo ipLoggerVo = new IpLoggerVo(url.toString(), localDateTimes);
            voList.add(ipLoggerVo);
        }
        redisTemplate.opsForHash().put(RedisTag.IP_LOGGER, ip, voList);
    }

//    /**
//     * 切点-针对Contrller
//     */
//    @Pointcut("execution(public * com.course.ymx.jwt.controller.*.*(..))")
//    public void controllerPointCut(){
//
//    }

    /**
     * 前置方法
     * @param joinPoint
     */
//    @Before(value = "controllerPointCut())")
    public void doMethodBefore(JoinPoint joinPoint) {
        LOGGER.info("单独前置方法Before******************进入Aop方法业务之前！");
        ReplayAttack annotatiaon = hasReplayAttackAnnotation(joinPoint);
        if (annotatiaon != null) {
            // 获取request请求对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 获取IP和请求地址
            String ip = request.getRemoteHost();
            StringBuffer url = request.getRequestURL();

            LOGGER.info(url + "====>接口为重放攻击接口");
            // 有注解,获取注解参数
            // 用户连续访问最高阀值，超过该值则认定为恶意操作的IP，进行限制
            int threshold = annotatiaon.threshold();
            // 用户访问最小安全时间(秒)，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
            int time = annotatiaon.time();

//            // 设置黑名单IP
//            redisTemplate.opsForValue().set(RedisTag.IP_FORBIDDEN_LIST + ip, 1, 60, TimeUnit.MINUTES);

            // 判断用户是否在黑名单
            if (redisTemplate.hasKey(RedisTag.IP_FORBIDDEN_LIST + ip)) {
                // 在黑名单
                LOGGER.info("当前IP访问过于频繁已被限制;IP ===> " + ip + " | 剩余时间(单位秒) ===> " + redisTemplate.getExpire(RedisTag.IP_FORBIDDEN_LIST + ip));
                throw new DefaultException(ResultCode.FORBIDDEN);
            }
            // 不在黑名单，判断IP访问是否达到限制
//        Map<String, Long[]> map = new HashMap<>();
            // 获取当前IP日志对象
            List<IpLoggerVo> voList = (List<IpLoggerVo>) redisTemplate.opsForHash().get(RedisTag.IP_LOGGER, ip);
            if (voList !=null) {
                IpLoggerVo vo = voList.stream().filter(v -> url.toString().equals(v.getUrl())).findFirst().get();
                if (vo != null) {
                    List<LocalDateTime> voTime = vo.getTime();
                    if (voTime.size() >= threshold && Duration.between(voTime.get(voTime.size() - 50), LocalDateTime.now()).toMillis() <= time * 1000) {
                        // 当前接口访问次数达到阈值且时间低于最小安全时间, IP加入黑名单
                        redisTemplate.opsForValue().set(RedisTag.IP_FORBIDDEN_LIST + ip, 1, 60, TimeUnit.MINUTES);

                        LOGGER.info("当前IP访问过于频繁已被限制;IP ===> " + ip + " | 剩余时间(单位秒) ===> " + redisTemplate.getExpire(RedisTag.IP_FORBIDDEN_LIST + ip));
                        throw new DefaultException(ResultCode.FORBIDDEN);
                    }else {
                        LOGGER.info("当前IP第" + voTime.size() + "次访问此接口：IP ===> " + ip + " | 接口 ===> " + url);
                        voTime.add(LocalDateTime.now());
//                    vo.setTime(voTime);
                        redisTemplate.opsForHash().put(RedisTag.IP_LOGGER, ip, voList);
                    }
                }else {
                    LOGGER.info("当前IP第一次访问此接口：IP ===> " + ip + " | 接口 ===> " + url);
                    List<LocalDateTime> localDateTimes = new ArrayList<>();
                    localDateTimes.add(LocalDateTime.now());
                    IpLoggerVo ipLoggerVo = new IpLoggerVo(url.toString(), localDateTimes);
                    voList.add(ipLoggerVo);
                    redisTemplate.opsForHash().put(RedisTag.IP_LOGGER, ip, voList);
                }

            }else {
                LOGGER.info("当前IP第一次访问服务器：IP ===> " + ip);
                List<IpLoggerVo> vos = new ArrayList<>();
                List<LocalDateTime> localDateTimes = new ArrayList<>();
                localDateTimes.add(LocalDateTime.now());
                IpLoggerVo ipLoggerVo = new IpLoggerVo(url.toString(), localDateTimes);
                vos.add(ipLoggerVo);
                redisTemplate.opsForHash().put(RedisTag.IP_LOGGER, ip, vos);
            }
        }
    }

    /**
     * 获取本次请求重放攻击<ReplayAttack>注解信息
     * @param joinPoint
     * @return
     */
    private ReplayAttack hasReplayAttackAnnotation(JoinPoint joinPoint) {
        //获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取方法上是否有<ReplayAttack>注解
        ReplayAttack annotatiaon = method.getAnnotation(ReplayAttack.class);
        if (annotatiaon == null) {
            //方法上没有<ReplayAttack>注解，获取类上是否有<ReplayAttack>注解
            annotatiaon = joinPoint.getTarget().getClass().getAnnotation(ReplayAttack.class);
            if (annotatiaon == null) {
                // 类上没有<ReplayAttack>注解
                // 获取接口上是否有<ReplayAttack>注解
                for (Class<?> cla : joinPoint.getTarget().getClass().getInterfaces()) {
                    annotatiaon = cla.getAnnotation(ReplayAttack.class);
                }
            }
        }
        return annotatiaon;
    }

//    public static void main(String[] args) {
//        List<IpLoggerVo> voList = new ArrayList<>();
//        List<LocalDateTime> times = new ArrayList<>();
//        times.add(LocalDateTime.now());
//        voList.add(new IpLoggerVo("url", times));
//
//        IpLoggerVo vo = voList.stream().filter(v -> "url".toString().equals(v.getUrl())).findFirst().get();
//        if (vo != null) {
//            List<LocalDateTime> voTime = vo.getTime();
//            LOGGER.info("当前IP第" + voTime.size() + "次访问此接口：IP ===> " + "ip" + " | 接口 ===> " + "url");
//            voTime.add(LocalDateTime.now());
//            System.out.println("11111");
//        }
//    }

}
