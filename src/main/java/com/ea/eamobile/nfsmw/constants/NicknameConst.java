package com.ea.eamobile.nfsmw.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NicknameConst {
    private static final Logger log = LoggerFactory.getLogger(NicknameConst.class);

    private static final String NICKNAME_PREFIX = "飞车手";
    private static final String BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static int charValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A';
        }
        return -1;
    }

    private static String encode64(String hex) {
        StringBuffer ret = new StringBuffer();
        int validCount = 0;
        int tempValue = 0;
        for (int i = 0; i < hex.length(); ++i) {
            char c = hex.charAt(i);
            int cv = charValue(c);
            if (cv < 0)
                continue;
            tempValue = (tempValue << 4) + cv;
            validCount++;

            if (validCount == 3) {
                char x = BASE64.charAt(tempValue / 64);
                ret.append(x);
                char y = BASE64.charAt(tempValue % 64);
                ret.append(y);
                
                validCount = 0;
                tempValue = 0;
            }
        }
        if (validCount != 0 || tempValue != 0) {
            log.warn("Abnormal mac address as deviceId {}", hex);
        }
        return ret.toString();
    }

    private static String encode64(long timestamp) {
        long left = timestamp;
        StringBuffer ret = new StringBuffer();
        while(left > 64) {
            int c = (int) (left % 64);
            left = left / 64;
            ret.append(BASE64.charAt(c));
        }
        return ret.toString();
    }

    public static String nicknameByDeviceId(String deviceId) {
        return NICKNAME_PREFIX + encode64(deviceId);
    }

    public static String nicknameByTimestamp() {
        return NICKNAME_PREFIX + encode64(System.currentTimeMillis());
    }
    
    public static void main(String[] args) {
        System.out.println(nicknameByDeviceId("A4:BB:CC:DD:EE:0F"));
        System.out.println(nicknameByTimestamp());
    }
}
