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
    //成绩查询
    public static ResultSet list(Connection conn ,Score s,String name) throws SQLException {
        StringBuilder sbb = new StringBuilder("select * from "+name);
        System.out.println(name);
        if (s.getSid()!=0){
            sbb.append(" where sid="+s.getSid());
        }
        PreparedStatement ps=conn.prepareStatement(sbb.toString());
        return ps.executeQuery();
    }
    //成绩添加
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
    //成绩删除
    public static int delete(Connection conn,Score s,String name) throws SQLException {
        String sql="DELETE FROM "+name+" WHERE sid="+s.getSid();
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
    //成绩修改
    public static int Update(Connection conn, Score s,String name) throws SQLException {
        String sql="UPDATE "+name +" set chinese="+s.getChinese()+",math="+s.getMath()+",english="+s.getEnglish()+",sum="+s.getSum()+" where "+"sid="+s.getSid();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
    //成绩求和
    public static ResultSet sum(Connection conn,String name) throws SQLException {
        String sql="SELECT COALESCE(SUM(chinese),0)AS score,COALESCE(sum(math),0)AS score,COALESCE(SUM(english),0)AS score  FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //成绩平均分
    public static ResultSet average(Connection conn,String name) throws SQLException {
        String sql="SELECT ROUND(AVG(chinese),2)AS score,ROUND(AVG(math),2) AS score,ROUND(AVG(english),2)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //单科最大
    public static ResultSet maximum(Connection conn,String name) throws SQLException {
        String sql="SELECT MAX(chinese)AS score,MAX(math)AS score,MAX(english)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //单科最小
    public static ResultSet minimum(Connection conn,String name) throws SQLException {
        String sql="SELECT min(chinese)AS score,min(math)AS score,min(english)AS score FROM "+name;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //成绩排序
    public static ResultSet sort(Connection conn,String name) throws SQLException {
        String sql="SELECT * FROM "+name+" ORDER BY sum DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //学号库表创建
    public static int Create(Connection conn,User u) throws SQLException {
        String sql="CREATE TABLE  "+u.getUserName()+" (sid INT(11) NOT NULL PRIMARY KEY,sname varchar(255) NOT NULL,chinese double,math double,english double,sum double)";
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

    //教师旧密码校验
    public static ResultSet FindPassword(Connection conn,String oldPassword,String name) throws SQLException {
        String sql="select password from teacher where password="+"\""+oldPassword+"\""+" and "+"tname="+"\""+name+"\"";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //修改教师密码
    public static int UpdatePassword(Connection conn,String newPassword, String name) throws SQLException {
        String sql="UPDATE teacher SET password="+"\""+newPassword+"\""+" where tname="+"\""+name+"\"";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
    }

}
