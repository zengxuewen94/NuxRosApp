package com.nudtit.lib_common.mqtt.utils;

import java.security.MessageDigest;

public class MD5Util {

    public static String getMD5String(String key) {
        char hexDigits[] = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] input = key.getBytes();
            // MD5算法的 MessageDigest 对象
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            byte[] digest = md5Digest.digest(key.getBytes());
            String passwordMd5 = toHexString(digest);
            return passwordMd5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        String hexStr;
        for (byte b : digest) {
            hexStr = Integer.toHexString(b & 0xFF);//& 0xFF处理负数
            if (hexStr.length() == 1) {//长度等于1，前面进行补0，保证最后的字符串长度为32
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }

        return sb.toString();
    }

}