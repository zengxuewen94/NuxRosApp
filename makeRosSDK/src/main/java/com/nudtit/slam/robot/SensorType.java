package com.nudtit.slam.robot;

/**
 * @author :  zengxuewen
 * @date :  2024/6/7
 * @desc :  TODO
 */
public enum SensorType {
    Unknown,
    Bumper,
    Cliff,
    Sonar,
    DepthCamera,
    WallSensor,
    MagTapeDetector;

    private SensorType() {
    }
}
