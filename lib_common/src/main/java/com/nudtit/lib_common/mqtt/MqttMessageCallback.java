package com.nudtit.lib_common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author :  zengxuewen
 * @date :  2024/5/21
 * @desc :  TODO
 */
public interface MqttMessageCallback {
    void messageArrived(String topic, MqttMessage message);
}
