package com.course.ymx.jwt.repository;

import com.course.ymx.jwt.entity.RelUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yinminxin
 * @description
 * @date 2020/6/3 15:38
 */
@Repository
public interface RelUserRoleRepository extends JpaRepository<RelUserRole,String>, JpaSpecificationExecutor<RelUserRole> {

    @Query(value = "SELECT role_id FROM rel_user_role WHERE state = 0 AND user_id = :id",nativeQuery = true)
    List<String> findRoleIdByUserId(@Param("id") String id);
}
