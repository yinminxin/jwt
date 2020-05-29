package com.course.ymx.jwt.controller;

import com.course.ymx.jwt.aop.LogInfo;
import com.course.ymx.jwt.common.base.BaseController;
import com.course.ymx.jwt.common.result.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinminxin
 * @description
 * @date 2020/5/27 15:48
 */
@RestController
@RequestMapping("/aop")
@LogInfo(info = "testAop类信息!")
public class AopController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopController.class);

    @GetMapping("/testAop")
    @LogInfo(info = "testAop方法信息!")
    public ResponseVO testAop(){
        int i=1/0;
        LOGGER.info("testAop,开始ing...");
        return getSuccess();
    }
}
