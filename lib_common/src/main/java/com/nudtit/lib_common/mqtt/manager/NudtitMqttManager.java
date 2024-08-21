package com.nudtit.lib_common.mqtt.manager;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.nudtit.lib_common.mqtt.common.CommonLog;

import com.nudtit.lib_common.mqtt.service.MqttService;
import com.nudtit.lib_common.utils.CpuUtil;


/**
 * @author :  zengxuewen
 * @date :  2024/5/9
 * @desc : mqtt通信管理类
 * <p>
 * 调用：
 * 1,先初始化数据initMqtt;
 * 2,连接onConnectMqtt
 * </p>
 */

public class NudtitMqttManager {
    private static NudtitMqttManager fgIotManager;
    private Application application;

    //获取FgIotLogManager实例
    public static NudtitMqttManager getInstance(Application application) {
        //判断fgIotLogManager是否为空
        if (fgIotManager == null) {
            //加锁
            synchronized (NudtitMqttManager.class) {
                //再次判断fgIotLogManager是否为空
                if (fgIotManager == null) {
                    //创建FgIotLogManager实例
                    fgIotManager = new NudtitMqttManager(application);
                }
            }
        }
        //返回FgIotLogManager实例
        return fgIotManager;
    }

    private NudtitMqttManager(Application application) {
        this.application = application;
    }


    /**
     * 初始化
     *
     * @param deviceModel 设备型号，如m1
     * @param host        mqtt服务器地址 tcp://120.79.96.196
     * @param productKey  产品ID
     * @param authCode    产品密钥
     */
    public void initMqtt(String deviceModel, String host, String productKey, String authCode) {
        //设备序列号
        CommonLog.mDeviceName = CpuUtil.getCPUSerial();
        //设备型号
        CommonLog.mDeviceModel = deviceModel;
        //设备身份
        CommonLog.mProductKey = productKey;
        //产品密钥
        CommonLog.authCode = authCode;
        //主机地址
        CommonLog.host = host;



    }

    /**
     * 连接mqtt
     */
    public void onConnectMqtt() {
        if (TextUtils.isEmpty(CommonLog.host)) {
            throw new IllegalArgumentException("mqtt连接地址不能为空");
        }

        if (null != application) {
            //启动MQService服务
            application.startService(new Intent(application, MqttService.class));
        }
    }

}
