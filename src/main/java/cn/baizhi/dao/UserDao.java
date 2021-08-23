package cn.baizhi.dao;

import cn.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserDao extends BaseDao<User,String> {
    //范围查询             从哪条开始   每页查询几条
    List<User> queryRange(@Param("start") int start, @Param("count") int count);

    //查数据库总条数
    int selectCount();

    //修改用户的状态
    void changeStatus(@Param("id") String id,@Param("status") int status);
}
