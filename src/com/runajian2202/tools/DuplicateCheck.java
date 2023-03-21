package com.runajian2202.tools;

import com.runajian2202.Model.*;
import com.runajian2202.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DuplicateCheck {
    public static ResultSet registration(Connection conn, User u) throws SQLException {
        String sql = "select tname from teacher where tname=?" ;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        return ps.executeQuery();
    }
    public static ResultSet addTools(Connection conn, Score s,String name) throws SQLException {
        String sql="select sid from "+name+" where sid=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,s.getSid());
        return ps.executeQuery();
    }
}
