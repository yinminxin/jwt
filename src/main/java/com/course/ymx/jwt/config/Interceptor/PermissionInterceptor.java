package com.course.ymx.jwt.config.Interceptor;

import com.course.ymx.jwt.common.constant.RedisTag;
import com.course.ymx.jwt.config.properties.Cors;
import com.course.ymx.jwt.config.properties.JwtProperties;
import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.utils.JwtUtils;
import com.course.ymx.jwt.utils.RedisUtils;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import com.course.ymx.jwt.vo.entity.RolePermissionVo;
import com.course.ymx.jwt.vo.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yinminxin
 * @description 权限拦截器
 * @date 2020/6/3 14:39
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JwtProperties jwt;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);
        //自动排除生成token的路径,并且如果是options请求是cors跨域预请求，设置allow对应头信息
        if(RequestMethod.OPTIONS.toString().equals(request.getMethod())){
            return true;
        }
        if(requestURL.toString().toUpperCase().contains("/ERROR")){
            return true;
        }

        //设置allow对应头信息
        this.allowedOrigins(request,response);

        //从请求的头字段中拿到token信息
        String token = request.getHeader("Authorization");

        //拦截请求判断cookie中的token是否通过
//        String token = CookieUtils.getCookieValue(request, jwt.getCookieName());
        if (StringUtils.isNotBlank(token) && redisUtils.hasKey(token) && redisUtils.getExpire(token) >= 0) {
            //token不为空,判断redis中token对应的value是否存在,判断redis中的用户数据是否过期
            //刷新token
            redisUtils.expire(token, 60L * 30);
            //获取当前登陆用户的角色
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, jwt.getPublicKey());
            List<String> roleIds = infoFromToken.getRoleId();
            //获取角色对应接口权限
            List<RolePermissionVo> vo;
            if (redisUtils.hasKey(RedisTag.ROLE_PERMISSION)) {
                vo = (List<RolePermissionVo>) redisUtils.get(RedisTag.ROLE_PERMISSION);
            }else{
                List<RolePermissionListVo> rolePermissionListVo = roleRepository.findRolePermissionListVo();
                vo = rolePermissionListVo
                        .stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors
                                .groupingBy(RolePermissionListVo::getRoleId))
                        .entrySet()
                        .stream()
                        .map(v1 -> {
                            RolePermissionVo rolePermissionVo = new RolePermissionVo();
                            String key = v1.getKey();
                            rolePermissionVo.setRoleId(key);
                            List<String> collect1 = v1.getValue().stream().filter(Objects::nonNull).map(v2 -> {
                                return v2.getInterfaceUrl();
                            }).collect(Collectors.toList());
                            rolePermissionVo.setPermissionUrl(collect1);
                            return rolePermissionVo;
                        }).collect(Collectors.toList());
                //TODO 结果
                System.out.println(vo);
                redisUtils.set(RedisTag.ROLE_PERMISSION,vo);
            }

            for (String roleId : roleIds) {
                for (RolePermissionVo permissionVo : vo) {
                    if (roleId.equals(permissionVo.getRoleId())) {
                        for (String url : permissionVo.getPermissionUrl()) {
                            if (requestURL.toString().contains(url)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        throw new Exception(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
    }

    /**
     * 处理跨域问题
     */
    private void allowedOrigins(HttpServletRequest request, HttpServletResponse response) {

        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        Cors cors = ctx.getBean(Cors.class);

        String[] allowDomains = cors.getAllowedOrigins();
        Set allowOrigins = new HashSet(Arrays.asList(allowDomains));
        String originHeads = request.getHeader("Origin");
        if(allowOrigins.contains(originHeads)){
            //设置允许跨域的配置
            // 这里填写你允许进行跨域的主机ip（生产环境可以动态配置具体允许的域名和IP）
            response.setHeader("Access-Control-Allow-Origin", originHeads);
            /*response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cookie");
            response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");*/
        }else if(allowOrigins.contains("*")){
            //设置允许跨域的配置
            // dev环境设置*
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
    }
}
