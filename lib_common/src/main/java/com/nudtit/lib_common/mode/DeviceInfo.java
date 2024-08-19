package com.nudtit.lib_common.mode;

/**
 * 设备的一些基本信息
 */
public enum DeviceInfo {

    /** 单列 */
    INSTANCE;

    private String deviceId;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

}
