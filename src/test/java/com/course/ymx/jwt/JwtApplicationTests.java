package com.course.ymx.jwt;

import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.service.RoleService;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void contextLoads() {
//        System.out.println("test");
        List<RolePermissionListVo> rolePermissionListVo = roleRepository.findRolePermissionListVo();
        System.out.println(rolePermissionListVo);
    }

}
