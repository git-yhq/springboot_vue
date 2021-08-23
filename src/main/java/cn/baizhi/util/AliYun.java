package cn.baizhi.util;

import cn.baizhi.dao.AliYunConfig;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AliYun {
    public static Map<String,Object> sedSms(String phone) {
        /*  键个的阿里云账号
        *   String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
            String ACCESS_KEY_ID = "LTAI5t8ZP4TY8F2CdYnhBu2t";
            String ACCESS_KEY_SECRET = "03M7kfvHWlIrACTk1aqTzSy0eFDkED";
        * */
        Map<String,Object> map = new HashMap<>();
        DefaultProfile profile =
                DefaultProfile
                        .getProfile("cn-hangzhou", "LTAI5t8ZP4TY8F2CdYnhBu2t", "03M7kfvHWlIrACTk1aqTzSy0eFDkED");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        //                                                    签名
        request.putQueryParameter("SignName", "登录验证");

        /*
       【登录验证】验证码1111,您正在进行333身份验证,打死不要告诉别人哦
        * */

        //                                                     需要模板CODE
        request.putQueryParameter("TemplateCode", "SMS_4020642");
        //工具类 获取验证码
        String securityCode = SecurityCode.getSecurityCode();
        String s = securityCode;//验证码
        map.put("yzm",s);
        String mes = "【应学】";
        request.putQueryParameter("TemplateParam", "{\"code\":\""+s+"\",\"product\":\""+mes+"\"}");
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println(response.getData());

            //将字符串转成json对象 对象里有一个code属性，等于ok说明手机号是正确的，能获取验证码，其他值不行
        Map map1 = JSONObject.parseObject(response.getData(), Map.class);
        Object code = map1.get("Code");
        if(code.equals("OK")){
            map.put("code",true);
        }else{
            map.put("code",false);
        }

        return map;
    }

    //将云端的图片路径下载到本地项目中
    //fileName 文件名
    public static void download(String fileName){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId=AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliYunConfig.ACCESS_KEY_SECRET;
        String bucketName = "yx-yhq";  //存储空间名
        String objectName = fileName;  //文件名
        String localFile="D:\\download\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
