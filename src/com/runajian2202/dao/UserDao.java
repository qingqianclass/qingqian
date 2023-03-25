package com.runajian2202.dao;

import com.runajian2202.Model.User;
import com.runajian2202.util.JdbcUtil;

import java.sql.*;

/**
 * @author qingqian
 *用户信息sql
 */
public class UserDao {
    //教师登录
    public static ResultSet login(Connection conn, User u) throws SQLException {
        String sql = "select * from teacher where tname=? and password=?" ;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        ps.setString(2,u.getPassword());
        return ps.executeQuery();
    }
    //教师注册
    public static int register(Connection conn ,User u) throws SQLException {
        String sql="insert into teacher (tname,password) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        ps.setString(2, u.getPassword());
       return ps.executeUpdate();
    }

}
