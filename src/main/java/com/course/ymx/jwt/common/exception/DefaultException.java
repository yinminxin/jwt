package com.course.ymx.jwt.common.exception;

import com.course.ymx.jwt.common.result.ResultCode;

/**
 * @description: 默认自定义异常
 * @author: yinminxin
 * @create: 2021-02-01
 **/
public class DefaultException extends RuntimeException {

    private String code;

    public DefaultException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DefaultException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
