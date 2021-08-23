package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.AliYunConfig;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoVoDao vvd;
    @Autowired
    private VideoDao videoDao;

    @DeleteCache
    @Override
    public void dropById(String id,String path) {
        //删除视频之前，先把云端数据删除
        String endpoint = AliYunConfig.ENDPOINT;
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliYunConfig.ACCESS_KEY_SECRET;
        String bucketName = "yx-yhq";  //存储空间名
        //path http://yx-yhq.oss-cn-beijing.aliyuncs.com/video/1629126885875smokinggir.mp4
        String videoName = path.substring(path.lastIndexOf("/") + 1);//视频文件名
        // videoName 1629126885875smokinggir.mp4 获取第一帧截图名
        String[] split = videoName.split("\\.");
        String photoName = split[0]+".jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        System.out.println("====="+videoName);
        System.out.println("++++++"+photoName);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, "video/"+videoName);//删除视频
        ossClient.deleteObject(bucketName, "video/"+photoName);//删除第一帧截图

        // 关闭OSSClient。
        ossClient.shutdown();

        videoDao.deleteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<VideoVo> queryByCreateDate() {
        return vvd.queryAll();
    }

    @DeleteCache
    @Override         //第一个是文件        第二个是video对象
    public void regist(MultipartFile video,Video video1) {
        String endpoint = AliYunConfig.ENDPOINT;
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliYunConfig.ACCESS_KEY_SECRET;

        String fileName = new Date().getTime()+video.getOriginalFilename();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = null;
        try{
            putObjectRequest = new PutObjectRequest("yx-yhq", "video/"+fileName, new ByteArrayInputStream(video.getBytes()));
        }catch(Exception e){
            e.printStackTrace();
        }

        // 上传视频。
        ossClient.putObject(putObjectRequest);

        //视频截帧
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_50000,f_jpg,w_500,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("yx-yhq", "video/"+fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        //截取的第一帧图片保存的路径
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        //将截帧出来的图片永久保存
        // 填写网络流地址。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        //fileName 1629123767776smokinggir.mp4 得改成 jpg结尾的
        String[] split = fileName.split("\\.");
        String fileName2 = split[0]+".jpg";

        ossClient.putObject("yx-yhq", "video/"+fileName2, inputStream);



        // 关闭OSSClient。
        ossClient.shutdown();








        video1.setCoverPath(signedUrl.toString());

        //http://yx-yhq.oss-cn-beijing.aliyuncs.com/video/smokinggir.mp4
        String videoPath =   "http://yx-yhq.oss-cn-beijing.aliyuncs.com/video/"+fileName;

        video1.setId(UUID.randomUUID().toString());
        video1.setCreateDate(new Date());
        video1.setVideoPath(videoPath);

        User user = new User();
        user.setId("1bd53eac-688d-40cb-bdea-15869e4e874e");
        video1.setUser(user);
        video1.setGroupId(null);

        videoDao.insert(video1);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> queryByPage(int page, int size) {
        /*
         * 用map集合作为返回值，是因为关于分页这块到时要返回很多值，
         * 比如当前页，最后页是几，分页查询出来的数据...
         * */
        Map<String,Object> map = new HashMap<>();

        //从哪一页开始  (page-1)*size 分页查
        List<Video> list = videoDao.queryRange((page - 1) * size, size);

        //查总页数
        //数据库总条数
        int i = videoDao.selectCount();

        //定义总页数变量
        int pages;
        if(i%size==0){
            pages=i/size;
        }else{
            pages=(i/size)+1;
        }

        //将分页查询的数据存在map中
        map.put("data",list);

        //将总页数存在map中
        map.put("pages",pages);

        //将当前页存在map集合中
        map.put("page",page);

        return map;
    }
}
