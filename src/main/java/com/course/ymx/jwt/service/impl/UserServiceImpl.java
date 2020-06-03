package com.course.ymx.jwt.service.impl;

import com.course.ymx.jwt.entity.RelUserRole;
import com.course.ymx.jwt.entity.User;
import com.course.ymx.jwt.repository.UserRepository;
import com.course.ymx.jwt.service.UserService;
import com.course.ymx.jwt.vo.entity.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinminxin
 * @description
 * @date 2020/6/3 15:03
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelUserRoleRepository relUserRoleRepository;

    @Override
    public User findByUserNameAndPassWord(String userName, String passWord) {
        User u = userRepository.findByUserNameAndPassWord(userName, passWord);
        String id = u.getId();
        List<String> roleIds = relUserRoleRepository.findRoleIdByUserId(id);
        u.setRoleId(roleIds);
        return u;
    }
}
