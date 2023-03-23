package com.runajian2202.page;

import com.runajian2202.Model.Stu;
import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.StringUtil;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StuLogin extends JFrame implements  KeyListener,ActionListener {
    JLabel jlb0, jlb1, jlb2;
    JTextField jtf;
    JPasswordField jpf;
    JButton jbt0, jbt1,jbt2;
    /*
     * 容器*/
    JPanel index;

    public StuLogin() {
        setSize(420, 600);
        setTitle("学生成绩管理系统");
        //标题
        jlb0 = new JLabel("欢迎登录", JLabel.CENTER);

        jlb0.setBounds(0, 40, 400, 50);

        jlb0.setFont(new Font("楷体", Font.BOLD, 40));
        //用户名
        jlb1 = new JLabel("学号:");
        jlb1.setBounds(40, 150, 240, 40);
        jlb1.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //用户名文本框
        jtf = new JTextField();
        jtf.setBounds(120, 150, 240, 40);
        jtf.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf.addKeyListener(this);

        //密码
        jlb2 = new JLabel("密码:");
        jlb2.setBounds(40, 250, 80, 40);
        jlb2.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //密码框
        jpf = new JPasswordField();
        jpf.setBounds(120, 250, 240, 40);
        jpf.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jpf.addKeyListener(this);



        //登录按钮
        jbt0 = new JButton("登录");
        jbt0.setBounds(40, 400, 110, 40);
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt0.addActionListener(this);
        //去掉文本框
        jbt0.setFocusPainted(false);

        //注册按钮
        jbt1 = new JButton("注册");
        jbt1.setBounds(250, 400, 110, 40);
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt1.addActionListener(this);
        //去掉文本框
        jbt1.setFocusPainted(false);

        jbt2=new JButton("修改密码");
        jbt2.setBounds(350,520,80,40);
        jbt2.setFont(new Font("等线",Font.BOLD, 10));
        jbt2.addActionListener(this);
        jbt2.setFocusPainted(false);
        jbt2.setBorderPainted(false);
        jbt2.setBackground(new Color(255, 240, 245));


        //添加标签到容器index
        index = new JPanel();
        //容器内容对齐方式
        index.setLayout(null);
        index.add(jlb0);
        index.add(jlb1);
        index.add(jlb2);
        index.add(jpf);
        index.add(jtf);
        index.add(jbt0);
        index.add(jbt1);
        index.add(jbt2);

        //index容器添加到窗口
        this.add(index);
        //窗口居中
        setLocationRelativeTo(null);
        //设置程序关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置固定窗口
        setResizable(false);
        //窗口可视化
        setVisible(true);

        Color bgColor = new Color(255, 240, 245);
        index.setBackground(bgColor);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbt0){
            String id = String.valueOf(jtf.getText());
            String password = new String(jpf.getPassword());
            if (StringUtil.isEmpty(id)) {
                JOptionPane.showMessageDialog(null, "请先输入账号");
                return;
            }
            if (StringUtil.isEmpty(password)) {
                JOptionPane.showMessageDialog(null, "密码不能为空");
                return;
            }
            Stu stu = new Stu();
            stu.setId(id);
            stu.setPassword(password);
            Connection conn= JdbcUtil.getConn();
            try {
                ResultSet rs= StuDao.stulog(conn,stu);
                if (rs.next()){
                    JOptionPane.showMessageDialog(null,"登录成功");
                    this.dispose();
                    new StuMainFrame();
                    conn.close();
                }else {
                    jpf.setText("");
                    JOptionPane.showMessageDialog(null, "账号或者密码错误");
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }if (e.getSource()==jbt1){
            //注册
            this.dispose();
            new StuRegister();
        }
        if (e.getSource()==jbt2){
            new stuChangePassword(jtf.getText());
            this.dispose();

        }

    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar()==KeyEvent.VK_ENTER){
            String id = String.valueOf(jtf.getText());
            String password = new String(jpf.getPassword());
            if (StringUtil.isEmpty(id)) {
                JOptionPane.showMessageDialog(null, "请先输入账号");
                return;
            }
            if (StringUtil.isEmpty(password)) {
                JOptionPane.showMessageDialog(null, "密码不能为空");
                return;
            }
            Stu stu = new Stu();
            stu.setId(id);
            stu.setPassword(password);
            Connection conn= JdbcUtil.getConn();
            try {
                ResultSet rs= StuDao.stulog(conn,stu);
                if (rs.next()){
                    JOptionPane.showMessageDialog(null,"登录成功");
                    this.dispose();
                    new StuMainFrame();
                    conn.close();
                }else {
                    jpf.setText("");
                    JOptionPane.showMessageDialog(null, "账号或者密码错误");
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
