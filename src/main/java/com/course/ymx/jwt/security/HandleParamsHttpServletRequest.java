package com.course.ymx.jwt.security;//package com.second.boss.estate.security;
//
//import com.alibaba.fastjson.JSONObject;
//import com.second.boss.estate.utils.RSA_EntryptUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Enumeration;
//
//public class HandleParamsHttpServletRequest extends HttpServletRequestWrapper {
//
//    private Logger logger = LoggerFactory.getLogger(HandleParamsHttpServletRequest.class);
//    private byte[] bytes;
//    private String schoolName;
//    private String districtName;
//    private String districtCode;
//    private String schoolCode;
//
//
//    /**
//     * 功能描述: <br>
//     * 〈POST请求的RequestBody替换schoolName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    public HandleParamsHttpServletRequest(HttpServletRequest request) {
//
//        super(request);
//
//        try (BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
//             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = bis.read(buffer)) > 0) {
//                baos.write(buffer, 0, len);
//            }
//            bytes = baos.toByteArray();
//            String body = new String(bytes);
//            if (!StringUtils.isEmpty(body)) {
//                JSONObject jsonBody = JSONObject.parseObject(body);
//                if (null != jsonBody) {
//                    //将schoolName替换为token中的学校名称
//                    String token = super.getHeader("authorization");
//                    if (StringUtils.isEmpty(token)) {
//                        token = super.getHeader("Authorization");
//                    }
//                    String aesKey = super.getHeader("token");
//                    String dataEncrypt = jsonBody.getString("data");
//                    String data = null;
//                    JSONObject json = null;
//                    try {
//                        if (StringUtils.isNotBlank(dataEncrypt) && StringUtils.isNotBlank(aesKey)) {
//                            data = RSA_EntryptUtil.decryptionRsaAndAes(dataEncrypt, aesKey);
//                        }
//                        json = null;
//                        if (StringUtils.isNotBlank(data)) {
//                            json = JSONObject.parseObject(data);
//                        }
//                    } catch (Exception e) {
//                        json = null;
//                    }
//                    if (json != null && !StringUtils.isEmpty(token)) {
//                        try {
//                            schoolName = null; //JwtUtil.getSchoolNameByToken(token);
//                            districtName = null; //JwtUtil.getRNameByToken(token);
//                            districtCode = null; //JwtUtil.getRegionCodeByToken(token);
//                            schoolCode = null; //JwtUtil.getSchoolCodeByToken(token);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        //判断token中是否有学校，有的话进行替换 schoolName
//                        if (StringUtils.isNotBlank(schoolName)) {
//                            if (!StringUtils.isEmpty(schoolName)) {
//                                json.put("schoolName", schoolName);
//                            }
//                            System.out.println("请求body中含有schoolName" + json.toJSONString());
//                        }
//                        //判断token中是否有区，有的话进行替换 districtName
//                        if (StringUtils.isNotBlank(districtName)) {
//                                if (!StringUtils.isEmpty(districtName)) {
//                                    json.put("districtName", districtName);
//                                }
//                                System.out.println("请求body中含有districtName" + json.toJSONString());
//                        }
//                        //判断token中是否有区编码，有的话进行替换 regionCode
//                        if (StringUtils.isNotBlank(districtCode)) {
//                                if (!StringUtils.isEmpty(districtCode)) {
//                                    json.put("regionCode", districtCode);
//                                }
//                                System.out.println("请求body中含有districtCode" + json.toJSONString());
//                        }
//                        //判断token中是否有学校编码，有的话进行替换 schoolCode
//                        if (StringUtils.isNotBlank(schoolCode)) {
//                                if (!StringUtils.isEmpty(schoolCode)) {
//                                    json.put("schoolCode", schoolCode);
//                                }
//                                System.out.println("请求body中含有schoolCode" + json.toJSONString());
//                        }
//
//                        body = json.toJSONString();
//                    }
//                    bytes = body.getBytes();
//                }
//            }
//
//            logger.info("body: {}", body);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 功能描述: <br>
//     * 〈POST请求的RequestBody替换schoolName,districtName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    @Override
//    public BufferedReader getReader() {
//        return new BufferedReader(new InputStreamReader(this.getInputStream()));
//    }
//
//    /**
//     * 功能描述: <br>
//     * 〈GET 请求替换schoolName,districtName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    @Override
//    public Enumeration<String> getParameterNames() {
//        Enumeration<String> enumeration = super.getParameterNames();
//        ArrayList<String> list = Collections.list(enumeration);
//        return Collections.enumeration(list);
//    }
//
//    /**
//     * 功能描述: <br>
//     * 〈GET 请求替换schoolName,districtName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    @Override
//    public String getParameter(String name) {
//        if ("schoolName".equals(name)) {
//            return schoolName;
//        }
//        if ("districtName".equals(name)) {
//            return districtName;
//        }
//        if ("schoolCode".equals(name)) {
//            return schoolCode;
//        }
//        if ("districtCode".equals(name)) {
//            return districtCode;
//        }
//        return super.getParameter(name);
//    }
//
//    /**
//     * 功能描述: <br>
//     * 〈GET 请求替换schoolName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    @Override
//    public String[] getParameterValues(String name) {
//        if ("schoolName".equals(name)) {
//            return new String[]{schoolName};
//        }
//        if ("districtName".equals(name)) {
//            return new String[]{districtName};
//        }
//        if ("schoolCode".equals(name)) {
//            return new String[]{schoolCode};
//        }
//        if ("districtCode".equals(name)) {
//            return new String[]{districtCode};
//        }
//        return super.getParameterValues(name);
//    }
//
//    /**
//     * 功能描述: <br>
//     * 〈POST请求的RequestBody替换schoolName〉
//     *
//     * @author Blare
//     * @date 2019/12/11
//     */
//    @Override
//    public ServletInputStream getInputStream() {
//        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        return new ServletInputStream() {
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//
//            }
//
//            @Override
//            public int read() {
//                return byteArrayInputStream.read();
//            }
//        };
//    }
//}