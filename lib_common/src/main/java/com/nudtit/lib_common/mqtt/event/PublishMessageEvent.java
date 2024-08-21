package com.nudtit.lib_common.mqtt.event;

/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc : 向主题推送消息
 */

public class PublishMessageEvent {
    String topicName,message;
    int qos;

    public PublishMessageEvent(String topicName, String message, int qos) {
        this.topicName = topicName;
        this.message = message;
        this.qos = qos;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
