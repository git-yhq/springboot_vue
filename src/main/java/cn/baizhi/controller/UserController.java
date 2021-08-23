package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin//解决跨域
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("queryByPage")
    public Map<String,Object> queryByPage(int page){
        //定义每页显示多少条数据
        int size=3;
        Map<String, Object> map = userService.queryByPage(page, size);
        return map;
    }
    @RequestMapping("updateStatuas")
    public void updateStatuas(String id, String status){
        log.debug(id);
        log.debug(status);
        int status1 = Integer.parseInt(status);
        userService.updateStatus(id, status1);
    }
  /*  @RequestMapping("add")
    public void add(MultipartFile photo, String username, String phone, String brief, HttpServletRequest request) throws IOException {

       *//* log.debug(photo.getOriginalFilename());
        log.debug(username);
        log.debug(phone);
        log.debug(brief);*//*
       //添加用户之前先将图片上传到本地服务器中

        //获取源文件名
        String filename = photo.getOriginalFilename();
        System.out.println(filename);

        //动态获取文件夹目录
        *//*String realPath = request.getServletContext().getRealPath("/upload");*//*
        String realPath = request.getServletContext().getRealPath("/upload");
        System.out.println("文件夹目录："+realPath);


      //文件名加上uuid 将生成的uuid中的 - 换成 ""
        String s = UUID.randomUUID().toString().replace("-", "");
        String realName = s+filename;

        //创建文件夹
        String dirName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File file = new File(realPath, dirName);
        file.mkdir();//如果文件夹存在，则不会创建

        //上传
        photo.transferTo(new File(file,realName));

       //将对象的一些属性赋值，然后添加
       User user = new User();
       user.setHeadimg(photo.getOriginalFilename());
       user.setUsername(username);
       user.setPhone(phone);
       user.setBrief(brief);
       userService.regist(user);
    }*/
    @RequestMapping("add")
    public void add(MultipartFile photo, String username, String phone, String brief) throws IOException {


        User user = new User();

        user.setUsername(username);
        user.setPhone(phone);
        user.setBrief(brief);
        userService.regist(photo,user);
    }
    @RequestMapping("deleteById")
    public void deleteById(String id){
        /*log.debug(id);
        System.out.println(id);*/
        userService.dropById(id);
    }
    @RequestMapping("selectById")
    public User selectById(String id){
        log.debug(id);
        User user = userService.queryById(id);
        System.out.println(user);
        return null;
    }

    @RequestMapping("download")
    public void download(){
        userService.downloadUser();
    }

    @RequestMapping("registCount")
    public Map<String,Object> registCount(){
        /*List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("hello");
        list.add("hello");*/
        List<String> list = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Integer> list2 = Arrays.asList(10, 20, 30, 40, 50, 60);
        List<Integer> list3 = Arrays.asList(20, 30, 40, 50, 60, 70);
        Map<String,Object> map = new HashMap<>();
        map.put("data", list);
        map.put("manCount", list2);
        map.put("womanCount", list3);
        return  map;
    }
}
