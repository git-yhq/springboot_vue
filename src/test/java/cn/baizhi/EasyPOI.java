package cn.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.baizhi.entity.Student;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EasyPOI {
    //导出
    @Test
    public void test() throws IOException {
        List<Student> list = new ArrayList<>();
        list.add(new Student("1", "小明", 20, new Date()));
        list.add(new Student("2", "小明2", 20, new Date()));
        list.add(new Student("3", "小明3", 20, new Date()));


        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("学生信息","学生信息表"),Student.class, list);

        //导出位置
        workbook.write(new FileOutputStream("D:\\easyPOI.xls"));
    }

    @Test
    public void test2() throws IOException {
        List<Student> list = new ArrayList<>();
        list.add(new Student("1", "小明", 20, new Date()));
        list.add(new Student("2", "小明2", 20, new Date()));
        list.add(new Student("3", "小明3", 20, new Date()));

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("1", "张三", 1000.0, list));
        teachers.add(new Teacher("2", "李四", 2000.0, list));
        teachers.add(new Teacher("3", "王五", 3000.0, list));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("老师信息","老师信息表"),Teacher.class, teachers);

        //导出位置
        workbook.write(new FileOutputStream("D:\\poi\\easyPOI2.xls"));
    }

    //导入
    @Test
    public void test3(){
        //导入参数对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格的标题 有几行就写几
        params.setHeadRows(2);//表头 有几行就写几

        List<Teacher> list = ExcelImportUtil.importExcel(new File("D:\\poi\\easyPOI2.xls"), Teacher.class, params);
        for (Teacher teacher : list) {
            System.out.println(teacher);
        }
    }

    //图片导出
    /*
    * 导出：文件路径可以是
    *       磁盘的绝对路径
    *       项目路径 src....
    *
    *       网络路径不管用
    * */
    /*@Test
    public void test4() throws IOException {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "小明", "src/main/webapp/upload/KPI.jpg"));
        list.add(new User("2", "小红", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg"));
        list.add(new User("3", "小黑", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg"));
        list.add(new User("4", "小刚", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg"));
        //list.add(new User("5", "小泽泽", "http://yx-yhq.oss-cn-beijing.aliyuncs.com/%E5%9B%BE%E7%89%87/%E5%94%90%E6%B3%BD.jpg"));


        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),User.class, list);

        //导出位置
        workbook.write(new FileOutputStream("D:\\poi\\easyPOI-user.xls"));

    }*/

    //图片导入
    @Test
    public void test5(){
        //导入参数对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格的标题 有几行就写几
        params.setHeadRows(1);//表头 有几行就写几

        List<User> list = ExcelImportUtil.importExcel(new File("D:\\poi\\easyPOI-user.xls"), User.class, params);

        for (User user : list) {
            System.out.println(user);
        }
    }

    //一对多导出，子项下有图片
    @Test
    public void test6() throws IOException {

        List<Product> products = new ArrayList<>();
        products.add(new Product("1", "电脑", "C:\\\\Users\\\\yinhuiquan\\\\Desktop\\\\新建文件夹\\\\美女.jpg"));
        products.add(new Product("2", "电脑2", "C:\\\\Users\\\\yinhuiquan\\\\Desktop\\\\新建文件夹\\\\美女.jpg"));
        products.add(new Product("3", "电脑3", "C:\\\\Users\\\\yinhuiquan\\\\Desktop\\\\新建文件夹\\\\美女.jpg"));


        List<User> list = new ArrayList<>();
        list.add(new User("1", "小明", "src/main/webapp/upload/KPI.jpg",products));
        list.add(new User("2", "小红", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg",products));
        list.add(new User("3", "小黑", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg",products));
        list.add(new User("4", "小刚", "C:\\Users\\yinhuiquan\\Desktop\\新建文件夹\\美女.jpg",products));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),User.class, list);

        //导出位置
        workbook.write(new FileOutputStream("D:\\poi\\easyPOI-user-product.xls"));
    }
}
