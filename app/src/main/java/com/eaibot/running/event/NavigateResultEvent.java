package com.eaibot.running.event;

/**
 * @author :  zengxuewen
 * @date :  2023/1/30
 * @desc :  导航结果
 */
public class NavigateResultEvent {

    // 导航结果
    private boolean success;
    // 是否充电中
    private boolean charging;


    public NavigateResultEvent(boolean success, boolean charging) {
        this.success = success;
        this.charging = charging;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }
}
