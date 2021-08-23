package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Excel(name="用户id")
    private String id;
    @Excel(name="用户名")
    private String username;
    @Excel(name="用户电话")
    private String phone;
    @Excel(name="用户头像",type = 2)
    private String headimg;
    @Excel(name="用户简介")
    private String brief;
    @Excel(name="用户微信号")
    private String wechat;
    @Excel(importFormat = "yyyy-MM-dd", name = "用户创建日期")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date createdate;
    @Excel(name="用户状态")
    private Integer status;
}
