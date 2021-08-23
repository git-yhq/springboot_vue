package cn.baizhi;

import cn.baizhi.dao.*;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.vo.MonthAndCount;
import cn.baizhi.vo.VideoVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class YxYhqApplicationTests {
   /* @Autowired
    private AdminDao adminDao;*/

   @Autowired
   private MonthAndCountDao monthAndCountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private VideoVoDao vvd;
   /* @Test
    public void test(){
        Admin admin = adminDao.selectByName("张三");
        System.out.println(admin);
    }*/
    @Test
    public void test2(){
        /*List<User> users = userDao.queryRange(6, 3);
        for (User user : users) {
            System.out.println(user);
        }*/
        System.out.println(userDao.selectCount());
    }
    @Test
    public void test3(){
        /*List<Category> list = categoryDao.selectByLevels(1);
        for (Category category : list) {
            System.out.println(category);
        }*/
        /*List<Category> list = categoryDao.selectByParentId("1");
        for (Category q : list) {
            System.out.println(q);
        }*/
       //categoryDao.add(new Category(UUID.randomUUID().toString(),"IOS",2,"1"));

        /*Category category = categoryDao.selectByCateName("IOS");
        System.out.println(category);*/
       /* List<Category> list = categoryDao.selectByParentId("2267ad1c-6d0f-4a53-9d0e-0ab42d09efba");
        System.out.println(list);
        for (Category category : list) {
            System.out.println(category);
        }*/
        List list = userDao.selectAll();
        System.out.println(list.size());
    }
    @Test
    public void test4(){
        /*List<Video> videos = videoDao.queryRange(3, 3);
        for (Video video : videos) {
            System.out.println(video);
        }*/
        /*System.out.println(videoDao.selectCount());*/
        videoDao.insert(new Video("5", "美女5","美女吸烟" ,"11","http://yx-yhq.oss-cn-beijing.aliyuncs.com/video/smokinggir.mp4" ,new Date(),new Category("37", null, null, null),new User("1bd53eac-688d-40cb-bdea-15869e4e874e", null, null, null, null, null, null, null), null));
    }
    @Test
    public void test5(){
        List<VideoVo> videoVos = vvd.queryAll();
        for (VideoVo videoVo : videoVos) {
            System.out.println(videoVo);
        }
    }
    @Test
    public void test6(){
        List<User> users = userDao.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void test7(){
        System.out.println("111");
        List<MonthAndCount> list = monthAndCountDao.selectCountBySex("男");
        for (MonthAndCount monthAndCount : list) {
            System.out.println(monthAndCount);
        }
    }
}
