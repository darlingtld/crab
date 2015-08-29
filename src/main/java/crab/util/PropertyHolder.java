package crab.util;


import java.io.IOException;
import java.util.Properties;

/**
 * Created by tangld on 2015/3/30.
 */
public class PropertyHolder {
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyHolder.class.getClassLoader().getResourceAsStream("crab.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String HEADER_MSG = "message";

    public static final String TOKEN = prop.getProperty("wechat.token");
    public static final String APPID = prop.getProperty("wechat.app_id");
    public static final String APPSECRET = prop.getProperty("wechat.app_secret");
    public static final String SERVER = prop.getProperty("wechat.server");

    public static final String MENU_GO_ORDER = "我要下单";
    public static final String MENU_MY_ORDER = "我的订单";
    public static final String MENU_MY_LIANGYUAN = "我的优品";
    public static final String MENU_MY_COUPON = "我的卡券";
    public static final String MENU_NEW_PRODUCT = "新鲜资讯";
    public static final String MENU_ABOUT_US = "关于我们";
    public static final String MENU_POST_SALE_SERVICE = "售后服务";

}
