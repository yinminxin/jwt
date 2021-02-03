package com.course.ymx.jwt.common.exception;

import com.course.ymx.jwt.common.result.ResponseVO;
import com.course.ymx.jwt.common.result.ResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局404/500异常捕捉处理工具
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseVO defaultErrorHandler(Exception e) {
        if(e instanceof DefaultException){
            DefaultException dex = (DefaultException) e;
            return new ResponseVO(dex.getCode(), dex.getMessage());
        }

        return new ResponseVO(ResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

}