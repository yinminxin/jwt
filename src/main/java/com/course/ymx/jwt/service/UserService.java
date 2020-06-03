package com.course.ymx.jwt.service;

import com.course.ymx.jwt.entity.User;

/**
 * @author yinminxin
 * @description
 * @date 2020/6/3 14:57
 */
public interface UserService {

    User findByUserNameAndPassWord(String userName, String passWord);
}
