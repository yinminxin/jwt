package com.course.ymx.jwt.service.util;

import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import com.course.ymx.jwt.vo.entity.RolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 权限工具类接口
 * @author: yinminxin
 * @create: 2021-02-01
 **/
@Component
public class PermissionUtilService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 获取角色权限
     * @return
     */
    public List<RolePermissionVo> findRolePermissionVoList(){
        List<RolePermissionListVo> rolePermissionListVo = roleRepository.findRolePermissionListVo();
        return rolePermissionListVo
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
//        System.out.println(voList);
    }
}
