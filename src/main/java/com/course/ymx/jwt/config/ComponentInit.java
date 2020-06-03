package com.course.ymx.jwt.config;

import com.course.ymx.jwt.common.constant.RedisTag;
import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.utils.RedisUtils;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import com.course.ymx.jwt.vo.entity.RolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yinminxin
 * @description 初始化
 * @date 2020/5/29 16:57
 */
@Component
public class ComponentInit {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * @Description: 初始化角色权限信息到redis缓存中
     * @Author: yinminxin
     * @Date: 2020/5/29 17:00
     */
    @PostConstruct
    public void initPermissionToRedis(){
        List<RolePermissionListVo> rolePermissionListVo = roleRepository.findRolePermissionListVo();
        System.out.println(rolePermissionListVo);
        List<RolePermissionVo> result = rolePermissionListVo
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
        System.out.println(result);
        redisUtils.set(RedisTag.ROLE_PERMISSION,result);
        List<RolePermissionVo> vo = (List<RolePermissionVo>) redisUtils.get(RedisTag.ROLE_PERMISSION);
        System.out.println(vo);
    }

}
