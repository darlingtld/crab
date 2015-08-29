package crab.service;

/**
 * Created by darlingtld on 2015/2/20.
 */


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import crab.pojo.AccessToken;
import crab.pojo.button.ClickButton;
import crab.pojo.button.ComplexButton;
import crab.pojo.button.ViewButton;
import crab.util.PropertyHolder;
import crab.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MenuManager {
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        String appId = PropertyHolder.APPID;
        String appSecret = PropertyHolder.APPSECRET;

        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        String jsonMenu = getMenu().toJSONString();
        log.debug(jsonMenu);
        if (null != at) {
            int result = WeixinUtil.createMenu(jsonMenu, at.getToken());

            if (0 == result) {
                log.info("菜单创建成功");
            } else {
                log.info("菜单创建失败，错误码：{}", result);
            }
        }
    }

    private static JSONObject getMenu() throws UnsupportedEncodingException {

        /**
         * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
         */
        String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=$appid&redirect_uri=$redirect_uri&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

        ViewButton btn11 = new ViewButton();
        btn11.setName(PropertyHolder.MENU_GO_ORDER);
        btn11.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER, "UTF-8")));

        ViewButton btn21 = new ViewButton();
        btn21.setName(PropertyHolder.MENU_MY_ORDER);
        btn21.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/myorder.html", "UTF-8")));
//        btn21.setUrl(PropertyHolder.SERVER + "?order_history=true");

        ViewButton btn22 = new ViewButton();
        btn22.setName(PropertyHolder.MENU_MY_COUPON);
        btn22.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/mycoupon.html", "UTF-8")));

        ClickButton btn31 = new ClickButton();
        btn31.setName(PropertyHolder.MENU_POST_SALE_SERVICE);
        btn31.setKey(PropertyHolder.MENU_POST_SALE_SERVICE);

//        ClickButton btn32 = new ClickButton();
//        btn32.setName(PropertyHolder.MENU_NEW_PRODUCT);
//        btn32.setKey(PropertyHolder.MENU_NEW_PRODUCT);

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName(PropertyHolder.MENU_GO_ORDER);
        mainBtn1.setSub_button(new ViewButton[]{btn11});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName(PropertyHolder.MENU_MY_LIANGYUAN);
        mainBtn2.setSub_button(new ViewButton[]{btn21, btn22});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName(PropertyHolder.MENU_POST_SALE_SERVICE);
        mainBtn3.setSub_button(new ClickButton[]{btn31});

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(btn11);
        jsonArray.add(mainBtn2);
        jsonArray.add(mainBtn3);

        JSONObject menu = new JSONObject();
        menu.put("button", jsonArray);
        System.out.println(menu);
        return menu;
    }
}
