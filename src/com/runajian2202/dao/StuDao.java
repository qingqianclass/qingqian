package com.runajian2202.dao;

import com.runajian2202.Model.Stu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qingqian
 * 学生查询成绩sql语句
 */
public class StuDao {
    //学生登录
    public static ResultSet stulog(Connection conn ,Stu s) throws SQLException {
        String sql = "select * from students where id=? and password=?";
        PreparedStatement ps =conn.prepareStatement(sql);
        ps.setString(1,s.getId());
        ps.setString(2,s.getPassword());
        return ps.executeQuery();
    }
    //学生注册
    public static int stuRegister(Connection conn, Stu s) throws SQLException {
        String sql="insert into students (id,password) values(?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, s.getId());
        ps.setString(2, s.getPassword());
        return ps.executeUpdate();
    }
    //学生旧密码校验
    public static ResultSet FindPassword(Connection conn,String oldPassword,String id) throws SQLException {
        String sql="select password from students where password="+"\""+oldPassword+"\""+" and "+"ID="+"\""+id+"\"";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //学生密码修改
    public static int updateStuPasswords(Connection conn,Stu s) throws SQLException {
        String sql="UPDATE students set password="+"\""+s.getPassword()+"\""+" where ID="+"\""+s.getId()+"\"";
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeUpdate();
    }
    //判断查询students表学号是否存在
    public static ResultSet  stuIdChecked(Connection conn,String s) throws SQLException {
        String sql="select id FROM idtable WHERE id="+s;
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //判断查询idtable表学号是否存在
    public static ResultSet  stuIdChecked_as_idtable(Connection conn,String s) throws SQLException {
        String sql="select ID FROM idtable WHERE ID="+s;
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
    //插入学生学号和录入成绩老师的用户名
  public static int insertId_and_teacherName(Connection conn,String id,String teacherName) throws SQLException {
        String sql="insert into idtable (ID,teacherName) values (?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1,id);
        pst.setString(2,teacherName);
        return pst.executeUpdate();
  }
  //查找学号对应的老师
  public static ResultSet findTeacherName(Connection conn,String id) throws SQLException {
        String sql="select teacherName from idtable where ID="+id;
        PreparedStatement ps=conn.prepareStatement(sql);
        return ps.executeQuery();
  }
  //查找学生成绩
  public static ResultSet findGrade(Connection conn,String id,String teacherName) throws SQLException {
        String sql="select * from "+teacherName+" where sid="+id;
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
  }
  //删除学号和老师用户名记录
  public static int delID_and_teacherName(Connection conn,String id) throws SQLException {
        String sql="delete from idtable where ID="+id;
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeUpdate();
  }
}
