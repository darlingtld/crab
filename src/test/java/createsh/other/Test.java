package createsh.other;

import createsh.util.PropertyHolder;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by darlingtld on 2015/5/30 0030.
 */
public class Test {

    @org.junit.Test
    public void testReplace() throws UnsupportedEncodingException {
        String t = URLEncoder.encode(PropertyHolder.SERVER, "UTF-8");
        System.out.println(t);
    }


    @org.junit.Test
    public void filterEmoji() {
        String text = "aaaaadsf\\xF0\\x9F\\x98\\x97\\xF0\\x9F";
        System.out.println(stringFilter(text));
    }

    public String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字和中文//[\\pP‘’“”
        String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
        Pattern p = Pattern.compile(regEx);
        StringBuilder sb = new StringBuilder(str);

        for (int len = str.length(), i = len - 1; i >= 0; --i) {

            if (!p.matches(regEx, String.valueOf(str.charAt(i)))) {
                sb.deleteCharAt(i);
            }
        }

        return sb.toString();
    }

    @org.junit.Test
    public void codeGenTest() throws NoSuchAlgorithmException {
        char[] wechatId = "o5Irvt5957jQ4xmdHmDp59epk0UU".toCharArray();
        String plaintext = "o5Irvt5957jQ4xmdHmDp59epk0UU";
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }

        System.out.println(hashtext);
    }

    @org.junit.Test
    public void testPattern4Code(){
        String content = "123123223";
        System.out.println(Pattern.compile("\\d{9}").matcher(content).find());
    }

}
