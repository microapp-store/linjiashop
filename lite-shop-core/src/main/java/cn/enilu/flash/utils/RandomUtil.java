package cn.enilu.flash.utils;

import java.util.Random;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/29 22:48
 */
public class RandomUtil {
    static  String number = "0123456789";
    static String str = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static String getRandomNumber(int length) {

        return getRandom(length, number);
    }
    public static String getRandomString(int length) {
        return getRandom(length, str);
    }

    public static String getRandom(int length, String base) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
