package com.course.ymx.jwt.controller;

import com.course.ymx.jwt.aop.LogInfo;
import com.course.ymx.jwt.aop.security.ReplayAttack;
import com.course.ymx.jwt.common.base.BaseController;
import com.course.ymx.jwt.common.result.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author yinminxin
 * @description
 * @date 2020/5/27 15:48
 */
@RestController
@RequestMapping("/aop")
//@ReplayAttack(threshold = 10,time = 9)
//@LogInfo(info = "testAop类信息!")
public class AopController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopController.class);

    @GetMapping("/testAop")
    @LogInfo(info = "testAop方法信息!")
    public ResponseVO testAop(){
//        int i=1/0;
        LOGGER.info("testAop,开始ing...");
        return getSuccess();
    }

    @GetMapping("/testAttack")
    @ReplayAttack(threshold = 8,time = 7)
    public ResponseVO testAttack(){
//        int i=1/0;
        LOGGER.info("testAttack,开始ing...");
        return getSuccess();
    }

    @GetMapping("/testNotAttack")
    public ResponseVO testNotAttack(){
//        int i=1/0;
        LOGGER.info("testNotAttack,开始ing...");
        return getSuccess();
    }


}
