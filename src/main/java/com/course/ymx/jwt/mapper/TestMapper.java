package com.course.ymx.jwt.mapper;

import com.course.ymx.jwt.common.base.MyMapper;
import com.course.ymx.jwt.entity.Test;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper extends MyMapper<Test,String> {
}
