package cn.baizhi;

import cn.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestPOI {
    @Test
    public void test() throws IOException {
        //创建excel
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作表
        HSSFSheet sheet1 = workbook.createSheet("学生信息表");
        HSSFSheet sheet2 = workbook.createSheet("学生成绩表");

        //学生信息表创建一行
        Row row = sheet1.createRow(1);//第二行
        //创建列 单元格
        Cell cell = row.createCell(2);//单元格对象
        cell.setCellValue("张三");

        workbook.write(new FileOutputStream("D:\\poi.xls"));
    }

    @Test
    public void test2(){
        List<Student> list = new ArrayList<>();
        list.add(new Student("1","张三",20,new Date()));
        list.add(new Student("2","李四",21,new Date()));
        list.add(new Student("3","王五",22,new Date()));
        list.add(new Student("4","赵六",23,new Date()));

        HSSFWorkbook workbook = new HSSFWorkbook();

        //构建字体样式
        HSSFFont font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)10);  //字号
        font.setFontName("微软雅黑");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建字体样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);     //将字体样式引入
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);  //文字居中

        //创建样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        //创建日期样式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();

        //样式对象设置日期样式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));

        HSSFSheet sheet = workbook.createSheet("学生信息表");//当前表空间
        //创建第一行 第一个单元格 并导入值
        HSSFCell cell1 = sheet.createRow(0).createCell(0);
        cell1.setCellStyle(cellStyle1);//当前单元格设置字体样式
        cell1.setCellValue("学生信息");

        //合并列
        //要合并的列  合并对象     参数：行开始，行结束，列开时，列结束
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(region);


        //设置单元格宽度   参数：列索引，列宽
        sheet.setColumnWidth(3, 20*256);


        HSSFRow row = sheet.createRow(1);//创建行
        String[] count={"id","姓名","年龄","生日"};
        for(int i = 0;i<count.length;i++){
            row.createCell(i).setCellValue(count[i]);//创建单元格
        }
        for(int i = 0;i<list.size();i++){
            HSSFRow row1 = sheet.createRow(i + 2);//创建每一行
           row1.createCell(0).setCellValue(list.get(i).getId());
           row1.createCell(1).setCellValue(list.get(i).getName());
           row1.createCell(2).setCellValue(list.get(i).getAge());
            HSSFCell cell = row1.createCell(3);//创建生日单元格
            cell.setCellStyle(cellStyle);//为日期单元格设置样式
            cell.setCellValue(list.get(i).getBir());
        }
        try {
            workbook.write(new FileOutputStream("D:\\poi2.xls"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //导入数据
    @Test
    public void test3() throws IOException {
        //获取要导入的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("D:\\poi2.xls"));
        HSSFSheet sheet = workbook.getSheet("学生信息表");//获取工作空间

        List<Student> list = new ArrayList<>();
        //从第三行开始，到 工作空间最后一行
        for(int i = 2;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);

            String id = row.getCell(0).getStringCellValue();

            String name = row.getCell(1).getStringCellValue();

            double age = row.getCell(2).getNumericCellValue();

            Date bir = row.getCell(3).getDateCellValue();

            Student student = new Student(id, name, (int) age, bir);
            list.add(student);
        }
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
