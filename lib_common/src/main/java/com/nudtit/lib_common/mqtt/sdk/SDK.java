package com.nudtit.lib_common.mqtt.sdk;


import com.nudtit.lib_common.mqtt.MqttMessageCallback;
import com.nudtit.lib_common.mqtt.event.ConnectStateEvent;
import com.nudtit.lib_common.mqtt.event.MqttConnectEvent;
import com.nudtit.lib_common.mqtt.event.MqttResetEvent;
import com.nudtit.lib_common.mqtt.event.PublishMessageEvent;
import com.nudtit.lib_common.mqtt.event.SubscribeTopicEvent;
import com.nudtit.lib_common.utils.LogUtils;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc :
 */
public class SDK {
    //SDK对象
    private static SDK SDK;
    String TAG = "nudtit_mqtt";

    public static SDK getInstance() {
        if (SDK == null) {
            SDK = new SDK();
        }
        return SDK;
    }

    boolean isConnected = false;//mqtt是否连接
    private MqttMessageCallback mqttCallback;
    private IMqttActionListener iMqttActionListener;
    private HashMap<String, String> map = new HashMap<>();


    public MqttMessageCallback getMqttCallback() {
        return mqttCallback;
    }

    /**
     * 设置连接状态
     *
     * @param iMqttActionListener
     */
    public void setiMqttActionListener(IMqttActionListener iMqttActionListener) {
        this.iMqttActionListener = iMqttActionListener;
    }

    public IMqttActionListener getiMqttActionListener() {

        return iMqttActionListener;
    }

    public HashMap<String, String> getMap() {
        return map;
    }


    /**
     * 发送消息
     *
     */
    public void publishMessage(String topicName,String message,int qos) {
        EventBus.getDefault().post(new PublishMessageEvent(topicName, message, qos));
    }

    /**
     * 订阅主题
     *
     */
    public void subscribeTopic(String topicName,int qos) {
        EventBus.getDefault().post(new SubscribeTopicEvent(topicName, qos));
    }



    /**
     * 手动发起mqtt连接
     *
     */
    public void connect() {
        EventBus.getDefault().post(new MqttConnectEvent());
    }

    /**
     * 获取连接状态
     *
     */
    public void connectState() {
        EventBus.getDefault().post(new ConnectStateEvent());
    }

    /**
     * 设置订阅消息回调
     *
     * @param mqttCallback
     */
    public void setMessageArrivedCallback(MqttMessageCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
    }

    /**
     * <p>消息格式
     * {
     * "id": "2a72ddb8-7a84-4a57-b745-97698716fb9e",
     * "method": "thing.event.property.post",
     * "params":{
     * 参考《机器人上报属性参照表》,可分时间段上报部分属性或同时上报所有属性。
     * }
     * }
     * </p>
     *
     * @param topTic    主题
     * @param messageId 消息id
     * @param method    方法字段
     * @param params    参数
     */
    public void publishMessage(String topTic, String messageId, String method, JSONObject params) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", messageId);
            object.put("method", method);
            object.put("params", params);
        } catch (JSONException jsonException) {
            LogUtils.d(TAG, "数据组合出错");
        }

        LogUtils.d(TAG, "上传数据：" + object.toString());
        publishMessage(topTic, object.toString(), 0);
    }


    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isConnected() {
        connectState();
        return isConnected;
    }

    public void resetMqtt(){
        EventBus.getDefault().post(new MqttResetEvent());
    }

}
