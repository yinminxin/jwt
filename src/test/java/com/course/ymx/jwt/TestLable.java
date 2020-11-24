package com.course.ymx.jwt;

import com.course.ymx.jwt.entity.User;
import com.mysql.jdbc.Driver;
import org.hibernate.annotations.CollectionId;

import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestLable {

    public static void main(String[] args) {
        //143, 264, 268, 348, 368, 198, 207, 267, 173
//        int[] arr ={143, 264, 268, 348};
////        int[] arr ={368, 198, 207, 267};
//        int count = 0;
//        for (int i : arr) {
//            count+=i;
//        }
//        System.out.println(count);
        try {
            String decode = URLDecoder.decode("%3Cspan%3E%E5%AD%A6%E6%83%85%E7%BB%9F%E8%AE%A1%3C%2Fspan%3E%3C%2Fa%3E", "UTF-8");
            System.out.println(decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关键字格式化（中间空格换成、号）
     */
    public static String formatSearchKey(String searchKey) {
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(searchKey.trim());
        return m.replaceAll("、");
    }

//    public static int i = 0;
//    public volatile static boolean flag = true;
//
//    public static StringBuilder sb = new StringBuilder();


//    public static void test1(){
//        System.out.println("当前执行线程" + Thread.currentThread().getName());
//        while (flag){
//            i++;
////            test3(100000);
//        }
//        System.out.println("跳出循环" + i);
//    }
//
//    public static void test2(){
//        System.out.println("当前执行线程" + Thread.currentThread().getName());
//        flag = false;
//    }
//
//    public static void test3(long start){
//        long l = System.nanoTime();
//        long end;
//        do{
//            end = System.nanoTime();
//        }while (start + l >= end);
//    }

//    public static void main(String[] args) throws SQLException {

//        String dirName = "D:\\yinminxin\\develop";
//        String dirName = "F:\\万达信息\\微校资源复制\\各区汇总等多个文件";
//        readFileName(dirName);
//        writeFile(sb.toString());

//        List<User> users = new ArrayList<>();
//
//        for (int i1 = 0; i1 < 5; i1++) {
//            User user = new User();
//            user.setToken("123456");
//            users.add(user);
//        }
//
//        System.out.println(users.get(0).getToken());
//
//        for (User user : users) {
//            user.setToken("5467");
//        }
//
//        System.out.println(users.get(0).getToken());

//        new Thread(()->{
//            test1();
//        },"线程test1").start();
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(()->{
//            test2();
//        },"线程test2").start();

//        //jdbc操作数据库
//        Connection connection = null;
//        ResultSet resultSet = null;
//        Statement statement = null;
//        try {
//            DriverManager.registerDriver(new Driver());
//            String url = "jdbc:mysql://localhost:3306/etrain?useUnicode=true&amp;characterEncoding=UTF-8";
//            String username = "root";
//            String password = "root123";
//            connection = DriverManager.getConnection(url, username, password);
//            String createDatabasesSql = "CREATE DATABASE IF NOT EXISTS create_datebase_test default charset utf8 COLLATE utf8_general_ci";
//
//            //建数据库
//            PreparedStatement cdps = connection.prepareStatement(createDatabasesSql);
//            cdps.executeUpdate();
//            //关闭连接
//            cdps.close();
//            connection.close();
//
//            //连接新建的数据库
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/create_datebase_test?useUnicode=true&amp;characterEncoding=UTF-8", username, password);
//            //建表
//            statement = connection.createStatement();
//            String createTableSql = "CREATE TABLE IF NOT EXISTS `create_table_test` ( " +
//                    " id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID', " +
//                    " created_time datetime NOT NULL COMMENT '创建时间', " +
//                    " updated_time datetime NOT NULL COMMENT '更新时间', " +
//                    " author varchar(255) NOT NULL DEFAULT '' COMMENT '作者', " +
//                    " PRIMARY KEY (`id`) " +
//                    ") ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='创建测试表'";
//            statement.executeUpdate(createTableSql);
//
//            //插入数据
//            String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//            String replace = UUID.randomUUID().toString().replace("-", "");
//            String insertLineInfoSql = "INSERT INTO create_table_test(created_time,updated_time,author) VALUES('" + format + "','" + format + "','" + replace + "')";
//            statement.executeUpdate(insertLineInfoSql);
//
//            //查询数据
//            String sql = "select id from create_table_test";
//            resultSet = statement.executeQuery(sql);
//
//            List list = new ArrayList<String>();
//            while (resultSet.next()){
//                String id = resultSet.getString("id");
//                list.add(id);
//            }
//            System.out.println(list.toString());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            resultSet.close();
//            connection.close();
//            statement.close();
//        }


        //jdbc连接查询数据库
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            DriverManager.registerDriver(new Driver());
//            String url = "jdbc:mysql://172.27.1.101:3306/etrain?useUnicode=true&characterEncoding=UTF-8";
////            String url = "jdbc:mysql://10.1.64.132:3306/etrain?useUnicode=true&amp;characterEncoding=UTF-8";
//            String username = "root";
//            String password = "tortVF2i-#XnN";
////            String password = "root123";
//            connection = DriverManager.getConnection(url, username, password);
//            String sql = "";
//            preparedStatement = connection.prepareStatement(sql);
//
//            resultSet = preparedStatement.executeQuery();
//            List list = new ArrayList<String>();
//            while (resultSet.next()){
//                String id = resultSet.getString("id");
//                list.add(id);
//            }
//            System.out.println(list.toString());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            resultSet.close();
//            preparedStatement.close();
//            connection.close();
//        }

//        int i = 0;
//        outer: // 此处不允许存在执行语句
//        for(; true ;) { // 无限循环
//            System.out.println("inner_after");
//            inner: // 此处不允许存在执行语句
//            for(; i < 10; i++) {
//                System.out.println("i = " + i);
//                if(i == 2) {
//                    System.out.println("continue");
//                    continue;
//                }
//                if(i == 3) {
//                    System.out.println("break");
//                    i++; // 否则 i 永远无法获得自增
//                    // 获得自增
//                    break;
//                }
//                if(i == 7) {
//                    System.out.println("continue outer");
//                    i++;  // 否则 i 永远无法获得自增
//                    // 获得自增
//                    continue outer;
//                }
//                if(i == 8) {
//                    System.out.println("break outer");
//                    break outer;
//                }
//                for(int k = 0; k < 5; k++) {
//                    if(k == 3) {
//                        System.out.println("continue inner");
//                        continue inner;
//                    }
//                }
//            }
//        }
        // 在此处无法 break 或 continue 标签
//    }

//    private static void readFileName(String dirName){
//        File file = new File(dirName);
//        File[] files = file.listFiles();
//        if (files == null || files.length <=0) {
//            return;
//        }
//
//        for (File file1 : files) {
//            if (file1.isDirectory()) {
//                readFileName(file1.getAbsolutePath());
//            }else if (file1.isFile()){
//                System.out.println(file1.getAbsolutePath());
//                try {
//                    writeFile(file1.getAbsolutePath());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
////                sb.append(file1.getAbsolutePath());
//            }
//        }
//        String[] list = file.list();
//        for (String s : list) {
//            System.out.println(s);
//        }
//    }

//    private static void writeFile(String text) throws IOException {
//        File file = new File("D:\\yinminxin\\项目需求\\2020\\fileNames.txt");
//
//        FileWriter writer = null;
//        BufferedWriter out = null;
//        try {
//            if (file.exists()) {
//                file.createNewFile();
//            }
//            writer = new FileWriter(file,true);
//            out = new BufferedWriter(writer);
//            out.write(text + "\r\n");
//            out.flush(); // 把缓存区内容压入文件
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            out.close();
//            writer.close();
//        }
//
//    }


}
