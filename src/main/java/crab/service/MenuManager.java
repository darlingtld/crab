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
        btn11.setName(PropertyHolder.MENU_BUY_CRAB);
        btn11.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "?buy_type=shuichandonghuo", "UTF-8")));

        ViewButton btn12 = new ViewButton();
        btn12.setName(PropertyHolder.MENU_BUY_CARD);
        btn12.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "?buy_type=card", "UTF-8")));

        ViewButton btn21 = new ViewButton();
        btn21.setName(PropertyHolder.MENU_MY_ORDER);
        btn21.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/myorder.html", "UTF-8")));

        ViewButton btn22 = new ViewButton();
        btn22.setName(PropertyHolder.MENU_MY_COUPON);
        btn22.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/mycoupon.html", "UTF-8")));

        ViewButton btn23 = new ViewButton();
        btn23.setName(PropertyHolder.MENU_MY_CARDCODE);
        btn23.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/mycardcode.html", "UTF-8")));


        ViewButton btn31 = new ViewButton();
        btn31.setName(PropertyHolder.MENU_CARDCODE_DELIVERY);
        btn31.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/modules/card_expense/index.html", "UTF-8")));

        ViewButton btn32 = new ViewButton();
        btn32.setName(PropertyHolder.MENU_CARDCODE_DELIVERY);
        btn32.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/postservice.html", "UTF-8")));

        ViewButton btn33 = new ViewButton();
        btn33.setName(PropertyHolder.MENU_CARDCODE_DELIVERY);
        btn33.setUrl(oauthUrl.replace("$appid", PropertyHolder.APPID).replace("$redirect_uri", URLEncoder.encode(PropertyHolder.SERVER + "/onsaleinfo.html", "UTF-8")));

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName(PropertyHolder.MENU_GO_SHOP);
        mainBtn1.setSub_button(new ViewButton[]{btn11, btn12});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName(PropertyHolder.MENU_MY_INFO);
        mainBtn2.setSub_button(new ViewButton[]{btn21, btn22, btn23});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName(PropertyHolder.MENU_SERVICE_HALL);
        mainBtn3.setSub_button(new ViewButton[]{btn31, btn32, btn33});

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(mainBtn1);
        jsonArray.add(mainBtn2);
        jsonArray.add(mainBtn3);

        JSONObject menu = new JSONObject();
        menu.put("button", jsonArray);
        System.out.println(menu);
        return menu;
    }
}
