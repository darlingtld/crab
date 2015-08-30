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
            prop.load(PropertyHolder.class.getClassLoader().getResourceAsStream("wechat.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String HEADER_MSG = "message";

    public static final String TOKEN = prop.getProperty("wechat.token");
    public static final String APPID = prop.getProperty("wechat.app_id");
    public static final String APPSECRET = prop.getProperty("wechat.app_secret");
    public static final String SERVER = prop.getProperty("wechat.server");

    public static final String MENU_GO_SHOP = "微商城";
    public static final String MENU_BUY_CRAB = "我要购蟹";
    public static final String MENU_BUY_CARD = "我要购卡";
    public static final String MENU_MY_INFO = "我的信息";
    public static final String MENU_MY_CARDCODE = "我的提货券";
    public static final String MENU_MY_ORDER = "我的订单";
    public static final String MENU_MY_COUPON = "我的优惠券";
    public static final String MENU_SERVICE_HALL = "服务大厅";
    public static final String MENU_CARDCODE_DELIVERY = "卡券提货";
    public static final String MENU_POST_SALE_SERVICE = "售后服务";

}
