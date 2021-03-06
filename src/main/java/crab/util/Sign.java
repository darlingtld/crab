package crab.util;

/**
 * Created by darlingtld on 2015/9/5 0005.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class Sign {
    private static Calendar thisTime = Calendar.getInstance();
    private static Calendar startTime = Calendar.getInstance();
    private static JSONObject config;

    public static JSONObject getWXConfig() {
        thisTime = Calendar.getInstance();
        thisTime.add(Calendar.HOUR, -1);
        if (config != null && thisTime.before(startTime)) {
            return config;
        } else {
            startTime = Calendar.getInstance();
            thisTime = Calendar.getInstance();
            config = generateWXConfig();
            return config;
        }
    }

    private static JSONObject generateWXConfig() {
        RestTemplate restTemplate = new RestTemplate();
        String getAccessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", PropertyHolder.APPID, PropertyHolder.APPSECRET);
        System.out.println("[wxconfig getAccessTokenUrl]" + getAccessTokenUrl);
        String retData = restTemplate.getForObject(getAccessTokenUrl, String.class, new HashMap<String, Object>());
        System.out.println("[wxconfig Access Token returned data] " + retData);

        JSONObject jsonObject = JSON.parseObject(retData);
        String accessToken = jsonObject.getString("access_token");
        String jsapiTicketUrl = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi", accessToken);
        retData = restTemplate.getForObject(jsapiTicketUrl, String.class, new HashMap<String, Object>());
        System.out.println("[wxconfig jsapiTicketUrl returned data] " + retData);

        jsonObject = JSON.parseObject(retData);
        String jsapi_ticket = jsonObject.getString("ticket");

        String url = "http://cai.songdatech.com/crab/?buy_type=card";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        /**
         * {
         * debug: true,
         * appId: "wxa92ba20a1d597040",
         * timestamp: 1441425346,
         * nonceStr: "b577850e-278f-4567-9163-d7baf2616aa8",
         * signature: "920580e1258ec45c115287b44093158249ba3978",
         * jsApiList: [
         * "checkJsApi",
         * "onMenuShareTimeline",
         * "onMenuShareAppMessage",
         * "onMenuShareQQ",
         * "onMenuShareWeibo",
         * "hideMenuItems",
         * "showMenuItems",
         * "hideAllNonBaseMenuItem",
         * "showAllNonBaseMenuItem",
         * "translateVoice",
         * "startRecord",
         * "stopRecord",
         * "onRecordEnd",
         * "playVoice",
         * "pauseVoice",
         * "stopVoice",
         * "uploadVoice",
         * "downloadVoice",
         * "chooseImage",
         * "previewImage",
         * "uploadImage",
         * "downloadImage",
         * "getNetworkType",
         * "openLocation",
         * "getLocation",
         * "hideOptionMenu",
         * "showOptionMenu",
         * "closeWindow",
         * "scanQRCode",
         * "chooseWXPay",
         * "openProductSpecificView",
         * "addCard",
         * "chooseCard",
         * "openCard"
         * ]
         * }
         */
        String[] jsApiList = {"checkJsApi",
                "onMenuShareTimeline",
                "onMenuShareAppMessage",
                "onMenuShareQQ",
                "onMenuShareWeibo",
                "hideMenuItems",
                "showMenuItems",
                "hideAllNonBaseMenuItem",
                "showAllNonBaseMenuItem",
                "translateVoice",
                "startRecord",
                "stopRecord",
                "onRecordEnd",
                "playVoice",
                "pauseVoice",
                "stopVoice",
                "uploadVoice",
                "downloadVoice",
                "chooseImage",
                "previewImage",
                "uploadImage",
                "downloadImage",
                "getNetworkType",
                "openLocation",
                "getLocation",
                "hideOptionMenu",
                "showOptionMenu",
                "closeWindow",
                "scanQRCode",
                "chooseWXPay",
                "openProductSpecificView",
                "addCard",
                "chooseCard",
                "openCard"
        };
        config = new JSONObject();
        config.put("debug", false);
        config.put("appId", PropertyHolder.APPID);
        config.put("nonceStr", ret.get("nonceStr"));
        config.put("timestamp", ret.get("timestamp"));
        config.put("signature", ret.get("signature"));
        config.put("jsApiList", jsApiList);
        return config;
    }


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String getAccessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", PropertyHolder.APPID, PropertyHolder.APPSECRET);
        String retData = restTemplate.getForObject(getAccessTokenUrl, String.class, new HashMap<String, Object>());
        System.out.println("[Acess Token returned data] " + retData);

        JSONObject jsonObject = JSON.parseObject(retData);
        String accessToken = jsonObject.getString("access_token");
        String jsapiTicketUrl = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi", accessToken);
        retData = restTemplate.getForObject(jsapiTicketUrl, String.class, new HashMap<String, Object>());
        System.out.println("[jsapiTicketUrl returned data] " + retData);

        jsonObject = JSON.parseObject(retData);
        String jsapi_ticket = jsonObject.getString("ticket");

        String url = "http://cai.songdatech.com/crab/?buy_type=card";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}

