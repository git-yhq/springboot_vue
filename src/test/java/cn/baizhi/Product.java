package cn.baizhi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Excel(name="产品id")
    private String id;
    @Excel(name="产品名")
    private String name;
    @Excel(name = "产品图片",type = 2)
    private String path;
}
