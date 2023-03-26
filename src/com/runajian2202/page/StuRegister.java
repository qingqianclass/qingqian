package com.runajian2202.page;

import com.runajian2202.Model.Stu;
import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.DuplicateCheck;
import com.runajian2202.tools.PasswordFormatCheck;
import com.runajian2202.tools.StringUtil;
import com.runajian2202.tools.VerificationCode;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qingqian
 * 学生注册
 */
public class StuRegister extends JFrame implements ActionListener {

    JLabel jlb0, jlb1, jlb2, jlb3, jlb4,jlb5;
    JTextField jtf, jtf2;
    JPasswordField jpf0, jpf1;
    JButton jbt0, jbt1, jbt2;
    JPanel index;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StuRegister() {

        setTitle("学生成绩管理系统");
        //注册
        jlb0 = new JLabel("欢迎注册", JLabel.CENTER);
        jlb0.setBounds(0, 40, 400, 50);
        jlb0.setFont(new Font("楷体", Font.BOLD, 40));
        //用户名
        jlb1 = new JLabel("学号:");
        jlb1.setBounds(40, 150, 240, 40);
        jlb1.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //用户名框
        jtf = new JTextField();
        jtf.setBounds(120, 150, 240, 40);
        jtf.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //密码
        jlb2 = new JLabel("密码:");
        jlb2.setBounds(40, 220, 80, 40);
        jlb2.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //密码框
        jpf0 = new JPasswordField();
        jpf0.setBounds(120, 220, 240, 40);
        jpf0.setFont(new Font("微软雅黑", Font.BOLD, 17));
        //确认密码
        jlb3 = new JLabel("确认密码:");
        jlb3.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb3.setBounds(40, 290, 80, 40);
        //确认密码框
        jpf1 = new JPasswordField();
        jpf1.setBounds(120, 290, 240, 40);
        jpf1.setFont(new Font("微软雅黑", Font.BOLD, 17));

        //验证码
        jlb4 = new JLabel("验证码:");
        jlb4.setBounds(40, 360, 80, 40);
        jlb4.setFont(new Font("微软雅黑", Font.BOLD, 17));

        //验证码框
        jtf2 = new JTextField();
        jtf2.setBounds(120, 360, 120, 40);
        jtf2.setFont(new Font("微软雅黑", Font.BOLD, 17));

        //验证码域
        setCode(VerificationCode.code());
        jbt2 = new JButton(getCode());
        jbt2.setBounds(250, 360, 100, 40);
        jbt2.setFont(new Font("等线", Font.PLAIN, 15));
        jbt2.setBackground(new Color(255, 255, 255));
        //去掉文本框
        jbt2.setFocusPainted(false);
        //去掉外边框
        jbt2.setBorderPainted(false);
        jbt2.setBackground(new Color(255, 240, 245));
        jbt2.addActionListener(this);


        //注册
        jbt0 = new JButton("注册");
        jbt0.setBounds(40, 430, 110, 40);
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt0.addActionListener(this);
        //去掉文本框
        jbt0.setFocusPainted(false);

        //登录
        jbt1 = new JButton("登录");
        jbt1.setBounds(250, 430, 110, 40);
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt1.addActionListener(this);
        //去掉文本框
        jbt1.setFocusPainted(false);

        //背景图片
        jlb5=new JLabel();
        jlb5.setBounds(0,0,420,600);
        //设置成不透明
        jlb5.setOpaque(true);
        //处理背景图片url
        URL url = getClass().getResource("/IMG/img1.png");
        if (url!=null){
            jlb5.setIcon(new ImageIcon(url));
        }
        //添加到容器,容器添加到主页
        index = new JPanel();
        index.setLayout(null);
        //容器内容对齐方式
        index.add(jtf);
        index.add(jlb0);
        index.add(jlb1);
        index.add(jlb2);
        index.add(jlb3);
        index.add(jbt0);
        index.add(jbt1);
        index.add(jpf0);
        index.add(jpf1);
        index.add(jbt2);
        index.add(jlb4);
        index.add(jtf2);
        index.add(jlb5);
        add(index);

        setSize(420, 600);
        //程序窗口居中
        setLocationRelativeTo(null);
        //关闭窗口即结束程序后台运行
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //固定窗口,不允许调整大小
        setResizable(false);
        setVisible(true);
        Color bgColor = new Color(255, 240, 245);
        index.setBackground(bgColor);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbt0){
            //注册
            String id = jtf.getText().trim();
            String pwd = new String(jpf0.getPassword());
            String pwd2 = new String(jpf1.getPassword());
            Stu stu = new Stu();
            stu.setId(id);
            stu.setPassword(pwd);
            stu.setPassword(pwd2);

            if (StringUtil.isEmpty(id)) {
                JOptionPane.showMessageDialog(null, "请先输入注册账号");
                return;
            }
            if (StringUtil.isEmpty(pwd)) {
                JOptionPane.showMessageDialog(null, "请输入密码");
                return;
            }
            if (StringUtil.isEmpty(pwd2)) {
                JOptionPane.showMessageDialog(null, "请重复输入密码");
                return;
            }
            if (!pwd.equals(pwd2)) {
                JOptionPane.showMessageDialog(null, "两次密码不一致");
                return;
            }if (!PasswordFormatCheck.check(pwd)){
                JOptionPane.showMessageDialog(null,"密码必须由数字,大小写字母三种组成");
                return;
            }
            if (!jtf2.getText().trim().equals(getCode())) {
                JOptionPane.showMessageDialog(null, "验证码错误");
                setCode(VerificationCode.code());
                jbt2.setText(getCode());
                return;
            }
            Connection conn = JdbcUtil.getConn();
            try {
                ResultSet rs=DuplicateCheck.StuAddTools(conn,stu);
                if (rs.next()){
                    JOptionPane.showMessageDialog(null,"学号已存在");
                    conn.close();
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                int i= StuDao.stuRegister(conn, stu);
                if (i!=0){
                    JOptionPane.showMessageDialog(null, "注册成功");
                    conn.close();
                    new StuLogin();
                    this.dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource()==jbt1){
            //返回登录
            this.dispose();
            new StuLogin();
        }
        if (e.getSource()==jbt2){
            setCode(VerificationCode.code());
            jbt2.setText(getCode());
        }

    }
}
