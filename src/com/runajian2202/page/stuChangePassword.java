package com.runajian2202.page;

import com.runajian2202.Model.Stu;
import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.PasswordFormatCheck;
import com.runajian2202.tools.VerificationCode;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qingqian
 * 学生密码修改
 */
public class stuChangePassword  extends JFrame implements ActionListener {
    JLabel label1, label2, label3, label4, label5, label6;
    JTextField jtf, jtf2;
    JPasswordField jpf1, jpf2, jpf3;
    JButton jbt, jbt2, jbt3;
    JPanel jpl;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public stuChangePassword(String name) {
        setSize(420, 600);
        setTitle("学生成绩管理系统");
        label1 = new JLabel("修改密码", JLabel.CENTER);
        label1.setBounds(0, 30, 400, 50);
        //设置字体:1:BOLD加粗,0:PLAIN不加粗
        label1.setFont(new Font("楷体", Font.BOLD, 40));

        label2 = new JLabel("当前学号:");
        label2.setBounds(40, 120, 240, 40);
        label2.setFont(new Font("微软雅黑", Font.BOLD, 16));

        jtf = new JTextField(name);
        jtf.setBounds(130, 120, 200, 40);
        jtf.setFont(new Font("微软雅黑", Font.BOLD, 17));

        label3 = new JLabel("旧密码:");
        label3.setFont(new Font("微软雅黑", Font.BOLD, 16));
        label3.setBounds(40, 190, 80, 40);

        jpf1 = new JPasswordField();
        jpf1.setBounds(130, 190, 200, 40);
        jpf1.setFont(new Font("微软雅黑", Font.BOLD, 17));

        label4 = new JLabel("新密码:");
        label4.setBounds(40, 260, 80, 40);
        label4.setFont(new Font("微软雅黑", Font.BOLD, 16));

        jpf2 = new JPasswordField();
        jpf2.setBounds(130, 260, 200, 40);
        jpf2.setFont(new Font("微软雅黑", Font.BOLD, 17));

        label5 = new JLabel("确认新密码:");
        label5.setBounds(40, 330, 120, 40);
        label5.setFont(new Font("微软雅黑", Font.BOLD, 16));

        jpf3 = new JPasswordField();
        jpf3.setFont(new Font("微软雅黑", Font.BOLD, 16));
        jpf3.setBounds(130, 330, 200, 40);

        label6 = new JLabel("验证码:");
        label6.setBounds(40, 400, 80, 40);
        label6.setFont(new Font("微软雅黑", Font.BOLD, 16));

        jtf2 = new JTextField();
        jtf2.setBounds(130, 400, 100, 40);
        jtf2.setFont(new Font("微软雅黑", Font.BOLD, 16));

        setCode(VerificationCode.code());
        jbt = new JButton(getCode());
        jbt.setBounds(240, 400, 90, 40);
        jbt.setFont(new Font("等线", Font.PLAIN, 14));
        jbt.setBackground(new Color(255, 240, 245));
        jbt.addActionListener(this);
        jbt.setBorderPainted(false);
        //去掉文本框
        jbt.setFocusPainted(false);

        jbt2 = new JButton("确认修改");
        jbt2.setBounds(70, 470, 110, 40);
        jbt2.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt2.addActionListener(this);
        //去掉文本框
        jbt2.setFocusPainted(false);

        jbt3 = new JButton("返回登录");
        jbt3.setBounds(240, 470, 110, 40);
        jbt3.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt3.addActionListener(this);


        jpl = new JPanel();
        jpl.setLayout(null);
        jpl.add(label1);
        jpl.add(label2);
        jpl.add(label3);
        jpl.add(label4);
        jpl.add(label5);
        jpl.add(label6);
        jpl.add(jtf);
        jpl.add(jtf2);
        jpl.add(jpf1);
        jpl.add(jpf2);
        jpl.add(jpf3);
        jpl.add(jbt);
        jpl.add(jbt2);
        jpl.add(jbt3);
        add(jpl);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jpl.setBackground(new Color(255, 240, 245));
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbt) {
            //手动刷新验证码
            setCode(VerificationCode.code());
            jbt.setText(getCode());
        }if (e.getSource()==jbt3){
            new StuLogin();
            this.dispose();
        }
        if (e.getSource()==jbt2){
            //修改密码
            String oldPassword = new String(jpf1.getPassword());
            String newpwd = new String(jpf2.getPassword());
            String newpwd1 = new String(jpf3.getPassword());
            if (jtf.getText().trim().length() == 0){
                JOptionPane.showMessageDialog(null,"学号为空");
                return;
            }
            if (!newpwd.equals(newpwd1)){
                JOptionPane.showMessageDialog(null,"两次新密码不一致");
                jpf2.setText("");
                jpf3.setText("");
                return;
            }if (!PasswordFormatCheck.check(newpwd)){
                JOptionPane.showMessageDialog(null,"密码必须由数字,大小写字母三种组成");
                return;
            }if (!jtf2.getText().trim().equals(getCode())) {
                JOptionPane.showMessageDialog(null, "验证码错误");
                //错误后刷新验证码
                setCode(VerificationCode.code());
                jbt.setText(getCode());
                return;
            }
            Stu stu = new Stu();
            stu.setId(jtf.getText());
            stu.setPassword(newpwd1);
            Connection conn = JdbcUtil.getConn();
            try {
                ResultSet rs = StuDao.stuIdChecked(conn,jtf.getText());
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"学号不存在");
                    conn.close();
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                //旧密码校验
                ResultSet rs=StuDao.FindPassword(conn,oldPassword,jtf.getText());
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"旧密码不正确");
                    jpf1.setText("");
                    conn.close();
                    return;
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                int i= StuDao.updateStuPasswords(conn,stu);
                if (i!=0){
                    JOptionPane.showMessageDialog(null,"修改成功");
                    new StuLogin();
                    this.dispose();
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
