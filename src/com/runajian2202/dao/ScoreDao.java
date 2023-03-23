package com.runajian2202.dao;

import com.runajian2202.Model.Score;
import com.runajian2202.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qingqian
 * 成绩sql方法包
 */
public class ScoreDao {
    public static ResultSet list(Connection conn ,Score s,String name) throws SQLException {
        StringBuilder sbb = new StringBuilder("select * from "+name);
        System.out.println(name);
        if (s.getSid()!=0){
            sbb.append(" where sid="+s.getSid());
        }
        PreparedStatement ps=conn.prepareStatement(sbb.toString());
        return ps.executeQuery();
    }
    public static int add(Connection conn, Score s,String name) throws SQLException {
        String sql="insert into "+ name +" (sid,sname,chinese,math,english,sum) value(?,?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1,s.getSid());
        ps.setString(2,s.getSname());
        ps.setDouble(3, s.getChinese());
        ps.setDouble(4, s.getMath());
        ps.setDouble(5,s.getEnglish());
        ps.setDouble(6,s.getSum());
        return ps.executeUpdate();
    }
    public static int delete(Connection conn,Score s,String name) throws SQLException {
        String sql="DELETE FROM "+name+" WHERE sid="+s.getSid();
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
    public static int Update(Connection conn, Score s,String name) throws SQLException {
        String sql="UPDATE "+name +" set chinese="+s.getChinese()+",math="+s.getMath()+",english="+s.getEnglish()+",sum="+s.getSum()+" where "+"sid="+s.getSid();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
    public static ResultSet sum(Connection conn,String name) throws SQLException {
        String sql="SELECT COALESCE(SUM(chinese),0)AS score,COALESCE(sum(math),0)AS score,COALESCE(SUM(english),0)AS score  FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet average(Connection conn,String name) throws SQLException {
        String sql="SELECT ROUND(AVG(chinese),2)AS score,ROUND(AVG(math),2) AS score,ROUND(AVG(english),2)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet maximum(Connection conn,String name) throws SQLException {
        String sql="SELECT MAX(chinese)AS score,MAX(math)AS score,MAX(english)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet minimum(Connection conn,String name) throws SQLException {
        String sql="SELECT min(chinese)AS score,min(math)AS score,min(english)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static ResultSet sort(Connection conn,String name) throws SQLException {
        String sql="SELECT * FROM "+name+" ORDER BY sum DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static int Create(Connection conn,User u) throws SQLException {
        String sql="CREATE TABLE  "+u.getUserName()+" (sid INT(11) NOT NULL PRIMARY KEY,sname varchar(255) NOT NULL,chinese double,math double,english double,sum double)";
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    //修改密码sql语句
    public static ResultSet FindPassword(Connection conn,String oldPassword,String name) throws SQLException {
        String sql="select password from teacher where password="+"\""+oldPassword+"\""+" and "+"tname="+"\""+name+"\"";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    public static int UpdatePassword(Connection conn,String newPassword, String name) throws SQLException {
        String sql="UPDATE teacher SET password="+"\""+newPassword+"\""+" where tname="+"\""+name+"\"";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
}
