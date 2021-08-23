package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.AliYunConfig;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.AliYun;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void downloadUser() {
        List<User> list = userDao.selectAll();
        //将所有用户的头像下载到本地
        for (User user : list) {
            String headimg = user.getHeadimg();
            //headimg http://yx-yhq.oss-cn-beijing.aliyuncs.com/KPI.jpg
            String fileName = headimg.substring(headimg.lastIndexOf("/") + 1);
            //System.out.println(fileName);
            AliYun.download(fileName);
            //System.out.println(fileName);

            user.setHeadimg("D:\\download\\"+fileName);
        }
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),User.class, list);

        //导出位置
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("D:\\poi\\user.xls");
            workbook.write(stream);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @DeleteCache
    @Override
    public void dropById(String id) {
        //得到要删除的用户的数据库的头像的值
        // 将http://yx-yhq.oss-cn-beijing.aliyuncs.com/唐泽.jpg 通过 "/" 将文件名分割出来
        User user = (User) userDao.selectById(id);
        String headimg = user.getHeadimg();
        System.out.println("headimg"+headimg);


        //删除用户之前先把云端数据删除
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tNhdGq41aPbmXQaqjF9";
        String accessKeySecret = "1nWwEbZsCykRoRhXThxGHDYm7p6osU";
        String bucketName = "yx-yhq";  //存储空间名
        String objectName = headimg.substring(headimg.lastIndexOf("/")+1);  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();


        userDao.deleteById(id);
    }

    @Override
    public User queryById(String id) {
        return (User) userDao.selectById(id);
    }

    @DeleteCache
    @Override
    public void regist(MultipartFile photo, User user) throws IOException {
        //先将头像上传到阿里云中 以流的方式上传，将图片转成流 byte[] bytes = photo.getBytes();
        String endpoint = AliYunConfig.ENDPOINT;
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliYunConfig.ACCESS_KEY_SECRET;

        //上传同一个文件时如果不改名会将之前的覆盖，为了保证不会覆盖，可以给文件名加上uuid
        //文件名加上uuid 将生成的uuid中的 - 换成 ""
        String s = UUID.randomUUID().toString().replace("-", "");
        String fileName = s+photo.getOriginalFilename();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest("yx-yhq", fileName, new ByteArrayInputStream(photo.getBytes()));

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

        String headimag = "http://yx-yhq.oss-cn-beijing.aliyuncs.com/"+fileName;



        user.setHeadimg(headimag);
        user.setStatus(0);
        user.setId(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        userDao.insert(user);
    }

    @DeleteCache
    @Override
    public void updateStatus(String id, int status) {
        userDao.changeStatus(id, status);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(int page, int size) {
        /*
        * 用map集合作为返回值，是因为关于分页这块到时要返回很多值，
        * 比如当前页，最后页是几，分页查询出来的数据...
        * */
        Map<String,Object> map = new HashMap<>();

        //从哪一页开始  (page-1)*size 分页查
        List<User> list = userDao.queryRange((page - 1) * size, size);

        //查总页数
        //数据库总条数
        int i = userDao.selectCount();

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
