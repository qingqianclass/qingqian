package com.runajian2202.page;

import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.StringUtil;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qingqian
 * 学生成绩查询主页
 */
public class StuMainFrame extends JFrame implements ActionListener {
    JPanel jpl;
    JLabel jbl,jbl2, jbl3,jbl4,jbl5, jbl6,jbl7;
    JTextField jtf1,jtf2, jtf3,jtf4, jtf5,jtf6;
    JButton jbt;

    public StuMainFrame() {
        setTitle("学生成绩查询系统");
        setSize(500, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        jbl = new JLabel("欢迎查询成绩",JLabel.CENTER);
        jbl.setBounds(40, 40, 400, 50);
        jbl.setFont(new Font("楷体",Font.BOLD, 50));

        jbl2=new JLabel("学号:");
        jbl2.setBounds(40,150,240,40);
        jbl2.setFont(new Font("微软雅黑", Font.BOLD, 30));

        jtf1=new JTextField();
        jtf1.setBounds(120,150,240,40);
        jtf1.setFont(new Font("微软雅黑",Font.BOLD, 30));

        jbl3=new JLabel("姓名:");
        jbl3.setBounds(40,220,240,40);
        jbl3.setFont(new Font("微软雅黑", Font.BOLD, 30));

        jtf2=new JTextField();
        jtf2.setBounds(120,220,240,40);
        jtf2.setFont(new Font("微软雅黑",Font.BOLD, 30));
        jtf2.setEditable(false);

        jbl4=new JLabel("语文:");
        jbl4.setBounds(40,290,240,40);
        jbl4.setFont(new Font("微软雅黑",Font.BOLD, 30));

        jtf3 = new JTextField();
        jtf3.setBounds(120,290,240,40);
        jtf3.setFont(new Font("微软雅黑",Font.BOLD, 30));
        jtf3.setEditable(false);

        jbl5 = new JLabel("数学:");
        jbl5.setBounds(40,360,240,40);
        jbl5.setFont(new Font("微软雅黑", Font.BOLD, 30));

        jtf4=new JTextField();
        jtf4.setBounds(120,360,240,40);
        jtf4.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jtf4.setEditable(false);

        jbl6=new JLabel("英语");
        jbl6.setBounds(40,440,240,40);
        jbl6.setFont(new Font("微软雅黑", Font.BOLD, 30));

        jtf5=new JTextField();
        jtf5.setBounds(120,440,240,40);
        jtf5.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jtf5.setEditable(false);

        jbl7 = new JLabel("总分:");
        jbl7.setBounds(40,510,240,40);
        jbl7.setFont(new Font("微软雅黑", Font.BOLD, 30));

        jtf6=new JTextField();
        jtf6.setBounds(120,510,240,40);
        jtf6.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jtf6.setEditable(false);

        jbt=new JButton("查询");
        jbt.setBounds(120,650,240,40);
        jbt.setFocusPainted(false);
        jbt.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jbt.addActionListener(this);


        jpl=new JPanel();
        jpl.setLayout(null);
        jpl.add(jbl);
        jpl.add(jbl2);
        jpl.add(jtf1);
        jpl.add(jbl3);
        jpl.add(jtf2);
        jpl.add(jbl4);
        jpl.add(jtf3);
        jpl.add(jbl5);
        jpl.add(jtf4);
        jpl.add(jbl6);
        jpl.add(jtf5);
        jpl.add(jbl7);
        jpl.add(jtf6);
        jpl.add(jbt);
        this.add(jpl);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbt){
            if (StringUtil.isEmpty(jtf1.getText().trim())){
                JOptionPane.showMessageDialog(null,"学号为空");
                return;
            }
            Connection conn= JdbcUtil.getConn();
            try {
                ResultSet rs= StuDao.stuIdChecked(conn,jtf1.getText());
                if (!rs.next()){
                    JOptionPane.showMessageDialog(null,"学号不存在");
                    conn.close();
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                //查找学号对应老师
                ResultSet rs2=StuDao.findTeacherName(conn,jtf1.getText());
                //对学号为空进行判断
                if (!rs2.next()){
                    JOptionPane.showMessageDialog(null,"学号不存在");
                    conn.close();
                    return;
                }
                String teacherName = rs2.getString("teacherName");
                ResultSet rs3 = StuDao.findGrade(conn,jtf1.getText(),teacherName);
                if (rs3.next()){
                    jtf2.setText(rs3.getString("sname"));
                    jtf3.setText(rs3.getString("chinese"));
                    jtf4.setText(rs3.getString("math"));
                    jtf5.setText(rs3.getString("english"));
                    conn.close();
                }
                //总分计算,使用BigDecimal解决double类型精度丢失
                BigDecimal chinese1 = new BigDecimal(jtf3.getText());
                BigDecimal math1 = new BigDecimal(jtf4.getText());
                BigDecimal english1 = new BigDecimal(jtf5.getText());
                BigDecimal sum;
                sum=chinese1.add(math1).add(english1);
                //然而BigDecimal只是解决精度问题的手段，获取double数据才是我们的目的
                double sum2 = sum.doubleValue();
                jtf6.setText(String.valueOf(sum2));

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
    }
}

