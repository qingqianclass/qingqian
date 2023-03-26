package com.runajian2202.page;

import com.runajian2202.Model.Score;
import com.runajian2202.Model.User;
import com.runajian2202.dao.ScoreDao;
import com.runajian2202.tools.ScoreRange;
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
 * 学生成绩修改类
 */
public class ScoreUpdate extends JPanel implements ActionListener {
    JLabel jlb0, jlb1, jlb2, jlb3, jlb4, jlb5;
    JTextField jtf0, jtf1, jtf2, jtf3, jtf4,jtf5;
    JButton jbt0, jbt1;

    public ScoreUpdate(String name) {
        setSize(906,680);
        setLayout(null);

        setSize(906, 680);
        setLayout(null);
        jlb0 = new JLabel("修改学生成绩", JLabel.CENTER);
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
        jbt0.setBounds(280, 500, 200, 40);
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt0.addActionListener(this);
        jbt0.setFocusPainted(false);

        jbt1 = new JButton("修改");
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt1.setBounds(520, 500, 200, 40);
        jbt1.addActionListener(this);
        jbt1.setFocusPainted(false);
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
            //对学号格式错误进行异常处理提醒
            try {
            if (StringUtil.isEmpty(jtf0.getText())) {
                JOptionPane.showMessageDialog(null, "学号为空");
                return;
            }
            Score score = new Score();
            User user = new User();
                score.setSid(Integer.parseInt(jtf0.getText()));
            Connection conn = JdbcUtil.getConn();
            try {
                ResultSet rs = ScoreDao.list(conn, score,jtf5.getText());
                if (rs.next()) {
                    jtf1.setText(rs.getString("sname"));
                    jtf2.setText(rs.getString("chinese"));
                    jtf3.setText(rs.getString("math"));
                    jtf4.setText(rs.getString("english"));
                    jtf2.setEditable(true);
                    jtf3.setEditable(true);
                    jtf4.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(null, "没有找到此人");
                    jtf0.setText("");
                    jtf1.setText("");
                    jtf2.setText("");
                    jtf3.setText("");
                    jtf4.setText("");
                    rs.close();
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }catch (NumberFormatException ex1){
                JOptionPane.showMessageDialog(null,"学号格式不正确,请检查");
            }
        }
        if (e.getSource() ==jbt1) {
            //对提交的数据格式错误进行异常处理
            try {

            Score score = new Score();
            User user = new User();
            if (StringUtil.isEmpty(jtf0.getText())) {
                JOptionPane.showMessageDialog(null, "学号为空");
                return;
            }if(ScoreRange.chekRange(Double.parseDouble(jtf2.getText()))){
                    JOptionPane.showMessageDialog(null,"成绩必须大于0小于150");
                    jtf2.setText("");
                    return;

                }if(ScoreRange.chekRange(Double.parseDouble(jtf3.getText()))){
                    JOptionPane.showMessageDialog(null,"成绩必须大于0小于150");
                    jtf3.setText("");
                    return;

                }if(ScoreRange.chekRange(Double.parseDouble(jtf4.getText()))){
                    JOptionPane.showMessageDialog(null,"成绩必须大于0小于150");
                    jtf4.setText("");
                    return;
                }
            try {
                //总分计算,使用BigDecimal解决double类型精度丢失
                BigDecimal chinese1 = new BigDecimal(jtf2.getText());
                BigDecimal math1 = new BigDecimal(jtf3.getText());
                BigDecimal english = new BigDecimal(jtf4.getText());
                BigDecimal sum;
                sum=chinese1.add(math1).add(english);
                //BigDecimal只是解决精度问题的手段，获取double数据才是我们的目的
                double sum2 = sum.doubleValue();

                int i=JOptionPane.showConfirmDialog(null,"确认修改吗");
                if (i!=0){
                    return;
                }
                score.setSid(Integer.parseInt(jtf0.getText()));
                score.setSname(jtf1.getText());
                score.setChinese(Double.parseDouble(jtf2.getText()));
                score.setMath(Double.parseDouble(jtf3.getText()));
                score.setEnglish(Double.parseDouble(jtf4.getText()));
                score.setSum(sum2);
                Connection conn = JdbcUtil.getConn();
                int rs = ScoreDao.Update(conn, score,jtf5.getText());
                if (rs != 0) {
                    JOptionPane.showMessageDialog(null, "修改成功");
                    jtf0.setText("");
                    jtf1.setText("");
                    jtf2.setText("");
                    jtf3.setText("");
                    jtf4.setText("");
                    score.setSum(0);
                    jtf2.setEditable(false);
                    jtf3.setEditable(false);
                    jtf4.setEditable(false);
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }catch (NumberFormatException ex2){
                JOptionPane.showMessageDialog(null,"数据格式不正确,请检查");
                throw new RuntimeException(ex2);
            }
        }

    }
}
