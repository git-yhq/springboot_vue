package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

//@Aspect//代表这个类是一个切面类
//@Component//表示能在工厂创建对象
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    /*
    *    下面是切面类中的环绕通知（额外功能）
    *     配置切点
    *
    *    三种配置方式
    *       execution() 方法级别
    *       within()  类级别
    *       @annotation  注解方式配置
    * */
    // 返回值 包名.类名.方法名(可变长参数)
    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){

        System.out.println("进入环绕通知");

        //将service查到的数据存到redis中，下次查询就不需要走数据库了
        //redis存储数据以键值对形式 key:类名全路径+方法名+实参 目的是保证键不会重复

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
        ValueOperations string = redisTemplate.opsForValue();

        Object obj = null;

        if(redisTemplate.hasKey(sb.toString())){
            //有这个key obj为redis中存的数据
            obj = string.get(sb.toString());

        }else{
            //没有这个key obj为数据库中查出来的值
            try {
                obj = joinPoint.proceed();//放行 会将配置切点的方法的返回值带回来
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //将结果存到redis中
            string.set(sb.toString(), obj);
        }
        /*
        *  判断redis中是否有数据了，有了就不放行了
        * */

        //System.out.println(obj);

        //System.out.println(sb);
        return obj;
    }

    //用户进行增删改操作后要清除redis中的缓存
    //对增删改操作配置切点
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("进入环绕通知");
        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println(name);

        Set keys = redisTemplate.keys("*");
        //把所有键遍历出来
        for (Object key : keys) {
            String newKey = (String)key;
            //将有关全限定名的键删掉
            if(newKey.startsWith(name)){
                redisTemplate.delete(key);
            }
        }
    }
}
