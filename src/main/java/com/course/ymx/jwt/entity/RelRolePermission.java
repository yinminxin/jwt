package com.course.ymx.jwt.entity;

import com.course.ymx.jwt.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yinminxin
 * @description 角色权限表
 * @date 2020/5/29 16:48
 */
@Entity
@Table(name = "rel_role_permission")
public class RelRolePermission extends BaseEntity {

    @Column(name = "role_id", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '角色ID'")
    private String roleId;

    @Column(name = "permission_id", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '权限ID'")
    private String permissionId;
}
