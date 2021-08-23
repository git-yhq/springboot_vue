package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect//代表这个类是一个切面类
@Component//表示能在工厂创建对象
public class CacheHahAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){

        System.out.println("进入环绕通知");

        StringBuilder sb = new StringBuilder();

        //获取类的全路径
        Object target = joinPoint.getTarget();//获得配置切点的类的对象
        String className = target.getClass().getName();//类名
        System.out.println(className);

        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName);
        sb.append(className).append(methodName);
        //实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
            sb.append(arg);
        }

        //取消键的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hash = redisTemplate.opsForHash();

        Object obj = null;

        if(hash.hasKey(className, sb.toString())){
            //有这个key obj为redis中存的数据
            obj = hash.get(className, sb.toString());

        }else{
            //没有这个key obj为数据库中查出来的值
            try {
                obj = joinPoint.proceed();//放行 会将配置切点的方法的返回值带回来
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hash.put(className, sb.toString(), obj);
        }
        return obj;
    }
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("进入环绕通知");

        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        //直接删除大键
        redisTemplate.delete(name);

        //类的全限定名
       /* String name = joinPoint.getTarget().getClass().getName();
        System.out.println(name);
        Set keys = redisTemplate.keys("*");*/
        //把所有键遍历出来
        /*for (Object key : keys) {
            String newKey = (String)key;
            //将有关全限定名的键删掉
            if(newKey.startsWith(name)){
                redisTemplate.delete(key);
            }
        }*/
    }
}
