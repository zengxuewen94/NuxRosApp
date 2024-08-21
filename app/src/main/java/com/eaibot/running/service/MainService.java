package com.eaibot.running.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.eaibot.running.R;
import com.eaibot.running.base.BaseApplication;
import com.eaibot.running.bean.NavigateTaskBean;
import com.eaibot.running.constants.BaseConstant;
import com.eaibot.running.db.SlamLocationDBManager;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.event.GoHomeEvent;
import com.eaibot.running.event.MoveToEvent;
import com.eaibot.running.event.NavigateResultEvent;
import com.eaibot.running.event.RobotInfoEvent;
import com.eaibot.running.event.RosConnectStatusEvent;
import com.eaibot.running.manger.BaseDataManager;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.manger.ThreadPoolManager;
import com.google.gson.JsonSyntaxException;
import com.http.retrofit.utils.GsonUtils;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.lib_common.mode.DeviceInfo;
import com.nudtit.lib_common.mqtt.MqttMessageCallback;
import com.nudtit.lib_common.mqtt.bean.MqttMessageBean;
import com.nudtit.lib_common.mqtt.bean.RobotControlBean;
import com.eaibot.running.constants.CommonTopic;
import com.nudtit.lib_common.mqtt.common.RobotControlConstant;
import com.nudtit.lib_common.mqtt.manager.NudtitMqttManager;
import com.nudtit.lib_common.mqtt.sdk.SDK;
import com.nudtit.lib_common.utils.CollectionUtils;
import com.nudtit.lib_common.utils.CpuUtil;
import com.nudtit.lib_common.utils.ListUtils;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.NetworkUtils;
import com.nudtit.lib_common.utils.TimeUtils;
import com.nudtit.lib_common.utils.Utils;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :  zengxuewen
 * @date :  2024/5/10
 * @desc :  主service
 */
public class MainService extends Service {
    private static final String TAG = MainService.class.getSimpleName();
    private BroadcastReceiver rosServiceBroadcastReceiver;

    // 底盘连接成功
    private boolean connectSuccess;
    //是否需要连接机器人
    private boolean needConnectRos = true;
    //连接失败的次数
    private int connectRosFailedTimes = 0;
    // 需要导航的位置点列表
    private List<SlamLocationBean> mSlamLocationList;
    //原始的导航数据
    private List<SlamLocationBean> mOriginalList;
    //保存的位置点列表
    private List<SlamLocationBean> mList;
    private ScheduledThreadPoolExecutor scheduledThreadPool;
    //导航次数
    private int mNavNumber = 1;
    //电量
    private int battery;
    //上传基础信息计数
    private int uploadInfoNumber = 0;

    private boolean isCharging;
    private boolean isUploadLocation = true;
    private String url="ws://192.168.0.200:9090";
    @Override
    public void onCreate() {
        //init();
        super.onCreate();
    }


    private void init() {

        EventBus.getDefault().register(this);
        connectROS();
        initMqtt();
        startTimerTask();

    }


    /**
     * 连接ROS底盘
     */
    private void connectROS() {

        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //这里失败后连接两次不成功放弃连接
                while (needConnectRos && connectRosFailedTimes < 2) {
                    connectSuccess = RosDeviceManager.INSTANCE.connect(url);//ws://192.168.0.150:9090
                    if (connectSuccess) {
                        RosDeviceManager.INSTANCE.startNavigate();
                        needConnectRos = false;
                        //获取
                        String mapName = RosDeviceManager.INSTANCE.getCurrentMap();
                        LogUtils.d(TAG, mapName);
                        //保存底盘当前加载的地图
                        BaseDataManager.INSTANCE.setLoadMapName(mapName);
                        //查询保存的位置点列表
                        mList = SlamLocationDBManager.getInstance(Utils.getContext()).queryLocationListByMap(mapName);
                        startService(new Intent(Utils.getContext(), RosService.class));

                        break;
                    } else {
                        LogUtils.d("TAG", Utils.getString(R.string.robotNoService));
                        connectRosFailedTimes++;
                        if (connectRosFailedTimes >= 2) {
                            needConnectRos = true;
                        }
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void initMqtt() {
        //GKBT002000   GKBT001000
        NudtitMqttManager.getInstance(BaseApplication.getApplication()).initMqtt("m1",
                "tcp://120.79.96.196:5620", "GKBT001000", "NudtitRobot2024");
        //设置连接状态回调


        String topTicName = "/sys/GKBT001000/" + DeviceInfo.INSTANCE.getDeviceId();
        //设备在线状态
        CommonTopic.MQTT_LINE_STATE = topTicName + "/c/#";
        //设备上报信息
        CommonTopic.MQTT_PROPERTY_PUSH = topTicName + "/s/event/property/post";
        //上传地图文件
        CommonTopic.MQTT_UPLOAD_MAP_DATA = topTicName + "/s/event/addRouteMap";
        //控制机器指令
        CommonTopic.MQTT_CONTROL_ROBOT_COMMAND = topTicName + "/c/service/command";

        //推送讲解任务
        CommonTopic.MQTT_PUSH_EXPLAIN_TASK = topTicName + "/c/service/pushExplanationTask";





        SDK.getInstance().setiMqttActionListener(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // mqtt 连接成功后，订阅主题消息
                SDK.getInstance().subscribeTopic(CommonTopic.MQTT_LINE_STATE, 0);
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
        //设置订阅消息回调
        SDK.getInstance().setMessageArrivedCallback(new MqttMessageCallback() {
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                try {
                    MqttMessageBean mqttMessageBean = GsonUtils.GsonToBean(message.toString(), MqttMessageBean.class);
                    if (TextUtils.equals(CommonTopic.MQTT_CONTROL_ROBOT_COMMAND, topic)) {//控制机器人移动（前后左右/充电/取消任务）
                        RobotControlBean robotControlBean = GsonUtils.GsonToBean(mqttMessageBean.getParams().toString(), RobotControlBean.class);
                        processIntent(robotControlBean.getCmd());
                    } else if (TextUtils.equals(CommonTopic.MQTT_UPLOAD_MAP_DATA + "_reply", topic)) {
                        isUploadLocation = false;
                    } else if (TextUtils.equals(CommonTopic.MQTT_PUSH_EXPLAIN_TASK, topic)) {//导航任务
                        NavigateTaskBean taskBean = GsonUtils.GsonToBean(mqttMessageBean.getParams().toString(), NavigateTaskBean.class);
                        if (null != taskBean) {
                            List<String> positions = new ArrayList<>();
                            String position = taskBean.getPoints();
                            //判断是否有多个位置点
                            if (position.contains(",")) {
                                positions.addAll(Arrays.asList(position.split(",")));
                            } else {
                                positions.add(position);
                            }
                            if (null == mSlamLocationList) {
                                mSlamLocationList = new ArrayList<>();
                            }
                            if (null == mOriginalList) {
                                mOriginalList = new ArrayList<>();
                            }
                            mSlamLocationList.clear();
                            mOriginalList.clear();
                            for (int i = 0; i < positions.size(); i++) {
                                String name = positions.get(i);
                                if (name.contains("-")) {//包含"-" 替换"#"
                                    name = name.replace("-", "#");
                                }
                                SlamLocationBean slamLocationBean = SlamLocationDBManager.getInstance(Utils.getContext()).queryLocationListByLocationName(name, RosDeviceManager.INSTANCE.getCurrentMap());
                                mSlamLocationList.add(slamLocationBean);
                                mOriginalList.add(slamLocationBean);
                            }
                            mNavNumber = taskBean.getCount();
                            onStartNav(true);
                        }
                    }
                } catch (JsonSyntaxException ignored) {

                }
            }
        });

    }


    /**
     * 处理控制移动命令
     *
     * @param cmd
     */
    private void processIntent(String cmd) {
        if (TextUtils.isEmpty(cmd)) {
            return;
        }
        switch (cmd) {
            case RobotControlConstant.ACTION_FORWARD://前进
                RosDeviceManager.INSTANCE.moveBy(MoveDirection.FORWARD);
                break;
            case RobotControlConstant.ACTION_BACK://后退
                RosDeviceManager.INSTANCE.moveBy(MoveDirection.BACKWARD);
                break;
            case RobotControlConstant.ACTION_LEFT://向左
                RosDeviceManager.INSTANCE.moveBy(MoveDirection.TURN_LEFT);
                break;
            case RobotControlConstant.ACTION_RIGHT://向右
                RosDeviceManager.INSTANCE.moveBy(MoveDirection.TURN_RIGHT);
                break;
            case RobotControlConstant.ACTION_STOP://停止
                RosDeviceManager.INSTANCE.actionCancel();
                break;
            case RobotControlConstant.ACTION_CANCEL://取消所有任务
                RosDeviceManager.INSTANCE.cancelAllAction();
                break;
            case RobotControlConstant.ACTION_HOME://回充
                EventBus.getDefault().post(new GoHomeEvent());
                break;
            default:
                break;
        }
    }


    private void startTimerTask() {
        scheduledThreadPool = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            String ipAddress = "https://www.baidu.com/";
            AtomicInteger state = new AtomicInteger();
            try {
                //读取cpu序列号作为唯一标识
                if (TextUtils.isEmpty(DeviceInfo.INSTANCE.getDeviceId())) {
                    DeviceInfo.INSTANCE.setDeviceId(CpuUtil.getCPUSerial());
                }
                //上传机器基础信息 每15s上传一次
                if (uploadInfoNumber % 15 == 0) {
                    uploadRobotInfo();
                }
                if (uploadInfoNumber % 150 == 0) {
                    //重置mqtt 连接次数
                    SDK.getInstance().resetMqtt();
                    // 判断底盘是否连接，没有连接则重置状态
                    if (!connectSuccess && uploadInfoNumber != 0) {
                        needConnectRos = true;
                        connectRosFailedTimes = 0;
                        connectROS();
                    }
                }
                uploadInfoNumber += 5;
                //获取网络状态
                URL url = new URL(ipAddress);
                HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                state.set(httpUrlConnection.getResponseCode());
                uploadMapPoints(mList);
            } catch (IOException e) {
                state.set(400);
            }

        }, 0, 5, TimeUnit.SECONDS);
    }


    /**
     * 上传机器基础信息
     */
    private void uploadRobotInfo() {

        //mqtt是连接状态，上传基础信息
        if (SDK.getInstance().isConnected()) {
            if (connectSuccess) {
                SDK.getInstance().publishMessage(CommonTopic.MQTT_PROPERTY_PUSH,
                        DeviceInfo.INSTANCE.getDeviceId() + TimeUtils.getCurrentTime2() + (new Random().nextInt(9000) + 1000),
                        "thing.event.property.post",
                        commonMqttParams());
            }
        } else {
            NudtitMqttManager.getInstance(BaseApplication.getApplication()).onConnectMqtt();
        }


    }

    /**
     * 组合上传数据，返回json格式对象
     *
     * @return
     */
    private JSONObject commonMqttParams() {
        //默认空闲状态
        int status = 0;
        JSONObject jsonObject = new JSONObject();
        try {
            // 机器唯一编码
            jsonObject.put("dn", DeviceInfo.INSTANCE.getDeviceId());
            // 实时电量
            jsonObject.put("battery", battery);
            // 回充电量
            jsonObject.put("rechargeLevel", 20);
            // 是否充电中
            jsonObject.put("charging", isCharging ? 1 : 0);
            // 状态
            jsonObject.put("status", status);
            // 状态
            jsonObject.put("ip", NetworkUtils.getIpAddress());
            // 状态
            jsonObject.put("lcip", url);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * 上传当前位置点
     *
     * @param currentPoint
     */
    private void uploadCurrentPoint(String currentPoint) {
        JSONObject jsonObject = new JSONObject();
        try {
            // 机器唯一编码
            jsonObject.put("dn", DeviceInfo.INSTANCE.getDeviceId());
            // 机器当前位置
            jsonObject.put("currentPoint", currentPoint);

            SDK.getInstance().publishMessage(CommonTopic.MQTT_PROPERTY_PUSH,
                    DeviceInfo.INSTANCE.getDeviceId() + TimeUtils.getCurrentTime2() + (new Random().nextInt(9000) + 1000),
                    "thing.event.property.post",
                    jsonObject);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }


    /**
     * 上传地图数据
     *
     * @param dataList
     */
    private void uploadMapPoints(List<SlamLocationBean> dataList) {

        if (ListUtils.isNotEmpty(dataList) && isUploadLocation) {
            LogUtils.d(TAG, "upload上传地图数据");
            try {
                JSONObject paramsObject = new JSONObject();
                JSONArray pointArray = new JSONArray();
                paramsObject.put("mapName", BaseDataManager.INSTANCE.getLoadMapName());
                //遍历位置点列表
                for (SlamLocationBean locationBean : dataList) {
                    JSONObject pointObject = new JSONObject();
                    String name = locationBean.getLocationNameChina();
                    if (name.contains("进电梯") || name.contains("电梯中") || name.contains("出电梯")) {
                        continue;
                    }
                    if (name.contains("#")) {//包含"#" 替换"&&"
                        name = name.replace("#", "-");
                    }
                    //位置点名称包含“,”,分割取第一个
                    if (name.indexOf(",") != -1) {
                        List<String> result = Arrays.asList(name.split(","));
                        pointObject.put("pointName", result.get(0));
                    } else {
                        pointObject.put("pointName", name);
                    }
                    pointObject.put("pointId", locationBean.getTableId());
                    pointArray.put(pointObject);
                }

                paramsObject.put("points", pointArray);
                LogUtils.d(TAG, "上传位置点信息：" + paramsObject);

                SDK.getInstance().publishMessage(CommonTopic.MQTT_UPLOAD_MAP_DATA,
                        DeviceInfo.INSTANCE.getDeviceId() + TimeUtils.getCurrentTime2() + (new Random().nextInt(9000) + 1000),
                        "thing.event.addRouteMap", paramsObject);

            } catch (JSONException jsonException) {
                LogUtils.d(TAG, "数据组合出错");
            }
        }

    }


    /**
     * wifi连接状态
     *
     * @param wifiConnected
     */
    public void wifiConnect(boolean wifiConnected) {
        if (wifiConnected) {
            connectRosFailedTimes = 0;
            connectROS();
        } else {
            needConnectRos = true;
        }
    }

    /**
     * 机器人信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotInfo(RobotInfoEvent event) {
        if (null != event) {
            isCharging = event.getPowerSupplyStatus() == 1;
            battery = event.getBatteryPercentage();
        }
    }


    /**
     * 开始导航
     *
     * @param mFirstNav 是否首次导航
     */
    private void onStartNav(boolean mFirstNav) {
        //导航点非空，执行导航任务
        if (CollectionUtils.isNotEmpty(mSlamLocationList)) {
            EventBus.getDefault().post(new MoveToEvent(mSlamLocationList.get(0)));
        } else {//没有导航点了
            //不是首次导航,导航次数减一
            if (!mFirstNav) {
                mNavNumber--;
            }
            LogUtils.d(TAG, "mNavNumber：" + mNavNumber);
            //判断导航次数，次数小于一次，已完成此次的导航任务，回去充电
            if (mNavNumber < 1) {
                EventBus.getDefault().post(new GoHomeEvent());
            } else {
                mSlamLocationList.addAll(mOriginalList);
                onStartNav(false);
            }
        }

    }


    /**
     * 导航结果
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNavigateEvent(NavigateResultEvent event) {
        if (!event.isCharging()) {
            if (CollectionUtils.isNotEmpty(mSlamLocationList)) {

                uploadCurrentPoint(mSlamLocationList.get(0).getLocationNameChina());
                //移除第一个位置点
                mSlamLocationList.remove(mSlamLocationList.get(0));
            }
            //继续导航
            onStartNav(false);
        }
    }


    /**
     * 断开连接
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRosConnect(RosConnectStatusEvent event) {
        if (event.isConnect()) {
            connectSuccess = true;
            needConnectRos = false;
            LogUtils.d("RosDeviceManager", "onConnect:连接成功");
            BaseConstant.rosConnected = true;
        } else {
            if (connectSuccess) {
                connectRosFailedTimes = 0;
            }
            needConnectRos = true;
            connectSuccess = false;
            BaseConstant.rosConnected = false;

        }
    }


    @Override
    public void onDestroy() {
        LogUtils.e(TAG, "onDestroy()");
        RosDeviceManager.INSTANCE.closeConnect();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
