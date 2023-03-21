package com.runajian2202.page;

import com.runajian2202.Model.User;
import com.runajian2202.dao.ScoreDao;
import com.runajian2202.Model.Score;
import com.runajian2202.util.JdbcUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ScoreList extends JPanel {
    /* JScrollPane滚动条
    * JTable 表格
    * 表头
    * Object[][] data;
    *Object[] columns;
    * */
    JScrollPane jsp;
    JTable jt;
    Object[][] data;
    Object[] columns;

    public ScoreList(String s) {
        setSize(906, 680);
        setLayout(null);
        columns = new Object[]{"学号", "姓名", "语文", "数学", "英语","总分"};
        data = new Object[][]{};
        jt = new JTable();
        jt.setModel(new DefaultTableModel(data, columns));

        //设置表头
        jt.getTableHeader().setPreferredSize(new Dimension(1, 40));
        jt.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        //设置行高,字体
        jt.setFont(new Font("等线", Font.PLAIN, 15));
        //行高
        jt.setRowHeight(30);
        //表格内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        jt.setDefaultRenderer(Object.class, tcr);
        //获取jt的默认样式,并保存到jtm
        DefaultTableModel jtm = (DefaultTableModel) jt.getModel();
        jtm.setRowCount(0);
        Connection conn = JdbcUtil.getConn();
        try {
            User user=new User();
            ResultSet rs = ScoreDao.list(conn, new Score(),s);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt(1));
                v.add(rs.getString(2));
                v.add(rs.getFloat(3));
                v.add(rs.getFloat(4));
                v.add(rs.getFloat(5));
                v.add(rs.getDouble(6));
                jtm.addRow(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //添加滚动条
        jsp = new JScrollPane(jt);
        jsp.setBounds(0, 0, 906, 680);
        add(jsp);
    }
}
