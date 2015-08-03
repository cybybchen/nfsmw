package com.ea.eamobile.nfsmw.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

/**
 * 字符串操作类
 * 
 * @author ma.ruofei@ea.com
 * @version 1.0 2009-06-23 11:32:57
 * @since 1.0
 */
public class StringUtil {
    /** 单例 */
    private static final StringUtil instance = new StringUtil();
    /** 随机数对象 */
    private static final Random random = new Random();
    /** 数字与字母字典 */
    private static final char[] LETTER_AND_DIGIT = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();
    /** 字母字典 */
    private static final char[] LETTER = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    /** 数字与字母字典长度 */
    private static final int LETTER_AND_DIGIT_LENGTH = LETTER_AND_DIGIT.length;
    /** 字母字典长度 */
    private static final int LETTER_LENGTH = LETTER.length;

    private StringUtil() {
    }

    /**
     * 取得该类唯一实例
     * 
     * @return 该类唯实例
     */
    public static StringUtil instance() {
        return instance;
    }

    /**
     * 检测字符串是否为空字符串。 字符串为空的标准：null或全部由空字符组成的字符串
     * 
     * @param input
     *            待检测字符串
     * @return <li>true：字符串是空字符串</li> <li>false：字符串不是空字符串</li>
     */
    public static boolean isEmpty(String input) {
        return (input == null || input.trim().length() == 0);
    }

    /**
     * 将对象转换为字符串
     * 
     * @param input
     *            待转换对象
     * @return 转换后的字符串
     * @see #getString(Object, String)
     * @see #getString(String)
     * @see #getString(String, String)
     */
    public static String getString(Object input) {
        return getString(input, "");
    }

    /**
     * 将对象转换为字符串
     * 
     * @param input
     *            待转换对象
     * @param defVal
     *            对象转换为空字符串时的默认返回值
     * @return 转换后的字符串
     * @see #getString(String)
     * @see #getString(String, String)
     */
    public static String getString(Object input, String defVal) {
        return (input == null) ? defVal : getString(input.toString(), defVal);
    }

    /**
     * 转换字符串
     * 
     * @param input
     *            待转换字符串
     * @return 转换后的字符串
     * @see #getString(String, String)
     */
    public static String getString(String input) {
        return getString(input, "");
    }

    /**
     * 转换字符串
     * 
     * @param input
     *            待转换字符串
     * @param defVal
     *            默认转换值
     * @return 转换后的字符串 <li>字符串为null或全部由空白字符组成的字符串时，返回defVal参数指定的值</li> <li>其他情况，返回去掉字符串两端空白字符后的字符串</li>
     */
    public static String getString(String input, String defVal) {
        return (isEmpty(input)) ? defVal : input.trim();
    }

    /**
     * 生成固定长度的随机数
     * 
     * @param len
     *            随机数长度
     * @return 生成的随机数
     */
    public static String getRandomString(final int len) {
        if (len < 1)
            return "";
        StringBuilder sb = new StringBuilder(len);
        sb.append(LETTER[random.nextInt(LETTER_LENGTH)]);
        for (int i = 1; i < len; i++) {
            sb.append(LETTER_AND_DIGIT[random.nextInt(LETTER_AND_DIGIT_LENGTH)]);
        }
        return sb.toString();
    }

    public static String md5(String plainText) {
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(plainText.getBytes("UTF8"));
            byte s[] = m.digest();
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    /**
     * 生成字符串的md5值
     * 
     * @param plainText
     * @return
     */
    public String MD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据匹配符取前缀字符串
     * 
     * @param str
     * @param c
     * @return
     */
    public static String getPrefix(String str, char c) {
        if (!isEmpty(str))
            str = str.substring(0, str.lastIndexOf(c));
        return str;
    }

    private static final String[] encodes = { "GB2312", "ISO-8859-1", "UTF-8", "GBK" };

    public static String getEncoding(String str) {
        for (int i = 0; i < encodes.length; i++) {
            String encode = encodes[i];
            try {
                if (str.equals(new String(str.getBytes(encode), encode))) {
                    return encode;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return "UTF-8";
    }

    public static ArrayList<String> sortString(ArrayList<String> a) {
        String str1 = "";
        String str2 = "";
        int flag;
        int y = a.size();
        for (int i = 0; i < a.size() - 1; i++) {
            for (int j = 0; j < y - 1; j++) {
                str1 = a.get(i);
                str2 = a.get(i + 1);
                flag = str1.compareToIgnoreCase(str2);
                if (flag >= 0) {
                    a.set(i, str2);
                    a.set(i + 1, str1);
                }

            }
            y = y - 1;
        }
        return a;

    }

}
