package com.course.ymx.jwt.config;

import com.course.ymx.jwt.common.constant.RedisTag;
import com.course.ymx.jwt.common.exception.DefaultException;
import com.course.ymx.jwt.common.result.ResultCode;
import com.course.ymx.jwt.repository.RoleRepository;
import com.course.ymx.jwt.service.util.PermissionUtilService;
import com.course.ymx.jwt.utils.RedisUtils;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import com.course.ymx.jwt.vo.entity.RolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yinminxin
 * @description 初始化
 * @date 2020/5/29 16:57
 */
//@Component
public class ComponentInit {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PermissionUtilService permissionUtilService;

    /**
     * @Description: 初始化角色权限信息到redis缓存中
     * @Author: yinminxin
     * @Date: 2020/5/29 17:00
     */
    @PostConstruct
    public void initPermissionToRedis(){
        List<RolePermissionVo> result = permissionUtilService.findRolePermissionVoList();
        redisUtils.set(RedisTag.ROLE_PERMISSION,result);
//        List<RolePermissionVo> vo = (List<RolePermissionVo>) redisUtils.get(RedisTag.ROLE_PERMISSION);
//        System.out.println(vo);
    }

}
