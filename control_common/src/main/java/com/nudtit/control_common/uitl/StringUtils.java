package com.nudtit.control_common.uitl;

import static java.lang.String.format;

/**
 * @author :  zengxuewen
 * @date :  2022/8/19
 * @desc :  TODO
 */
public class StringUtils {
    /**
     * 保留两位小数
     * @param str
     * @return
     */
    public static String decimal2(Object str) {
        return format("%.2f", str);
    }


    public static String decimal1(Object str) {
        return format("%.1f", str);
    }
}
