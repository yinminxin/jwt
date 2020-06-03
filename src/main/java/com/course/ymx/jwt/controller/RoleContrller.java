package com.course.ymx.jwt.controller;

import com.course.ymx.jwt.common.base.BaseController;
import com.course.ymx.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinminxin
 * @description 角色Controller
 * @date 2020/5/29 17:06
 */
@RestController
@RequestMapping("role")
public class RoleContrller extends BaseController {

    @Autowired
    private RoleService roleService;
}
