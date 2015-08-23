package createsh.service;

/**
 * Created by darlingtld on 2015/2/19.
 */

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 */
@Service
public class TulingApiService {
    /**
     *
     * @param content
     * @return
     */
    public String getTulingResult(String content) {
        String result = null;
        int retryCount = 5;
        while (--retryCount > 0) {
            try {
                String APIKEY = "c57e07c4ae18efef05ecf750df4da58a";
                String INFO = URLEncoder.encode(content, "utf-8");
                String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
                URL getUrl = new URL(getURL);
                HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(10 * 1000);
                connection.connect();
                // ȡ������������ʹ��Reader��ȡ
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                // �Ͽ�����
                connection.disconnect();
                result = sb.toString();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                result = "我不明白哦~";
            }
        }
        try {
            JSONObject json = JSONObject.parseObject(result);
            //��code=100000Ϊ�����ο�ͼ�������api�ĵ�
            if (100000 == json.getInteger("code")) {
                result = json.getString("text");
            } else if (200000 == json.getInteger("code")) {
                result = json.getString("text") + json.getString("url");
            } else {
                result = json.getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
