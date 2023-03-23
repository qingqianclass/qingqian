package com.runajian2202.page;

import org.apache.poi.ss.usermodel.FontCharset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LnitialLogin extends JFrame implements ActionListener {
    JPanel jpl;
    JLabel jlb,jlb1;
    JButton jbt0,jbt1;

    public LnitialLogin()  {
        setTitle("学生成绩管理系统");
        setSize(600,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        jlb1=new JLabel("欢迎使用学生成绩管理系统");
        jlb1.setFont(new Font("楷体",Font.BOLD,45));
        jlb1.setBounds(20,50,600,100);

        jlb = new JLabel("选择登录方式",JLabel.CENTER);
        jlb.setFont(new Font("楷体",Font.BOLD, 30));
        jlb.setBounds(80, 180, 400, 50);

        jbt0=new JButton("学生登录");
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbt0.setBounds(70,300,160,70);
        jbt0.setFocusPainted(false);
        jbt0.addActionListener(this);

        jbt1=new JButton("教师登录");
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbt1.setBounds(350,300,160,70);
        jbt1.setFocusPainted(false);
        jbt1.addActionListener(this);

        jpl=new JPanel();
        jpl.setLayout(null);
        jpl.add(jlb);
        jpl.add(jlb1);
        jpl.add(jbt0);
        jpl.add(jbt1);
        this.add(jpl);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LnitialLogin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbt0){
            new StuLogin();
            this.dispose();
        }
        if (e.getSource()==jbt1){
            new login();
            this.dispose();
        }
    }
}
