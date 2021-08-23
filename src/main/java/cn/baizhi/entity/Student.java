package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    @Excel(name = "学生的id")
    private String id;
    @Excel(name="学生姓名")
    private String name;
    @Excel(name="学生年龄")
    private Integer age;
    @Excel(name="学生生日",format = "yyyy-MM-dd",width = 20)
    private Date bir;
}
