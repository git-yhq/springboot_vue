package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VideoDao extends BaseDao {
    //分页查展示所有
    List<Video> queryRange(@Param("start") int start, @Param("count") int count);

    //查询数据库总条数
    int selectCount();
}
