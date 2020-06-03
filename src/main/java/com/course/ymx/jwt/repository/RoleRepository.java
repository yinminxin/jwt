package com.course.ymx.jwt.repository;

import com.course.ymx.jwt.entity.Role;
import com.course.ymx.jwt.vo.entity.RolePermissionListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yinminxin
 * @description 角色数据层
 * @date 2020/5/29 17:08
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {

    @Query(value = "SELECT new com.course.ymx.jwt.vo.entity.RolePermissionListVo(r.id, p.interfaceUrl)  " +
            "FROM Role r  " +
            "LEFT JOIN RelRolePermission rrp ON rrp.roleId = r.id  " +
            "LEFT JOIN Permission p ON rrp.permissionId = p.id  " +
            "WHERE rrp.state = 0  " +
            "AND p.state = 0  " +
            "AND r.state = 0")
    List<RolePermissionListVo> findRolePermissionListVo();

}
