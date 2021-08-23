package cn.baizhi.service;

import cn.baizhi.entity.Admin;

import java.util.Map;

public interface AdminService {
    //登录业务
    Map<String,Object> login(Admin admin);
}
