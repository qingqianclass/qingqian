package com.runajian2202.dao;

import java.sql.*;
import java.time.LocalDateTime;

public class LockDao {
    //查询锁定状态
    public static ResultSet checkLock(Connection conn, String uername) throws SQLException {
        String sql="select * from user_lock where username=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,uername);
        return ps.executeQuery();
    }
    //锁定
    public static int lockUrename(Connection conn, String name, LocalDateTime t) throws SQLException {
        String sql="INSERT INTO user_lock (username, lock_time) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setTimestamp(2, Timestamp.valueOf(t));
        return ps.executeUpdate();
    }
    //解锁
    public static int unlockUrename(Connection conn,String name) throws SQLException {
        String sql="DELETE FROM user_lock WHERE username = ?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1, name);
        return ps.executeUpdate();
    }
    public static int lockSeconds(Connection conn, String name, long seconds) throws SQLException {
        String sql = "UPDATE user_lock set lock_seconds=? where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, seconds);
        ps.setString(2,name);
        return ps.executeUpdate();
    }
}
