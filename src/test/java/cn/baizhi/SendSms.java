package cn.baizhi;




import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>请参见最新版本号</version>
</dependency>
*/
public class SendSms {
    public static void main(String[] args) {
        DefaultProfile profile =
                DefaultProfile
            .getProfile("cn-hangzhou", "LTAI5tNhdGq41aPbmXQaqjF9", "1nWwEbZsCykRoRhXThxGHDYm7p6osU");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "18055046182");
        //                                                    签名
        request.putQueryParameter("SignName", "登录验证");

        /*
       【登录验证】验证码1111,您正在进行333身份验证,打死不要告诉别人哦
        * */

        //                                                     需要模板CODE
        request.putQueryParameter("TemplateCode", "SMS_4020642");
        String s = "靠自己寸步难行,靠富婆直通天庭";
        String mes = "哈哈哈";
        request.putQueryParameter("TemplateParam", "{\"code\":\""+s+"\",\"product\":\""+mes+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());

//            System.out.println(response.getHttpStatus());

//            if(response.getHttpStatus() == 200 ){
//
//            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}