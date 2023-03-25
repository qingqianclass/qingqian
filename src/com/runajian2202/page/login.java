package com.runajian2202.page;

import com.runajian2202.Model.User;
import com.runajian2202.dao.LockDao;
import com.runajian2202.dao.UserDao;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * 教师登录界面
 * swing+JFrame
 *
 * @author qingqian
 */

public class login extends JFrame implements KeyListener, ActionListener {
    JLabel jlb0, jlb1, jlb2;
    JTextField jtf;
    JPasswordField jpf;
    JButton jbt0, jbt1,jbt2,jbt3;
    /*
     * 容器*/
    JPanel index;

    int count = 3;

    public login() {

        setSize(420, 600);
        setTitle("教师登录");
        //标题
        jlb0 = new JLabel("欢迎登录", JLabel.CENTER);
        //控件位置和宽高
        jlb0.setBounds(0, 40, 400, 50);
        //设置字体:1:BOLD加粗,0:PLAIN不加粗
        jlb0.setFont(new Font("楷体", Font.BOLD, 40));
        //用户名
        jlb1 = new JLabel("用户名:");
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

        jbt3=new JButton("切换学生端");
        jbt3.setBounds(0,520,110,40);
        jbt3.setFont(new Font("等线",Font.BOLD, 10));
        jbt3.addActionListener(this);
        jbt3.setFocusPainted(false);
        jbt3.setBorderPainted(false);
        jbt3.setBackground(new Color(255, 240, 245));


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
        index.add(jbt3);


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
        if (e.getSource() == jbt0) {
            String userName = jtf.getText().trim();
            String password = new String(jpf.getPassword());
            if (StringUtil.isEmpty(userName)) {
                JOptionPane.showMessageDialog(null, "请先输入账号");
                return;
            }
            if (StringUtil.isEmpty(password)) {
                JOptionPane.showMessageDialog(null, "密码不能为空");
                return;
            }
            //检查是否被锁定
            Connection conn2=JdbcUtil.getConn();
            try {
                //到期解锁
                //获取当前时间
                LocalDateTime now = LocalDateTime.now();
                //检查该用户名是否锁定
                ResultSet rs3=LockDao.checkLock(conn2,jtf.getText());
                if (rs3!=null&&rs3.next()) {
                    //查询锁定时间,超过三分钟就删除锁定记录,并且登录次数恢复3次
                    LocalDateTime lock_time = rs3.getTimestamp(2).toLocalDateTime();
                    //获取当前时间和锁定时间的差
                    Duration duration=Duration.between(now,lock_time);
                    //接收一个锁定时间差值
                    long minutes= duration.toMinutes();
                    if (Math.abs(minutes)>=3){
                        //删除锁定记录,执行登录
                        LockDao.unlockUrename(conn2, jtf.getText());
                        conn2.close();
                        count=3;
                        User user = new User();
                        user.setUserName(userName);
                        user.setPassword(password);
                        Connection conn = JdbcUtil.getConn();
                        try {
                            ResultSet rs = UserDao.login(conn, user);
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(null, "登录成功");
                                new MainFrame(user.getUserName());
                                conn.close();
                                this.dispose();
                            }else {
                                count--;
                                JOptionPane.showMessageDialog(null,"账号或者密码错误,剩余"+count+"次机会");
                                if (count==0){
                                    JOptionPane.showMessageDialog(null,"账号或者密码错误3次,已锁定");
                                    //获取当前锁定时间
                                    LocalDateTime now2 = LocalDateTime.now();
                                    //写入锁定的用户名和锁定时间
                                    LockDao.lockUrename(conn,userName,now2);
                                    jbt0.setEnabled(false);
                                    conn.close();
                                }

                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else {
                        //获得解锁倒计时
                        Connection conn3 = JdbcUtil.getConn();
                        //获取当前时间
                        LocalTime now3 = LocalTime.now();
                       ResultSet rs4= LockDao.checkLock(conn3, userName);
                       rs4.next();
                       //获取锁定时间
                       LocalTime lockTime= rs4.getTime("lock_time").toLocalTime();
                       Duration duration1=Duration.between(lockTime,now3);
                       //计算相差秒数
                       long seconds=duration1.getSeconds();
                       long s=180-seconds;
                        //写入倒计时
                        LockDao.lockSeconds(conn3,userName,s);
                        //查询倒计时
                        ResultSet rs5=LockDao.checkLock(conn3,userName);
                        rs5.next();
                        long s2=rs5.getLong("lock_seconds");
                        JOptionPane.showMessageDialog(null,s2+"秒后解锁");
                        conn3.close();
                    }
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            Connection conn = JdbcUtil.getConn();
            try {
                ResultSet rs = UserDao.login(conn, user);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "登录成功");
                    new MainFrame(user.getUserName());
                    conn.close();
                    this.dispose();
                }else {
                    jpf.setText("");
                    count--;
                    JOptionPane.showMessageDialog(null,"账号或者密码错误,剩余"+count+"次机会");
                    if (count==0){
                        JOptionPane.showMessageDialog(null,"账号或者密码错误3次,已锁定");
                        //获取当前锁定时间
                        LocalDateTime now = LocalDateTime.now();
                        //写入锁定
                        LockDao.lockUrename(conn,userName,now);
                        conn.close();
                    }

                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == jbt1) {
            this.dispose();
            new Register();
        }if (e.getSource()==jbt2){
            this.dispose();
            new ChangePassword(jtf.getText());
        }
        if (e.getSource() ==jbt3){
            //切换学生端
            new StuLogin();
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //键盘监听,回车登录,一键爽
        //代码和鼠标监听一致
        if (e.getKeyChar() == KeyEvent.VK_ENTER){
            String userName = jtf.getText().trim();
        String password = new String(jpf.getPassword());
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "请先输入账号");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
            return;
        }
        //检查是否被锁定
        Connection conn2 = JdbcUtil.getConn();
        try {
            //到期解锁
            LocalDateTime now = LocalDateTime.now();
            ResultSet rs3 = LockDao.checkLock(conn2, jtf.getText());
            if (rs3 != null && rs3.next()) {
                //查询锁定时间,获取锁定时间,超过三分钟就删除锁定记录,并且错误次数清零
                LocalDateTime lock_time = rs3.getTimestamp(2).toLocalDateTime();
                Duration duration = Duration.between(now, lock_time);
                long minutes = duration.toMinutes();
                if (Math.abs(minutes) >= 3) {
                    //删除锁定记录,执行登录
                    LockDao.unlockUrename(conn2, jtf.getText());
                    conn2.close();
                    count = 3;
                    User user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);
                    Connection conn = JdbcUtil.getConn();
                    try {
                        ResultSet rs = UserDao.login(conn, user);
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "登录成功");
                            new MainFrame(user.getUserName());
                            conn.close();
                            this.dispose();
                        } else {
                            count--;
                            jpf.setText("");
                            JOptionPane.showMessageDialog(null, "账号或者密码错误,剩余" + count + "次机会");
                            if (count == 0) {
                                JOptionPane.showMessageDialog(null, "账号或者密码错误三次,已锁定");
                                //获取当前锁定时间
                                LocalDateTime now2 = LocalDateTime.now();
                                LockDao.lockUrename(conn, userName, now2);
                                conn.close();
                            }

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    //获得解锁倒计时
                    Connection conn3 = JdbcUtil.getConn();
                    //获取当前时间
                    LocalTime now3 = LocalTime.now();
                    ResultSet rs4= LockDao.checkLock(conn3, userName);
                    rs4.next();
                    //获取锁定时间
                    LocalTime lockTime= rs4.getTime("lock_time").toLocalTime();
                    Duration duration1=Duration.between(lockTime,now3);
                    //计算相差秒数
                    long seconds=duration1.getSeconds();
                    long s=180-seconds;
                    //写入倒计时
                    LockDao.lockSeconds(conn3,userName,s);
                    //查询倒计时
                    ResultSet rs5=LockDao.checkLock(conn3,userName);
                    rs5.next();
                    long s2=rs5.getLong("lock_seconds");
                    JOptionPane.showMessageDialog(null,s2+"秒后解锁");
                    conn3.close();
                }
                return;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        Connection conn = JdbcUtil.getConn();
        try {
            ResultSet rs = UserDao.login(conn, user);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "登录成功");
                new MainFrame(user.getUserName());
                conn.close();
                this.dispose();
            } else {
                count--;
                JOptionPane.showMessageDialog(null, "账号或者密码错误,剩余" + count + "次机会");
                if (count == 0) {
                    JOptionPane.showMessageDialog(null, "账号或者密码错误三次,已锁定");
                    //获取当前锁定时间
                    LocalDateTime now = LocalDateTime.now();
                    LockDao.lockUrename(conn, userName, now);
                    conn.close();
                }

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    }
    @Override
    public void keyReleased(KeyEvent e) {}

}

