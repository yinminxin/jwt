package com.course.ymx.jwt.repository;

import com.course.ymx.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author yinminxin
 * @description
 * @date 2020/6/3 15:05
 */
@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    @Query(value = "SELECT * FROM user u WHERE u.state = 0 AND user_name = :userName AND pass_word = :passWord",nativeQuery = true)
    User findByUserNameAndPassWord(@Param("userName") String userName,@Param("passWord")  String passWord);
}
