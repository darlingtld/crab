package createsh.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
