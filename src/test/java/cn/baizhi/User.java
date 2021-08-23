package cn.baizhi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Excel(name="用户id",needMerge = true)
    private String id;
    @Excel(name="用户名",needMerge = true)
    private String name;
    @Excel(name="用户头像",type = 2,needMerge = true)
    private String headpath;
    @ExcelCollection(name="产品信息")
    private List<Product> products;//有图片路径的属性一对多的时候导出会失败
}
