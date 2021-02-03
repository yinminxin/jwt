package com.course.ymx.jwt.aop.security;

import java.lang.annotation.*;

/**
* @Description: 自定义重放攻击注解
* @Author: yinminxin
* @Date: 2021/2/1
*/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReplayAttack {

    /**
     * 用户连续访问最高阀值，超过该值则认定为恶意操作的IP，进行限制
     */
    int threshold() default 5;

    /**
     * 用户访问最小安全时间(秒)，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
     */
    int time() default 5;
}
