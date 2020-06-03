package com.course.ymx.jwt.vo.entity;

/**
 * @author yinminxin
 * @description
 * @date 2020/6/3 15:32
 */
public class UserRoleVo {

    private String userId;

    private String userName;

    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserRoleVo(String userId, String userName, String roleId) {
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleId;
    }

    public UserRoleVo() {
    }
}
