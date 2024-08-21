package com.nudtit.lib_common.mqtt.event;

/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc :
 */
public class SubscribeTopicEvent {

    String topicName;
    int qos;

    public SubscribeTopicEvent(String topicName, int qos) {
        this.topicName = topicName;
        this.qos = qos;
    }


    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

}
