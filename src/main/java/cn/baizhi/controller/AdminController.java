package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin")
@CrossOrigin//解决跨域问题
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminServiceImpl adminService;
    @RequestMapping("login")
    public Map<String,Object> login(@RequestBody Admin admin){
        log.debug(admin.getUsername());
        log.debug(admin.getPassword());
        Map<String, Object> map = adminService.login(admin);
        return map;
    }

}
