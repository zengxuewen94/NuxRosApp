package com.nudtit.module_main.util;


/**
 * 角度弧度转换工具类
 */
public class RadianUtil {

    /**
     * 角度转弧度
     * @param angel 角度
     * @return
     */
    public static float toRadians(float angel) {
        return (float) (Math.PI * angel / 180);
    }

    /**
     * 弧度转角度
     * @param radians 弧度
     * @return
     */
    public static float toAngel(float radians) {
        return (float) (radians * 180 / Math.PI);
    }
}
