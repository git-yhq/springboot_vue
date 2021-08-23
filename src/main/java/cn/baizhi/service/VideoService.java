package cn.baizhi.service;

import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    //分页查业务
    //分页查询                      查询第几页   每页展示多少条
    Map<String,Object> queryByPage(int page, int size);

    //添加视频业务
    void regist(MultipartFile video, Video video1);

    //根据id删除业务
    void dropById(String id,String path);

    //根据视频的上传时间倒序排列
    List<VideoVo> queryByCreateDate();
}
