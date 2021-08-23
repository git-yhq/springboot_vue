package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin//解决跨域
@RequestMapping("video")
public class VideoController {
    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoServiceImpl videoService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(int page){
        //定义每页显示多少条数据
        int size=3;
        Map<String, Object> map = videoService.queryByPage(page, size);
        return map;
    }
    @RequestMapping("add")
    public void add(MultipartFile video,String title,String brief,String id){

        log.debug(video.getOriginalFilename());
        log.debug(title);
        log.debug(brief);
        log.debug(id);
        Video video1 = new Video();
        video1.setTitle(title);
        video1.setBrief(brief);

        Category category = new Category();
        category.setId(id);

        video1.setCategory(category);
        videoService.regist(video, video1);
    }
    @RequestMapping("deleteById")
    public void deleteById(String id,String videoPath){
        log.debug(id);
        log.debug(videoPath);
        videoService.dropById(id, videoPath);
    }
}
