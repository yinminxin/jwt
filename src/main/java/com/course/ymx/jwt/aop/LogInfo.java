package com.course.ymx.jwt.aop;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LogInfo {

    //方法说明
    String info();
}
