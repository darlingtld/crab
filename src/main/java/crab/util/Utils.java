package crab.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by darlingtld on 2015/7/25 0025.
 */
public class Utils {

    public static double formatDouble(double value, int scale) {
        BigDecimal b = new BigDecimal(value);
        double fValue = b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return fValue;
    }

    public static Date yyyyMMddHHmmssParse(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String chineseDateFormat(Date startTime) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(startTime);
    }

    public static String yyyyMMddHHmmssFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public synchronized static String generateConfirmCode() {
        Random random = new Random();
        String value = String.valueOf(random.nextInt(1000000000));
        StringBuffer code = new StringBuffer(value);
        for (int i = 0; i < 9 - value.length(); i++) {
            code.insert(0, "0");
        }
        return code.toString();
    }

    public synchronized static String generateCardCode() {
        int codeLength = 15;
        Random random = new Random();
        long rlong = random.nextLong();
        String value = String.valueOf(rlong < 0 ? -rlong : rlong);
        StringBuffer code = new StringBuffer(value);
        if (code.length() > codeLength) {
            return code.substring(0, codeLength);
        }
        for (int i = 0; i < codeLength - value.length(); i++) {
            code.insert(0, "0");
        }
        return code.toString();
    }
}
