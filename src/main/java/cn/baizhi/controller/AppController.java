package cn.baizhi.controller;

import cn.baizhi.comment.CommentResult2;
import cn.baizhi.service.VideoServiceImpl;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("app")
@RestController
@CrossOrigin
public class AppController {
    @Autowired
    private VideoServiceImpl videoService;
    @RequestMapping("getPhoneCode")
    public Map<String,Object> getPhoneCode(String phone){
        Map<String,Object> map = new HashMap<>();
            Map<String, Object> smsMap = AliYun.sedSms(phone);
            //验证码
        Object yzm = smsMap.get("yzm");
        System.out.println("验证码："+yzm);
        //System.out.println(s);
        if((boolean)smsMap.get("code")){
            //发送验证码成功
           /* map.put("data", phone);
            map.put("message","发送验证码成功");
            map.put("status", "100");*/
            /*CommentResult cr = CommentResult.success("发送验证码成功", phone);
            map.put("cr",cr);*/
            return CommentResult2.success("发送验证码成功", phone);
        }else{
            //发送验证码失败
           /* map.put("data", null);
            map.put("message","发送验证码失败");
            map.put("status", "104");*/

            /*CommentResult cr = CommentResult.fail("发送验证码失败", null);
            map.put("cr",cr);*/
            return CommentResult2.fail("发送验证码失败", null);
        }
    }
    @RequestMapping("queryByReleaseTime")
    public Map<String,Object> queryByReleaseTime(){
        List<VideoVo> videoVos = new ArrayList<>();
        try {
            videoVos = videoService.queryByCreateDate();//空
            //业务执行成功
            return CommentResult2.success("查询成功", videoVos);
        }catch(Exception e){
            e.printStackTrace();
            //查询业务失败
            return CommentResult2.fail("查询失败", videoVos);//数据为空
        }
    }
}
