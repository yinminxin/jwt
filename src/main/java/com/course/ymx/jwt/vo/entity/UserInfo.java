package com.course.ymx.jwt.vo.entity;

import java.util.List;

public class UserInfo {
    private String id;

    private String userName;

    private List<String> roleId;

    public List<String> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<String> roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo() {
    }

    public UserInfo(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public UserInfo(String id, String userName, List<String> roleId) {
        this.id = id;
        this.userName = userName;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
