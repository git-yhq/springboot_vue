package cn.baizhi.dao;

import cn.baizhi.vo.VideoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VideoVoDao {
    List<VideoVo> queryAll();
}
