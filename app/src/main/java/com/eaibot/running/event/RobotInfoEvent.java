package com.eaibot.running.event;

/**
 * @author :  zengxuewen
 * @date :  2022/9/2
 * @desc :  机器信息event【电量、充电状态、急停按钮】
 */
public class RobotInfoEvent {

    public RobotInfoEvent(int batteryPercentage, int powerSupplyStatus, int buttonStatus) {
        this.batteryPercentage = batteryPercentage;
        this.powerSupplyStatus = powerSupplyStatus;
        this.buttonStatus = buttonStatus;
    }

    //电量
    int batteryPercentage;
    //充电状态
    int powerSupplyStatus;
    //急停按钮状态
    int buttonStatus;

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public int getPowerSupplyStatus() {
        return powerSupplyStatus;
    }

    public int getButtonStatus() {
        return buttonStatus;
    }
}
