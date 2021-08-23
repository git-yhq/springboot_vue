package cn.baizhi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.baizhi.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Excel(name="id",needMerge = true)
    private String id;
    @Excel(name="姓名",needMerge = true)
    private String name;
    @Excel(name="工资",needMerge = true)
    private Double salary;
    @ExcelCollection(name="对应的学员")
    private List<Student> stus;
}
