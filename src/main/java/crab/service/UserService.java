package crab.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import crab.dao.OrderDao;
import crab.dao.UserDao;
import crab.pojo.Role;
import crab.pojo.User;
import crab.util.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by darlingtld on 2015/6/24 0024.
 */
@Service
public class UserService {

    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static ConcurrentHashMap<String, User> codeUserMap = new ConcurrentHashMap<>();

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(4 * 60 * 60 * 1000);
                        codeUserMap.clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }.start();
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        return converters;
    }

    public User getUserInformation(String code) {
        if (codeUserMap.containsKey(code)) {
            logger.info("Get user from codeUserMap by key {}", code);
            return codeUserMap.get(code);
        }
        logger.info("Code {} is not found in the codeUserMap", code);

        String getAccessTokenUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", PropertyHolder.APPID, PropertyHolder.APPSECRET, code);
        logger.info("[Access Token URL] {}", getAccessTokenUrl);
        String retData = restTemplate.getForObject(getAccessTokenUrl, String.class, new HashMap<String, Object>());
        logger.info("[Acess Token returned data] {}", retData);

        /** jsonObject should be something like below
         * {
         "access_token":"ACCESS_TOKEN",
         "expires_in":7200,
         "refresh_token":"REFRESH_TOKEN",
         "openid":"OPENID",
         "scope":"SCOPE",
         "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
         }
         */
        JSONObject jsonObject = JSON.parseObject(retData);
        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
//        https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String getUserInfoUrl = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", accessToken, openid, code);
        String userData = restTemplate.getForObject(getUserInfoUrl, String.class, new HashMap<String, Object>());
        if (userData.contains("errcode")) {
            logger.info("UserData contains errcode");
            return null;
        }
        logger.info("[User info returned data] {}", userData);
        User user = JSONObject.parseObject(userData, User.class);
        logger.info("[Parsed User info] {}", user);
        codeUserMap.put(code, user);
//        logger.info("[CodeUserMap]{}", codeUserMap);
//save user information
        saveOrUpdate(user);
        return user;
    }

    @Transactional
    public User getUserByWechatId(String fromUserName) {
        User user = userDao.getUserByWechatId(fromUserName);

        return user;
    }

    @Transactional
    public User saveOrUpdate(User user) {
        logger.info(user.toString());
        // filter emoji
        try {
            user.setNickname(new String(user.getNickname().getBytes("utf-8"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            user.setNickname("songda user");
        }

        User userInDB = userDao.getUserByWechatId(user.getOpenid());
        if (userInDB == null) {
            logger.info("save user");
            user.setRole(Role.USER.toString());
            userDao.save(user);
        } else {
            logger.info("update user");
            userInDB.setNickname(user.getNickname());
//            userInDB.setUsername(user.getUsername());
            userInDB.setHeadimgurl(user.getHeadimgurl());
//            userInDB.setMobile(user.getMobile());
//            userInDB.setEmail(user.getEmail());
            userInDB.setHeadimgurl(user.getHeadimgurl());
//            userInDB.setBuyerInfo(user.getBuyerInfo());
//            userInDB.setConsignee(user.getConsignee());
//            userInDB.setConsigneeContact(user.getConsigneeContact());
            userDao.update(userInDB);
        }
        return userDao.getUserByWechatId(user.getOpenid());
    }

    @Transactional
    public List<User> getAll() {
        return userDao.getAll();
    }
}
