package com.course.ymx.jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(2)
@WebFilter(filterName = "paramsFilter", urlPatterns = "/*", asyncSupported = true)
public class CustomizeParamsFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeParamsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.debug("Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //参数处理
        HandleParamsDecryptHttpServletRequest handleParamsHttpServletRequest = new HandleParamsDecryptHttpServletRequest((HttpServletRequest) servletRequest);
        filterChain.doFilter(handleParamsHttpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("Filter destroy");
    }
}
