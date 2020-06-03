package com.course.ymx.jwt.entity;

import com.course.ymx.jwt.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author yinminxin
 * @description 权限表
 * @date 2020/5/29 16:46
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Column(name = "name", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '权限名称'")
    private String name;
    @Column(name = "interface_url", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '接口路径'")
    private String interfaceUrl;
}
