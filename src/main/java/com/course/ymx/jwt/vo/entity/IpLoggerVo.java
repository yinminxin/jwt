package com.course.ymx.jwt.vo.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: IP访问记录Vo
 * @author: yinminxin
 * @create: 2021-02-02
 **/
public class IpLoggerVo {

    /** 接口地址 */
    private String url;
    /** 接口访问时间 (毫秒) */
    private List<LocalDateTime> time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LocalDateTime> getTime() {
        return time;
    }

    public void setTime(List<LocalDateTime> time) {
        this.time = time;
    }

    public IpLoggerVo() {
    }

    public IpLoggerVo(String url, List<LocalDateTime> time) {
        this.url = url;
        this.time = time;
    }
}
