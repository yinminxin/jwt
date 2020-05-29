package com.course.ymx.jwt.security;

import com.alibaba.fastjson.JSONObject;
import com.course.ymx.jwt.utils.RSA_EntryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class HandleParamsDecryptHttpServletRequest extends HttpServletRequestWrapper {

    private Logger logger = LoggerFactory.getLogger(HandleParamsDecryptHttpServletRequest.class);
    private byte[] bytes;

    /**
     * 功能描述: <br>
     * 〈POST请求的RequestBody替换〉
     * @author ymx
     * @date 2019/12/11
     */
    public HandleParamsDecryptHttpServletRequest(HttpServletRequest request) {

        super(request);

        try (BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            bytes = baos.toByteArray();
            String body = new String(bytes);
            if (!StringUtils.isEmpty(body)) {
                JSONObject jsonBody = JSONObject.parseObject(body);
                if (null != jsonBody) {
                    String aesKey = super.getHeader("token");
                    String dataEncrypt = jsonBody.getString("data");
                    String data = null;
                    JSONObject json = null;
                    try {
                        if (StringUtils.isNotBlank(dataEncrypt) && StringUtils.isNotBlank(aesKey)) {
                            data = RSA_EntryptUtil.decryptionRsaAndAes(dataEncrypt, aesKey);
                        }
                        json = null;
                        if (StringUtils.isNotBlank(data)) {
                            json = JSONObject.parseObject(data);
                        }
                    } catch (Exception e) {
                        json = null;
                    }
                    if (json != null) {
                        body = json.toJSONString();
                    }
                    bytes = body.getBytes();
                }
            }

            logger.info("body: {}", body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: <br>
     * 〈POST请求的RequestBody替换〉
     * @author ymx
     * @date 2019/12/11
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * 功能描述: <br>
     * 〈GET 请求替换〉
     * @author ymx
     * @date 2019/12/11
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> enumeration = super.getParameterNames();
        ArrayList<String> list = Collections.list(enumeration);
        return Collections.enumeration(list);
    }

    /**
     * 功能描述: <br>
     * 〈GET 请求替换〉
     * @author ymx
     * @date 2019/12/11
     */
    @Override
    public String getParameter(String name) {
//        if ("schoolName".equals(name)) {
////            return schoolName;
////        }
        return super.getParameter(name);
    }

    /**
     * 功能描述: <br>
     * 〈GET 请求替换〉
     * @author ymx
     * @date 2019/12/11
     */
    @Override
    public String[] getParameterValues(String name) {
//        if ("schoolName".equals(name)) {
//            return new String[]{schoolName};
//        }
        return super.getParameterValues(name);
    }

    /**
     * 功能描述: <br>
     * 〈POST请求的RequestBody替换schoolName〉
     * @author ymx
     * @date 2019/12/11
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }
}