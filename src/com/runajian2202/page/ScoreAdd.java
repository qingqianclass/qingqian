package com.runajian2202.page;

import com.runajian2202.Model.User;
import com.runajian2202.dao.ScoreDao;
import com.runajian2202.Model.Score;
import com.runajian2202.dao.StuDao;
import com.runajian2202.tools.DuplicateCheck;

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
 * 录入成绩主页
 *
 * @author qingqian
 */
public class ScoreAdd extends JPanel implements ActionListener {
    JLabel jlb0, jlb1, jlb2, jlb3, jlb4, jlb5;
    JTextField jtf0, jtf1, jtf2, jtf3, jtf4,jtf5;
    JButton jbt0, jbt1;

    public ScoreAdd(String sheetName) {
        setSize(906, 680);
        setLayout(null);

        setSize(906, 680);
        setLayout(null);
        jlb0 = new JLabel("录入学生成绩", JLabel.CENTER);
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


        jlb3 = new JLabel("语文:");
        jlb3.setBounds(200, 270, 80, 40);
        jlb3.setFont(new Font("微软雅黑", Font.BOLD, 17));

        jtf2 = new JTextField();
        jtf2.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf2.setBounds(280, 270, 440, 40);

        jlb4 = new JLabel("数学");
        jlb4.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb4.setBounds(200, 340, 80, 40);

        jtf3 = new JTextField();
        jtf3.setBounds(280, 340, 440, 40);
        jtf3.setFont(new Font("微软雅黑", Font.BOLD, 17));


        jlb5 = new JLabel("英语");
        jlb5.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jlb5.setBounds(200, 410, 80, 40);

        jtf4 = new JTextField();
        jtf4.setFont(new Font("微软雅黑", Font.BOLD, 17));
        jtf4.setBounds(280, 410, 440, 40);

        jbt0 = new JButton("添加");
        jbt0.setBackground(Color.GREEN);
        jbt0.setBounds(280, 500, 200, 40);
        jbt0.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt0.addActionListener(this);
        //去掉文字框选
        jbt0.setFocusPainted(false);

        jbt1 = new JButton("重置");
        jbt1.setFont(new Font("微软雅黑", Font.BOLD, 14));
        jbt1.setBounds(520, 500, 200, 40);
        jbt1.setBackground(Color.magenta);
        jbt1.addActionListener(this);
        jbt1.setFocusPainted(false);


        //中转用户名
        jtf5 = new JTextField(sheetName);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbt1) {
            jtf0.setText("");
            jtf1.setText("");
            jtf2.setText("");
            jtf3.setText("");
            jtf4.setText("");
        }
        if (e.getSource() == jbt0) {
            if (StringUtil.isEmpty(jtf0.getText())) {
                JOptionPane.showMessageDialog(null, "学号不能为空");
                return;
            }
            if (StringUtil.isEmpty(jtf1.getText())) {
                JOptionPane.showMessageDialog(null, "姓名不能为空");
                return;
            }
            if (StringUtil.isEmpty(jtf2.getText())) {
                JOptionPane.showMessageDialog(null, "语文成绩不能为空");
                return;
            }
            if (StringUtil.isEmpty(jtf3.getText())) {
                JOptionPane.showMessageDialog(null, "数学成绩不能为空");
                return;
            }
            if (StringUtil.isEmpty(jtf4.getText())) {
                JOptionPane.showMessageDialog(null, "英语成绩不能为空");
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
            Score score = new Score();
            //查重并且对添加错误数据格式进行异常处理
            try {
                int idCheck = Integer.parseInt(jtf0.getText());
                score.setSid(idCheck);
                Connection conn1 = JdbcUtil.getConn();
                try {
                    //f5.getText()用户名,也是就要添加的表的表名
                    //这里查两次,是因为我们数据库,每个老师录入的成绩单独成表,还需要查询idtable表
                    ResultSet rs1 = DuplicateCheck.addTools(conn1, score,jtf5.getText());
                    ResultSet rs2= StuDao.stuIdChecked_as_idtable(conn1,jtf0.getText());
                    if (rs2.next()){
                        JOptionPane.showMessageDialog(null,"成绩库中已存在该学号");
                        jtf0.setText("");
                        conn1.close();
                        return;
                    }
                    if (rs1.next()) {
                        JOptionPane.showMessageDialog(null, "成绩库中已存在该学号");
                        jtf0.setText("");
                        conn1.close();
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                int id = Integer.parseInt(jtf0.getText());
                String name = jtf1.getText();
                double chinese = Double.parseDouble(jtf2.getText());
                double math = Double.parseDouble(jtf3.getText());
                double english = Double.parseDouble(jtf4.getText());

                //总分
                //总分计算,使用BigDecimal解决double类型精度丢失
                BigDecimal chinese1 = new BigDecimal(jtf2.getText());
                BigDecimal math1 = new BigDecimal(jtf3.getText());
                BigDecimal english1 = new BigDecimal(jtf4.getText());
                BigDecimal sum;
                sum=chinese1.add(math1).add(english1);
                //然而BigDecimal只是解决精度问题的手段，获取double数据才是我们的目的
                double sum2 = sum.doubleValue();

                score.setSname(name);
                score.setSid(id);
                score.setChinese(chinese);
                score.setEnglish(english);
                score.setMath(math);
                score.setSum(sum2);
                //数据库
                Connection conn = JdbcUtil.getConn();
                try {
                    int rs = ScoreDao.add(conn, score,jtf5.getText());
                    //插入学号和老师用户名到idtable表,绑定学号和老师用户名,后期学生端查询成绩才能对应学号和成绩
                    StuDao.insertId_and_teacherName(conn,jtf0.getText(),jtf5.getText());
                    if (rs != 0) {
                        JOptionPane.showMessageDialog(null, "录入成功");
                        jtf0.setText("");
                        jtf1.setText("");
                        jtf2.setText("");
                        jtf3.setText("");
                        jtf4.setText("");
                        score.setSum(0);
                        conn.close();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "录入的数据格式不正确");
                throw new RuntimeException(ex);
            }
        }
    }
}