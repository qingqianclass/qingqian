package com.runajian2202.page;

import com.runajian2202.tools.inputSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    JPanel jpl0, jpl1, jpl2;
    JButton jbt0, jbt1, jbt2, jbt3, jbt4, jbt5,jbt6,jbt7;
    JLabel jlb0, jlb1;

    public MainFrame(String s) {
        setTitle("学生成绩管理系统");
        setSize(1200, 834);
        //窗口居中
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //对齐方式:自适应
        setLayout(null);

        //页面头部
        jpl0 = new JPanel();
        jpl0.setBounds(0, 0, 1200, 100);
        jpl0.setBorder(BorderFactory.createEtchedBorder());
        jpl0.setLayout(null);

        jlb0 = new JLabel("学生成绩管理系统");
        jlb0.setBounds(20, 24, 300, 50);
        jlb0.setFont(new Font("微软雅黑", Font.BOLD, 36));

        jlb1 = new JLabel("你好," + s);
        jlb1.setFont(new Font("楷体", Font.BOLD, 24));
        jlb1.setBounds(960, 35, 200, 30);


        //页面导航
        jpl1 = new JPanel();
        jpl1.setLayout(null);
        jpl1.setBounds(10, 110, 260, 600);

        jbt0 = new JButton("成绩列表");
        jbt0.setBounds(10, 40, 240, 40);
        jbt0.setBackground(new Color(6, 176, 255));
        jbt0.addActionListener(this);

        jbt1 = new JButton("成绩录入");
        jbt1.setBounds(10, 110, 240, 40);
        jbt1.addActionListener(this);

        jbt2 = new JButton("成绩删除");
        jbt2.setBounds(10, 180, 240, 40);
        jbt2.addActionListener(this);

        jbt3 = new JButton("成绩修改");
        jbt3.setBounds(10, 250, 240, 40);
        jbt3.addActionListener(this);

        jbt4 = new JButton("成绩统计");
        jbt4.setBounds(10, 320, 240, 40);
        jbt4.addActionListener(this);

        jbt5=new JButton("成绩排序");
        jbt5.setBounds(10,390,240,40);
        jbt5.addActionListener(this);

        jbt7=new JButton("打印成绩");
        jbt7.setBounds(10,460,240,40);
        jbt7.addActionListener(this);

        jbt6 = new JButton("退出系统");
        jbt6.setBounds(10, 530, 240, 40);
        jbt6.addActionListener(this);

        //去掉文字框选
        jbt0.setFocusPainted(false);
        jbt1.setFocusPainted(false);
        jbt2.setFocusPainted(false);
        jbt3.setFocusPainted(false);
        jbt4.setFocusPainted(false);
        jbt5.setFocusPainted(false);
        jbt6.setFocusPainted(false);

        //主体
        jpl2 = new JPanel();
        jpl2.setBounds(280, 110, 906, 680);
        jpl2.setLayout(null);

        //启动默认内容
        ScoreList sl = new ScoreList(s);
        jpl2.add(sl);
        setVisible(true);

        //添加到容器
        jpl0.add(jlb0);
        jpl0.add(jlb1);
        jpl1.add(jbt0);
        jpl1.add(jbt1);
        jpl1.add(jbt2);
        jpl1.add(jbt3);
        jpl1.add(jbt4);
        jpl1.add(jbt5);
        jpl1.add(jbt6);
        jpl1.add(jbt7);
        //容器添加到窗体
        add(jpl0);
        add(jpl1);
        add(jpl2);
        //显示放在语末,避免控件加载不出来
        setVisible(true);

    }
    /*
     *  //重置按钮背景颜色
     * */

    private void resetNav() {
        jbt0.setBackground(null);
        jbt1.setBackground(null);
        jbt2.setBackground(null);
        jbt3.setBackground(null);
        jbt4.setBackground(null);
        jbt5.setBackground(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        resetNav();
        if (e.getSource() == jbt0) {
            //成绩列表
            jbt0.setBackground(new Color(6, 176, 255));
            jpl2.removeAll();
            //将用户名传入成绩列表
            ScoreList sl = new ScoreList(jlb1.getText().substring(3));
            jpl2.add(sl);
            jpl2.updateUI();//刷新界面
        } else if (e.getSource() == jbt1) {
            //成绩录入
            jpl2.removeAll();
            ScoreAdd scoreAdd = new ScoreAdd(jlb1.getText().substring(3));
            jpl2.add(scoreAdd);
            jpl2.updateUI();
            jbt1.setBackground(new Color(6, 176, 255));
        } else if (e.getSource() == jbt2) {
            //成绩删除
            jbt2.setBackground(new Color(6, 176, 255));
            jpl2.removeAll();
            ScoreDelete sd = new ScoreDelete(jlb1.getText().substring(3));
            jpl2.add(sd);
            jpl2.updateUI();

        } else if (e.getSource() == jbt3) {
            //成绩修改
            jpl2.removeAll();
            ScoreUpdate su = new ScoreUpdate(jlb1.getText().substring(3));
            jpl2.add(su);
            jpl2.updateUI();
            jbt3.setBackground(new Color(6, 176, 255));
        } else if (e.getSource() == jbt4) {
            //成绩统计
            jpl2.removeAll();
            ScoreStatistics sct = new ScoreStatistics(jlb1.getText().substring(3));
            jpl2.add(sct);
            jpl2.updateUI();
            jbt4.setBackground(new Color(6, 176, 255));
        } else if (e.getSource() == jbt5) {
            //排序
            jpl2.removeAll();
            ScoreSort scoreSort = new ScoreSort(jlb1.getText().substring(3));
            jpl2.add(scoreSort);
            jpl2.updateUI();
            jbt5.setBackground(new Color(6, 176, 255));

        } else if (e.getSource() == jbt6) {
            //退出系统
            int p = JOptionPane.showConfirmDialog(null, "确定退出系统", "选择选项", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (p == 0) {
                System.exit(0);
            }
        } else if (e.getSource()==jbt7) {
            ScoreSort scoreSort = new ScoreSort(jlb1.getText().substring(3));
            inputSheet.saveTableToExcel(scoreSort.jt);
        }

    }

}
