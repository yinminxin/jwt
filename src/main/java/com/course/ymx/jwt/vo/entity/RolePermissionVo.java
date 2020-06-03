package com.course.ymx.jwt.vo.entity;

import java.util.List;

/**
 * @author yinminxin
 * @description 角色权限VO
 * @date 2020/5/29 17:02
 */
public class RolePermissionVo {

    //角色ID
    private String roleId;
    //权限列表
    private List<String> permissionUrl;

    public RolePermissionVo(String roleId, List<String> permissionUrl) {
        this.roleId = roleId;
        this.permissionUrl = permissionUrl;
    }

    public RolePermissionVo() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(List<String> permissionUrl) {
        this.permissionUrl = permissionUrl;
    }
}
