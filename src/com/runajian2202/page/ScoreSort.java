package com.runajian2202.page;

import com.runajian2202.Model.Score;
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

/**
 * @author qingqian
 * 成绩排序
 */
public class ScoreSort extends JPanel {
    JScrollPane jsp;
    JTable jt;
    Object[][] data;
    Object[] columns;

    public ScoreSort(String sheetname) {
        setSize(906, 680);
        setLayout(null);
        columns = new Object[]{"学号", "姓名", "语文", "数学", "英语","总分","排名"};
        data=new Object[][]{};
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
            ResultSet rs = ScoreDao.sort(conn,sheetname);
            //排名计数
            int count=0;
            while (rs.next()) {
                count++;
                Vector v = new Vector();
                v.add(rs.getInt(1));
                v.add(rs.getString(2));
                v.add(rs.getFloat(3));
                v.add(rs.getFloat(4));
                v.add(rs.getFloat(5));
                v.add(rs.getDouble(6));
                v.add(count);
                jtm.addRow(v);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //添加滚动条
        jsp = new JScrollPane(jt);
        jsp.setBounds(0, 0, 906, 680);
        add(jsp);
    }
}
