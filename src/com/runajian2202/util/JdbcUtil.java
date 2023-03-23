package com.runajian2202.util;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author qingqian
 * mysql连接
 */
public class JdbcUtil {
    //连接参数
    private  static  Connection conn=null;

    private static final String URL =
            "jdbc:mysql://8.130.108.248:3306/ssms?characterEncoding=utf8&serverTimeZone=Asia/Shanghai";
    private static final String UserName = "ssms";
    private static final String PassWord = "278220xyh";

    //静态块加载驱动
    public static Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,UserName,PassWord);
        } catch (Exception e) {
            e.printStackTrace();
            //异常处理,抛给java
        }
       return conn;
    }
}
