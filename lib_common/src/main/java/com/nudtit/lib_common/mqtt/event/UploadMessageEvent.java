package com.nudtit.lib_common.mqtt.event;

/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc :
 */

public class UploadMessageEvent {
    String topicName,message;


    public UploadMessageEvent(String topicName, String message){
        this.topicName = topicName;
        this.message = message;

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


}
