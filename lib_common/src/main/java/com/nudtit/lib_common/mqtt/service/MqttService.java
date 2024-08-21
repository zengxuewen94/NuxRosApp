package com.nudtit.lib_common.mqtt.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nudtit.lib_common.mqtt.common.CommonLog;
import com.nudtit.lib_common.mqtt.event.ConnectStateEvent;
import com.nudtit.lib_common.mqtt.event.MqttConnectEvent;
import com.nudtit.lib_common.mqtt.event.MqttResetEvent;
import com.nudtit.lib_common.mqtt.event.PublishMessageEvent;
import com.nudtit.lib_common.mqtt.event.SubscribeTopicEvent;
import com.nudtit.lib_common.mqtt.event.UploadMessageEvent;
import com.nudtit.lib_common.mqtt.manager.MyThreadManager;
import com.nudtit.lib_common.mqtt.sdk.SDK;
import com.nudtit.lib_common.mqtt.utils.MD5Util;
import com.nudtit.lib_common.utils.LogUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc :
 */
public class MqttService extends Service {

    public static final String TAG = MqttService.class.getSimpleName();

    private MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    Context context;
    int connectNumber = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        context = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "onStartCommand");
        init();
        return super.onStartCommand(intent, flags, startId);
    }


    private void init() {
        //允许连接三次，不成功放弃连接
        if ((client == null || !client.isConnected()) && connectNumber <= 2) {
            connectNumber++;
            //如果有网则连接
            MyThreadManager.getInstance().execute(() -> {
                String clientId = CommonLog.mProductKey + "_" + CommonLog.mDeviceName + "_" + CommonLog.mDeviceModel;
                // 服务器地址（协议+地址+端口号）
                String uri = CommonLog.host;
                String userName = CommonLog.mDeviceName; //mqtt用户名称
                if (client == null) {
                    client = new MqttAndroidClient(context, uri, clientId);
                }
                // 设置MQTT监听并且接受消息
                client.setCallback(mqttCallback);
                conOpt = new MqttConnectOptions();
                // 清除缓存
                conOpt.setCleanSession(true);
                // 设置超时时间，单位：秒
                conOpt.setConnectionTimeout(10);
                // 心跳包发送间隔，单位：秒
                conOpt.setKeepAliveInterval(20);
                // 用户名
                conOpt.setUserName(userName);
                // 密码
                conOpt.setPassword(MD5Util.getMD5String(CommonLog.authCode + clientId).toCharArray());
                doClientConnection();
            });
        }
    }


    @Override
    public void onDestroy() {
        try {
            if (client != null) {
                client.disconnect();  //服务销毁,断开连接
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (!client.isConnected()) {
            Log.d(TAG, "连接mqtt");
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 发布 （向服务器发送消息）
     *
     * @param topic   主题
     * @param message 消息
     */
    private void uploadMessage(String topic, String message) {
        if (client != null && client.isConnected()) {
            if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(message)) {
                Boolean retained = true;
                try {
                    //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
                    client.publish(topic, message.getBytes(), 0, retained, null, new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            LogUtils.d(TAG, "Mqtt发送消息成功：" + message + topic);

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                        }
                    });
                } catch (MqttException e) {

                }
            }
        }
    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.d(TAG, "连接成功");
            if (SDK.getInstance().getiMqttActionListener() != null) {
                SDK.getInstance().getiMqttActionListener().onSuccess(arg0);
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            Log.d(TAG, "连接失败" + arg1.getMessage());
            // 连接失败，重连
            if (SDK.getInstance().getiMqttActionListener() != null) {
                SDK.getInstance().getiMqttActionListener().onFailure(arg0, arg1);
            }
        }
    };

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            if (!TextUtils.isEmpty(topic) && message != null) {
                //subscribe后得到的消息会执行到这里面
                Log.d(TAG, "subscribe后得到的消息会执行到这里面messageArrived----------" + topic + message);
                SDK.getInstance().getMqttCallback().messageArrived(topic, message);
            }
        }


        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {

            if (SDK.getInstance().getMqttCallback() != null) {

            }
        }

        @Override
        public void connectionLost(Throwable arg0) {
            Log.d(TAG, "失去连接：" + arg0.getMessage());


        }
    };

    /**
     * 向订阅的主题发布消息
     *
     * @param topic   订阅的主题
     * @param payload 消息载荷
     * @param qos     设置消息发送质量，可为0,1,2.
     */
    private void publishMessage(String topic, String payload, int qos) {
        if (client != null) {
            try {
                MqttMessage message = new MqttMessage();
                message.setPayload(payload.getBytes());
                message.setQos(qos);
                client.publish(topic, message, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i(TAG, topic + "publish succeed!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i(TAG, topic + "publish failed!");
                    }
                });
            } catch (Exception e) {
                Log.i(TAG, "publish failed!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 订阅特定的主题
     *
     * @param topicName mqtt主题
     * @param qos       设置消息发送质量，可为0,1,2.
     */
    private void subscribeTopic(String topicName, int qos) {
        try {
            client.subscribe(topicName, qos, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "subscribed succeed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "subscribed failed");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅特定的主题
     *
     * @param topicName mqtt主题
     */
    private void unSubscribeTopic(String topicName) {
        try {
            client.unsubscribe(topicName);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 订阅主题
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void subscribeTopic(SubscribeTopicEvent event) {

        subscribeTopic(event.getTopicName(), event.getQos());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void uploadMessage(UploadMessageEvent event) {
        uploadMessage(event.getTopicName(), event.getMessage());
    }


    /**
     * 向特定的主题推送消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void sendMessage(PublishMessageEvent event) {
        publishMessage(event.getTopicName(), event.getMessage(), event.getQos());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void connect(MqttConnectEvent event) {
        init();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void isConnected(ConnectStateEvent event) {

        SDK.getInstance().setConnected(client != null && client.isConnected());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void resetMqtt(MqttResetEvent event) {
        connectNumber = 0;
    }
}
