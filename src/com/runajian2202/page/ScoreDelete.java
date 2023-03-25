package com.runajian2202.page;

import com.runajian2202.Model.Score;
import com.runajian2202.Model.User;
import com.runajian2202.dao.ScoreDao;
import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.StringUtil;
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
 * 删除成绩
 */
public class ScoreDelete extends JPanel implements ActionListener {
    JLabel jlb0, jlb1, jlb2, jlb3, jlb4, jlb5;
    JTextField jtf0, jtf1, jtf2, jtf3, jtf4,jtf5;
    JButton jbt0, jbt1;

    public ScoreDelete(String name) {
        setSize(906, 680);
        setLayout(null);
        jlb0 = new JLabel("删除学生成绩", JLabel.CENTER);
        jlb0.setFont(new Font("楷体", Font.BOLD, 40));
        jlb0.setBounds(0, 30, 906, 50);

        jlb1 = new JLabel("学号:");
        jlb1.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb1.setBounds(200, 130, 80, 40);

        jtf0 = new JTextField();
        jtf0.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf0.setBounds(280, 130, 440, 40);

        jlb2 = new JLabel("姓名:");
        jlb2.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb2.setBounds(200, 200, 80, 40);

        jtf1 = new JTextField();
        jtf1.setBounds(280, 200, 440, 40);
        jtf1.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf1.setEditable(false);

        jlb3 = new JLabel("语文:");
        jlb3.setBounds(200, 270, 80, 40);
        jlb3.setFont(new Font("微软雅黑", Font.BOLD, 17));

        jtf2 = new JTextField();
        jtf2.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf2.setBounds(280, 270, 440, 40);
        jtf2.setEditable(false);

        jlb4 = new JLabel("数学");
        jlb4.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb4.setBounds(200, 340, 80, 40);

        jtf3 = new JTextField();
        jtf3.setBounds(280, 340, 440, 40);
        jtf3.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf3.setEditable(false);

        jlb5 = new JLabel("英语");
        jlb5.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb5.setBounds(200, 410, 80, 40);

        jtf4 = new JTextField();
        jtf4.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf4.setBounds(280, 410, 440, 40);
        jtf4.setEditable(false);

        jbt0 = new JButton("查询");
        jbt0.setBackground(Color.PINK);
        jbt0.setBounds(280, 500, 200, 40);
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt0.addActionListener(this);
        jbt0.setFocusPainted(false);

        jbt1 = new JButton("删除");
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt1.setBounds(520, 500, 200, 40);
        jbt1.setBackground(new Color(255, 142, 133));
        jbt1.addActionListener(this);
        jbt1.setFocusPainted(false);
        //获取用户名:成绩表表名及用户名,土办法
        jtf5=new JTextField(name);


        add(jlb0);
        add(jlb1);
        add(jlb2);
        add(jlb3);
        add(jlb4);
        add(jlb5);
        add(jbt0);
        add(jbt1);
        add(jtf0);
        add(jtf1);
        add(jtf2);
        add(jtf3);
        add(jtf4);
        add(jtf5);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbt0) {
            if (StringUtil.isEmpty(jtf0.getText())) {
                JOptionPane.showMessageDialog(null, "学号为空");
                return;
            }
            Score score = new Score();
            User user=new User();
            score.setSid(Integer.parseInt(jtf0.getText()));
            Connection conn = JdbcUtil.getConn();
            try {
                ResultSet rs = ScoreDao.list(conn, score,jtf5.getText());
                if (rs.next()) {
                    jtf1.setText(rs.getString("sname"));
                    jtf2.setText(rs.getString("chinese"));
                    jtf3.setText(rs.getString("math"));
                    jtf4.setText(rs.getString("english"));
                } else {
                    JOptionPane.showMessageDialog(null, "没有找到此人");
                    jtf0.setText("");
                    rs.close();
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == jbt1) {
            if (StringUtil.isEmpty(jtf0.getText())) {
                JOptionPane.showMessageDialog(null, "学号为空");
                return;
            }
            Score score = new Score();
            score.setSid(Integer.parseInt(jtf0.getText()));
            Connection conn = JdbcUtil.getConn();
            if (jtf1.getText()!=null){
                JOptionPane.showMessageDialog(null,"请先查询");
                return;
            }
            try {
                int i= JOptionPane.showConfirmDialog(null, "确认删除" );
             if (i==0) {
                 //删除idTable的对应记录
                 StuDao.delID_and_teacherName(conn,jtf0.getText());
                 //jtf5.getText()表名
                 int rs = ScoreDao.delete(conn, score,jtf5.getText());
                 if (rs != 0) {
                     JOptionPane.showMessageDialog(null, "删除成功");
                     jtf0.setText("");
                     jtf1.setText("");
                     jtf2.setText("");
                     jtf3.setText("");
                     jtf4.setText("");
                     conn.close();
                 } else {
                     JOptionPane.showMessageDialog(null, "没有找到此人");
                     conn.close();
                 }
             }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
