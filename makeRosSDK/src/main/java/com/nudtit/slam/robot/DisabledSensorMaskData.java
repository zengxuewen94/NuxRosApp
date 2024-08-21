package com.nudtit.slam.robot;

/**
 * @author :  zengxuewen
 * @date :  2024/6/7
 * @desc :  TODO
 */
public class DisabledSensorMaskData {
    private int id;
    private boolean isAlways;

    public DisabledSensorMaskData(int id, boolean isAlways) {
        this.id = id;
        this.isAlways = isAlways;
    }

    public int getId() {
        return this.id;
    }

    public boolean getIsAlways() {
        return this.isAlways;
    }

    public void setId(int x) {
        this.id = x;
    }

    public void setIsAlways(boolean y) {
        this.isAlways = y;
    }
}
