package com.course.ymx.jwt.vo.entity;

/**
 * @author yinminxin
 * @description 角色权限列表
 * @date 2020/5/29 17:11
 */
public class RolePermissionListVo{



    //角色Id
    private String roleId;

    //权限路径
    private String interfaceUrl;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    public RolePermissionListVo() {
    }

    public RolePermissionListVo(String roleId, String interfaceUrl) {
        this.roleId = roleId;
        this.interfaceUrl = interfaceUrl;
    }
}
