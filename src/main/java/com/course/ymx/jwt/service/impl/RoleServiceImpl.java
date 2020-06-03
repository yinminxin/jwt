package com.course.ymx.jwt.service.impl;

import com.course.ymx.jwt.entity.RelRolePermission;
import com.course.ymx.jwt.entity.Role;
import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.service.RoleService;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinminxin
 * @description 角色业务层
 * @date 2020/5/29 17:07
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RolePermissionListVo> findRolePermissionListVo(){
        List<RolePermissionListVo> rolePermissionListVo = roleRepository.findRolePermissionListVo();
        return rolePermissionListVo;
    }

}
