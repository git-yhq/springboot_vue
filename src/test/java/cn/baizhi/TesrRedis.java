package cn.baizhi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

@SpringBootTest
public class TesrRedis {
    //操作对象
    @Autowired
    private RedisTemplate redisTemplate;
    //操作字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        ValueOperations<String, String> string = stringRedisTemplate.opsForValue();
        /*string.set("name", "张三");
        System.out.println(string.get("name"));*/
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }
}
