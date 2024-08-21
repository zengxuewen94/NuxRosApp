package com.nudtit.lib_common.mqtt.bean;

import com.google.gson.JsonElement;

/**
 * @author :  zengxuewen
 * @date :  2024/5/13
 * @desc :  mqtt消息实体类
 */
public class MqttMessageBean {
    JsonElement params;
    String id;
    String method;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JsonElement getParams() {
        return params;
    }

    public void setParams(JsonElement params) {
        this.params = params;
    }
}
