package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UserService {
    //分页查询                      查询第几页   每页展示多少条
    Map<String,Object> queryByPage(int page,int size);

    //修改用户状态业务
    void updateStatus(String id,int status);

    //添加用户业务
    void regist(MultipartFile photo, User user) throws IOException;

    //删除用户业务
    void dropById(String id);

    //根据id查用户
    User queryById(String id);

    //查所有业务
    void downloadUser();
}
