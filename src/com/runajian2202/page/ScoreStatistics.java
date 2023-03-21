package com.runajian2202.page;

import com.runajian2202.Model.User;
import com.runajian2202.dao.ScoreDao;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ScoreStatistics extends JPanel {
    JScrollPane jsp;
    JLabel jlb0;
    JTable jt;
    Object[][] data;
    Object[] columns;

    public ScoreStatistics(String sheetname) {
            setSize(906,680);
            setLayout(null);
            jlb0 = new JLabel("学生信息统计",JLabel.CENTER);
            jlb0.setBounds(0,30,906,50);
            jlb0.setFont(new Font("楷体",Font.BOLD, 40));
            //表头
        columns=new Object[] {
                "统计/科目","语文","数学","英语"
            };
        //假数
        data=new Object[][] {};
        jt=new JTable();
        jt.setModel(new DefaultTableModel(data,columns));
        //表头
        jt.getTableHeader().setPreferredSize(new Dimension(1, 40));
        jt.getTableHeader().setFont(new Font("微软雅黑", 1, 16));
        //行高,字体
        jt.setRowHeight(30);
        jt.setFont(new Font("等线", Font.PLAIN,15));
        //内容居中
        DefaultTableCellRenderer tcr=new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        jt.setDefaultRenderer(Object.class, tcr);
        //数据操作
        DefaultTableModel  jtm=(DefaultTableModel) jt.getModel();
        jtm.setRowCount(0);
        Connection conn= JdbcUtil.getConn();
        try {
            User user=new User();
            ResultSet rs = ScoreDao.sum(conn,sheetname);
            while (rs.next()){
                Vector keys = new Vector();
                keys.add(0,"总分");
                keys.add(rs.getFloat(1));
                keys.add(rs.getFloat(2));
                keys.add(rs.getFloat(3));
                jtm.addRow(keys);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet rs2 = ScoreDao.average(conn,sheetname);
            while (rs2.next()){
                Vector keys = new Vector();
                keys.add(0,"平均分");
                keys.add(rs2.getFloat(1));
                keys.add(rs2.getFloat(2));
                keys.add(rs2.getFloat(3));
                jtm.addRow(keys);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet rs3=ScoreDao.maximum(conn,sheetname);
            while (rs3.next()){
                Vector keys = new Vector();
                keys.add(0,"最高分");
                keys.add(rs3.getFloat(1));
                keys.add(rs3.getFloat(2));
                keys.add(rs3.getFloat(3));
                jtm.addRow(keys);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultSet rs4=ScoreDao.minimum(conn,sheetname);
            while (rs4.next()){
                Vector keys = new Vector();
                keys.add(0,"最低分");
                keys.add(rs4.getFloat(1));
                keys.add(rs4.getFloat(2));
                keys.add(rs4.getFloat(3));
                jtm.addRow(keys);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //添加滚动条
        jsp=new JScrollPane(jt);
        jsp.setBounds(50,150,806,200);
        add(jlb0);
        add(jsp);
        }
    }

