package createsh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import createsh.util.PropertyHolder;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by darlingtld on 2015/7/1 0001.
 */
public class OauthServiceTest {
    @Test
    public void testCode() {
        RestTemplate restTemplate = new RestTemplate();
        String code = "021565eebce0b7acec009e7f55ed842k";
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", PropertyHolder.APPID, PropertyHolder.APPSECRET, code);
        System.out.println(url);
        String jsonObject = restTemplate.getForObject(url, String.class, new HashMap<String, Object>());
        System.out.println(jsonObject);
    }

    @Test
    public void jsonParseAccessToken() {
        String data = "{\"access_token\":\"OezXcEiiBSKSxW0eoylIeLSvKsd5SChtMzLp0RGzJPuQjGnW-9E4tKWWPgA1hiw59wPc8qNc5loovguBbjnSgh9ORuF82thcblzz3OjLdcz59izwypZ7BGZqPBIDKgygkkYsyJRm2DqP3HTTX6ctmw\",\"expires_in\":7200,\"refresh_token\":\"OezXcEiiBSKSxW0eoylIeLSvKsd5SChtMzLp0RGzJPuQjGnW-9E4tKWWPgA1hiw5tWIAuVAiczDWQ1AwuJKPTGqojSVKcqz2R_R0stxG19WpozA6jonjc4swekfI6zCwxOPG00iA7bsVVEOjvvTstg\",\"openid\":\"o5Irvt5957jQ4xmdHmDp59epk0UU\",\"scope\":\"snsapi_userinfo\"}";
        JSONObject jsonObject = JSON.parseObject(data);
        System.out.println(jsonObject.getString("access_token"));
    }

    @Test
    public void messyCodeTest() throws UnsupportedEncodingException {
        String data = "哈哈哈哈哈喽\uF61A\\xF0\\x9F\\x98\\x9A";
        System.out.println(new String(data.getBytes(), "utf-8"));
    }

    @Test
    public void getUserInformation() {
        RestTemplate restTemplate = new RestTemplate();
        String code = "o5Irvt3tty06NYT8W-nJa0gPERvM";
        String url = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN", PropertyHolder.TOKEN, code);
        System.out.println(url);
        String jsonObject = restTemplate.getForObject(url, String.class, new HashMap<String, Object>());
        System.out.println(jsonObject);
    }


}
