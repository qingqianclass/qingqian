package com.runajian2202.tools;

import java.io.*;
import javax.swing.*;
import javax.swing.text.Style;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class inputSheet {
    public static void saveTableToExcel(JTable table) {
        String sheetName = "成绩单"; // 表名
        String fileName = "成绩单.xls"; // 文件名

        // 创建一个 Excel 文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // 写表头,创建第一行
        HSSFRow rowHeader = sheet.createRow(0);
        //遍历所有列 table.getColumnCount()返回列数
        for (int j = 0; j < table.getColumnCount(); j++) {
            //CellType.STRING表示单元格类型为字符串,createCell给第一行创建单元格,j表示列数
            HSSFCell cell = rowHeader.createCell(j, CellType.STRING);
            //table.getColumnName(j)获取第j列的列名,并用cell.setCellValue()方法写入
            cell.setCellValue(table.getColumnName(j));
            cell.setCellStyle(style);
        }

        // 写数据
        //外层for循环,遍历所有行
        for (int i = 0; i < table.getRowCount(); i++) {
            //使用createRow()创建i+1行,应为第一行被用作了表头,
            HSSFRow row = sheet.createRow(i + 1);
            //内循环,遍历所有列
            for (int j = 0; j < table.getColumnCount(); j++) {
                //CellType.NUMERIC指定单元格为数值类型,所有createCell()创建单元格
                HSSFCell cell = row.createCell(j, CellType.NUMERIC);

                //getValueAt(i, j)表示获取第i行第j列的数据
                cell.setCellValue(String.valueOf(table.getValueAt(i, j).toString()));

                cell.setCellStyle(style);
            }
        }

        // 弹出保存路径对话框
        JFileChooser fileChooser = new JFileChooser();

        //打开一个打开文件的对话框,标题为保存文件
        fileChooser.setDialogTitle("保存文件");

        //设置选择的文件类型,FILES_ONLY（只能选文件）、DIRECTORIES_ONLY（只能选文件夹）、FILES_AND_DIRECTORIES
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //打开一个保存文件对话框,如果选择了保存,就返回APPROVE_OPTION
        int result = fileChooser.showSaveDialog(null);

        //JFileChooser定义了三个静态常量用于表示用户操作的返回值：
        //APPROVE_OPTION：表示确认或保存
        //CANCEL_OPTION：表示取消或关闭
        //ERROR_OPTION：表示出现错误
        if (result == JFileChooser.APPROVE_OPTION) {

            //getAbsolutePath()返回绝对路径字符串
            String path = fileChooser.getSelectedFile().getAbsolutePath();

            // 拼接文件路径
            //File.separator表示文件分隔符,判断文件路径是否已分隔符\结尾,如果不是就 path += File.separator,加上\
            if (!path.endsWith(File.separator)) {
                path += File.separator;
            }
            //加上文件名成绩单.xls
            path += fileName;

            // 将 Excel文件保存到指定路径
            try {
                //创建输出流对象outputStream
                FileOutputStream outputStream = new FileOutputStream(path);
                //将excel文件写入输出流
                workbook.write(outputStream);
                outputStream.close();
                JOptionPane.showMessageDialog(null, "文件已保存至：" + path, "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}