package cn.baizhi.dao;

import cn.baizhi.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends BaseDao {
    //根据用户名查询
    Admin selectByName(String username);
}
