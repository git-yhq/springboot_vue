package cn.baizhi;

import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class GoEasyTest {
    //GoEasy向频道中发送消息
    @Test
    public void test(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-11d0aa917fd04b6b94cf10be1a643fa7");
                goEasy.publish("my_channel", "Hello, GoEasy!");
    }

    @Test
    public void test1(){
        Random r = new Random();
        for(int i = 0;i<50;i++) { //向前端发送10次数据
            List<String> list = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
            List<Integer> list2 = Arrays.asList(r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100));
            List<Integer> list3 = Arrays.asList(r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextInt(100));
            //List<Integer> list3 = Arrays.asList(20, 30, 40, 50, 60, 70);
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            map.put("manCount", list2);
            map.put("womanCount", list3);

            //通过goeasy实时发送到前端页面 只能发送字符串，需要将map集合转成json串
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-11d0aa917fd04b6b94cf10be1a643fa7");
            goEasy.publish("my_channel", JSONObject.toJSONString(map));

            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
