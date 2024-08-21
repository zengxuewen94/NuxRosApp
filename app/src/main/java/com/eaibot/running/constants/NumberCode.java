package com.eaibot.running.constants;

/**
 *
 * @author Yist
 * @date 2018/1/10
 */

public interface NumberCode {

    /**
     * 当前状态为自转360
     */
    int TURN360CODE = 2;

    /**
     * 更新电量的累加值
     */
    int REFRESH_VOLTAGE = 1500;

    /**
     * CD充电
     */
    int CHARGE_CD = 2;

    /**
     * 两种充电方式同时进行
     */
    int CHARGE_WAY_ALL = 3;

    /**
     * 超声波检测最大值
     */
    float MAX_SONAR_DISTANCE = 0.8f;

    /**
     * 超声波未插入返回值
     */
    float NO_SONAR_CODE = 1.0f;

    /**
     * 返回键的键值
     */
    int BACK_CODE = 4;

    /**
     * 虚拟摇杆触摸感应最小值
     */
    float PARENT_MIN_SIZE = 200f;

    /**
     * 虚拟摇杆触摸感应最大值
     */
    float PARENT_MAX_SIZE = 400f;

    /**
     * 虚拟摇杆半径规范值
     */
    float CONTACT_RADIUS = 1f;

    /**
     * 360度常量值
     */
    float ANGLE360 = 360;

    /**
     * 90度常量值
     */
    float ANGLE90 = 90;

    /**
     * 270度常量值
     */
    float ANGLE270 = 270;

}
