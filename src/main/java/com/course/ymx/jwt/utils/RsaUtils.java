package com.course.ymx.jwt.utils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtils {
    /**
     * 从文件中读取公钥
     *
     * @param filename 公钥保存路径，相对于classpath
     * @return 公钥对象
     * @throws Exception
     */
    public static PublicKey getPublicKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取密钥
     *
     * @param filename 私钥保存路径，相对于classpath
     * @return 私钥对象
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    /**
     * 获取公钥
     *
     * @param bytes 公钥的字节形式
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @param bytes 私钥的字节形式
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     *
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(new File(fileName).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------分隔符--------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    public static void jdkRSA() {
        try {
            // 1.初始化发送方密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("Public Key:" + Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
            System.out.println("Private Key:" + Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));

            // 2.私钥加密、公钥解密 ---- 加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal("rsa test".getBytes());
            System.out.println("私钥加密、公钥解密 ---- 加密:" + Base64.getEncoder().encodeToString(result));

            // 3.私钥加密、公钥解密 ---- 解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密、公钥解密 ---- 解密:" + new String(result));

            // 4.公钥加密、私钥解密 ---- 加密
            X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
            PublicKey publicKey2 = keyFactory2.generatePublic(x509EncodedKeySpec2);
            Cipher cipher2 = Cipher.getInstance("RSA");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey2);
            byte[] result2 = cipher2.doFinal("rsa test".getBytes());
            System.out.println("公钥加密、私钥解密 ---- 加密:" + Base64.getEncoder().encodeToString(result2));

            // 5.私钥解密、公钥加密 ---- 解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory5 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey5 = keyFactory5.generatePrivate(pkcs8EncodedKeySpec5);
            Cipher cipher5 = Cipher.getInstance("RSA");
            cipher5.init(Cipher.DECRYPT_MODE, privateKey5);
            byte[] result5 = cipher5.doFinal(result2);
            System.out.println("公钥加密、私钥解密 ---- 解密:" + new String(result5));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //公钥加密
    public static String encryptStrByPublicKey(String publicKey,String encryptText){
        try {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
        KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
        PublicKey publicKey2 = keyFactory2.generatePublic(x509EncodedKeySpec2);
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, publicKey2);
        byte[] result2 = cipher2.doFinal(encryptText.getBytes());
        return Base64.getEncoder().encodeToString(result2);
        }catch (Exception e){
            e.printStackTrace();
            return "异常了";
        }
    }

    //私钥解密
    public static String decryptStrByPrivateKey(String privateKey,String decryptText){
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
            KeyFactory keyFactory5 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey5 = keyFactory5.generatePrivate(pkcs8EncodedKeySpec5);
            Cipher cipher5 = Cipher.getInstance("RSA");
            cipher5.init(Cipher.DECRYPT_MODE, privateKey5);
            byte[] result5 = cipher5.doFinal(Base64.getDecoder().decode(decryptText));
            return new String(result5);
        }catch (Exception e){
            e.printStackTrace();
            return "异常了";
        }
    }

    //私钥加密
    public static String encryptStrByPrivateKey(String privateKeyStr,String encryptText){
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(encryptText.getBytes());
//            System.out.println("私钥加密、公钥解密 ---- 加密:" + Base64.getEncoder().encodeToString(result));
            return Base64.getEncoder().encodeToString(result);
        }catch (Exception e){
            e.printStackTrace();
            return "异常了";
        }
    }

    //公钥解密
    public static String decryptStrByPublicKey(String publicKeyStr,String decryptText){
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(decryptText.getBytes()));
//            System.out.println("私钥加密、公钥解密 ---- 解密:" + new String(result));
            return new String(result);
        }catch (Exception e){
            e.printStackTrace();
            return "异常了";
        }
    }

    public static void main(String[] args) {
//        jdkRSA();

        //公钥加密,私钥解密
//        encryptByPublicKeyAndDecryptByPrivateKey();

//      私钥加密/公钥解密
        encryptByPrivateKeyAndDecryptByPublicKey();
    }

    //公钥加密,私钥解密
    public static void encryptByPublicKeyAndDecryptByPrivateKey(){
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIY0TZWjA/u1l0bmIZ4I0eBIkMdOMQYl6yqeOPKlykGYPIf3eQSbUmNmPPpCtBqTXuuyBR//Zq/cKXniN9pHbVcCAwEAAQ==";
        String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAhjRNlaMD+7WXRuYhngjR4EiQx04xBiXrKp448qXKQZg8h/d5BJtSY2Y8+kK0GpNe67IFH/9mr9wpeeI32kdtVwIDAQABAkBZOJiD7PaO9zD1b7XVzONLCrNF7ZPmFHt9v+b4WoRWipMcotTzmUga1MhJUVj1GD94hga9YULhJMsNVckuL57hAiEA7kxV+4O3IyDZ6PxsrVncs9Wk1JfvQf/T1nYDIWXb+QkCIQCQLG/VAx3ohFyHhR45Ytl+3KBXraG0NucsSEs1ICWrXwIgb44c0lme6jr+yCsjVSO0RhvFxiA6SVNSABvO61ub1sECIE++VGjUnYFTXL6UkaAaHcS+kW2Nm0hLqdfjcL3EtzEvAiASdT3OYBtFoP6tssfYDOPjIobaElfDJXLbupubRgKMHg==";

        //公钥加密/私钥解密 ----公钥加密
        String encryptStrByPublicKey = encryptStrByPublicKey(publicKey, "公钥加密/私钥解密");
        System.out.println("公钥加密/私钥解密/加密前的字符串===" + "公钥加密/私钥解密");
        System.out.println("公钥加密/私钥解密/加密后的字符串===" + encryptStrByPublicKey);
        //公钥加密/私钥解密 ----私钥解密
        String decryptStrByPrivateKey = decryptStrByPrivateKey(privateKey, encryptStrByPublicKey);
        System.out.println("公钥加密/私钥解密/解密后的字符串===" + decryptStrByPrivateKey);
    }

    //私钥加密/公钥解密
    public static void encryptByPrivateKeyAndDecryptByPublicKey(){
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALzANFblHlM/nAOq2H8mMpGqjtIKlDtNG8Yh7KV1RObovdW0hbVyJ9WiADFaTb1t+MHtaoi+GAN3Id9zVEBnFy0CAwEAAQ==";
        String privateKey = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAvMA0VuUeUz+cA6rYfyYykaqO0gqUO00bxiHspXVE5ui91bSFtXIn1aIAMVpNvW34we1qiL4YA3ch33NUQGcXLQIDAQABAkEAu8WGDBAA3BaCsY9Lsyof0pPXYxMNJfVutOvYzT1dX0veGOSqeG3lnujkXPcG6yokRH/csk3h/ktYUesQc77KbQIhAP8wr5sfKeqjE9xKGwOA6ug3+5dFHrkKgoClDf7ZzvGvAiEAvVmLNQ3MIs7NiJO0RUK7PX7V/DOqqaCjVvfLwaklB+MCIQCtKqR/Zw5Soob/nWnnXrwJCLQ5WjYW6wLTuS1eGv9LEQIhALDmc3QBi/IJG2S5+fWrPptZYDnbW1pWxcN+hlJL4FjZAiEAmMT7ELuX8KX3qFnvlvGoP2A/Ap3PHc1fT+mJcD4eNrw=";
        //私钥加密/公钥解密 ----私钥加密
        String encryptStrByPrivateKey = encryptStrByPrivateKey(privateKey, "私钥加密/公钥解密");
        System.out.println("私钥加密/公钥解密/加密前的字符串===" + "私钥加密/公钥解密");
        System.out.println("私钥加密/公钥解密/加密后的字符串===" + encryptStrByPrivateKey);
        //私钥加密/公钥解密 ----公钥解密
        String decryptStrByPublicKey = decryptStrByPublicKey(publicKey, encryptStrByPrivateKey);
        System.out.println("私钥加密/公钥解密/解密后的字符串===" + decryptStrByPublicKey);
    }

}
