package com.course.ymx.jwt.service;

import com.course.ymx.jwt.entity.Role;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;

import java.util.List;

/**
 * @author yinminxin
 * @description 角色Service
 * @date 2020/5/29 17:07
 */
public interface RoleService {

    List<RolePermissionListVo> findRolePermissionListVo();

}
