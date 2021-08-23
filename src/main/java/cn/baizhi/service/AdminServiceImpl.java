package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> login(Admin admin) {
        HashMap<String,Object> map = new HashMap<>();
        //调用dao根据用户名查询
        Admin admin1 = adminDao.selectByName(admin.getUsername());
        map.put("flag", false);
        if (admin1 == null) {
            //用户名不存在
            map.put("msg", "用户名不存在");
        }else{
            if(admin1.getPassword().equals(admin.getPassword())){
                //用户名和密码都正确
                map.put("flag", true);
                map.put("admin", admin);
            }else{
                //密码输入错误
                map.put("msg", "密码输入错误");
            }
        }
        return map;
    }
}
