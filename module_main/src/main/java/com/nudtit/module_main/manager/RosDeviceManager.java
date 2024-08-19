package com.nudtit.module_main.manager;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.nudtit.androidrosbridgeclient.RosBridgeClientManager;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.androidrosbridgeclient.interfaces.OnNavigateListener;
import com.nudtit.androidrosbridgeclient.ros.ROSClient;
import com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs.Pose;
import com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs.Quaternion;
import com.nudtit.androidrosbridgeclient.ros.message.nav_msgs.OccupancyGrid;
import com.nudtit.androidrosbridgeclient.ros.message.os_msgs.OSRequestCmd;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.module_main.bean.LocalPose;
import com.nudtit.module_main.bean.Location;
import com.nudtit.module_main.bean.Rotation;
import com.nudtit.module_main.event.RosDisconnectEvent;
import com.nudtit.module_main.util.RadianUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  【上位机-Ros商用底盘】操作管理类
 */
public enum RosDeviceManager {

    /**
     * 单列
     */
    INSTANCE;
    public String TAG = RosDeviceManager.class.getSimpleName();
    public String ip;
    private final RosBridgeClientManager manager = RosBridgeClientManager.INSTANCE;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 连接底盘
     *
     * @param uri 连接底盘的uri  "ws://192.168.31.119:9090"
     * @return 底盘连接结果
     */
    public synchronized boolean connect(String uri) {
        this.ip = uri;
        LogUtils.d(TAG, "connect:" + uri);
        try {
            return manager.connect(uri, connectionStatusListener);
        } catch (Exception e) {
            onErrorInfo(e);
        }
        return false;
    }

    /**
     * @return 地图列表
     */
    public synchronized List<String> getMapList() {
        try {
            return manager.getMapList();
        } catch (Exception e) {
            onErrorInfo(e);
        }
        return null;
    }


    /**
     * 加载地图
     *
     * @param mapName 地图名称 .yaml 结尾的文件
     * @return 地图加载结果
     */
    public synchronized boolean loadMap(String mapName) {
        try {
            return manager.LoadMap(mapName);
        } catch (Exception e) {
            onErrorInfo(e);
        }
        return false;
    }

    /**
     * 回充电桩
     *
     * @param onNavigateListener 底盘运动状态的回调
     */
    public synchronized void goHome(OnNavigateListener onNavigateListener) {
        manager.goHome(onNavigateListener);
    }


    /**
     * 取消当前动作,包含取消动作和回桩
     */
    public void actionCancel() {
        try {
            manager.actionCancel();
            // 不能同时发两条命令，延迟100ms
            HANDLER.postDelayed(this::cancelGoHome, 100);
        } catch (Exception e) {
            onErrorInfo(e);
        }

    }


    /**
     * 移动到指定位置
     *
     * @param x   x 轴坐标
     * @param y   y 轴坐标
     * @param yaw 弧度
     */
    public synchronized void moveTo(double x, double y, double yaw) {
        moveTo(x, y, yaw, null);
    }


    /**
     * 移动到指定位置
     *
     * @param x                  x 轴坐标
     * @param y                  y 轴坐标
     * @param yaw                弧度
     * @param onNavigateListener 底盘运动状态的回调
     */
    public synchronized void moveTo(double x, double y, double yaw, OnNavigateListener onNavigateListener) {
        LogUtils.d(TAG, "movTo:" + "double:" + x + "--double:" + y + "--double:" + yaw);
        try {
            manager.moveTo(x, y, yaw, onNavigateListener);
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }


    /**
     * 控制机器前后左右移动
     *
     * @param moveDirection 枚举类型 FORWARD,BACKWARD,TURN_RIGHT,TURN_LEFT;
     */
    public synchronized void moveBy(MoveDirection moveDirection) {
        moveBy(moveDirection, null);
    }

    /**
     * 控制机器前后左右移动
     *
     * @param moveDirection      枚举类型 FORWARD,BACKWARD,TURN_RIGHT,TURN_LEFT;
     * @param onNavigateListener 底盘运动状态的回调
     */
    public synchronized void moveBy(MoveDirection moveDirection, OnNavigateListener onNavigateListener) {
        try {
            if (null != moveDirection) {
                LogUtils.d(TAG, "moveBy:" + moveDirection);
                cancelGoHome();
                manager.moveBy(moveDirection, onNavigateListener);
            }
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }


    /**
     * 旋转到指定角度
     *
     * @param angle 传 0°~360°范围的角度
     */
    public synchronized void rotateTo(float angle) {
        rotateTo(angle, null);
    }

    /**
     * 旋转到指定角度
     *
     * @param angle              传 0°~360°范围的角度
     * @param onNavigateListener 底盘运动状态的回调
     */
    public synchronized void rotateTo(float angle, OnNavigateListener onNavigateListener) {
        try {
            manager.rotateTo(RadianUtil.toRadians(angle), onNavigateListener);
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }


    /**
     * 设置底盘运行的线速度
     *
     * @param speedValue 速度值
     */
    public synchronized void setSpeed(String speedValue) {
        try {
            if (!TextUtils.isEmpty(speedValue)) {
                manager.setSpeed(speedValue);
            }
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }

    /**
     * 设置底盘旋转的角速度
     *
     * @param rotateSpeedValue 速度值
     */
    public synchronized void setRotateSpeed(String rotateSpeedValue) {
        try {
            if (!TextUtils.isEmpty(rotateSpeedValue)) {
                manager.setRotateSpeed(rotateSpeedValue);
            }
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }


    /**
     * 底盘重定位，指定坐标和角度信息
     *
     * @param x   x轴坐标
     * @param y   x轴坐标
     * @param yaw 角度
     */
    public synchronized void setPose(double x, double y, double yaw) {
        try {
            manager.setPose(x, y, yaw);
        } catch (Exception e) {
            onErrorInfo(e);
        }
    }


    /**
     * @return 返回电量百分比数值
     */
    public int getBatteryPercentage() {
        return manager.getBatteryPercentage();
    }


    /**
     * @return 底盘制动状态 0: 松开状态;1: 急停按下; 2: 刹车按下; 3: 刹车急停同时按下
     */
    public int getButtonStatus() {
        return manager.getButtonStatus();
    }


    /**
     * @return 底盘当前位置信息
     */
    public LocalPose getPose() {
        LocalPose pose = null;
        try {
            Pose temp = manager.getPose();
            if (temp != null) {
                Location location = new Location();
                location.setX((float) temp.position.x);
                location.setY((float) temp.position.y);
                Rotation rotation = new Rotation();
                rotation.setYaw((float) quaternion2Yaw(temp.orientation));
                pose = new LocalPose(location, rotation);
            }
        } catch (Exception e) {
            onErrorInfo(e);

        }
        return pose;
    }

    public double quaternion2Yaw(Quaternion quaternion) {
        double siny_cosp = 2 * (quaternion.w * quaternion.z + quaternion.x * quaternion.y);
        double cosy_cosp = 1 - 2 * (quaternion.y * quaternion.y + quaternion.z * quaternion.z);
        double yaw = Math.atan2(siny_cosp, cosy_cosp);
        return yaw;
    }

    /**
     * @return 电充状态 0 未知;1 充电中;2 放电中;3 没有充电;4 电池电量已满
     */
    public int getPowerSupplyStatus() {
        return manager.getPowerSupplyStatus();
    }

    /**
     * @return 获取底盘的导航状态信息
     * 0：任务尚未被处理
     * 1：任务正在被处理
     * 2：任务执行后收到取消请求
     * 3：导航任务成功，目标点已到达
     * 4：由于某些故障，任务执行过程中中止了目标
     * 5：任务在未处理的情况下被服务器拒绝
     * 6：目标在尚未完成执行动作时收到了取消请求
     * 7：目标在开始执行前收到了取消请求，但是动作服务器还没有确认目标被取消
     * 8：目标在开始执行前收到了取消请求，并成功取消
     * 9: 已经确定目标丢失
     */
    public int getStatus() {
        return manager.getStatus();
    }


    /**
     * @return 地图原始数据
     */
    public synchronized OccupancyGrid getMapData() {
        return manager.getMapData();
    }

    /**
     * 底盘系统控制:开机重启，开启导航，开启建图
     * requestCmd.system_cmd 与 requestCmd.os_status 当其中一个赋了有效值之后另一个需要赋 值-1.
     * 示例：
     * OSRequestCmd requestCmd = new OSRequestCmd();
     * requestCmd.os_status = 2|8|16;
     * requestCmd.system_cmd= -1;
     * settingOs(requestCmd)
     *
     * @param requestCmd 控制对象
     * @return 结果
     */
    public boolean settingOs(OSRequestCmd requestCmd) {
        try {
            return manager.settingOs(requestCmd);
        } catch (Exception e) {
            onErrorInfo(e);
        }
        return false;
    }


    /**
     * 重启-指底盘
     */
    public void settingOsReBoot() {
        OSRequestCmd requestCmd = new OSRequestCmd();
        requestCmd.system_cmd = 2;
        requestCmd.os_status = -1;
        settingOs(requestCmd);
    }


    /**
     * 关机-指底盘
     */
    public void settingOsShutDown() {
        OSRequestCmd requestCmd = new OSRequestCmd();
        requestCmd.system_cmd = 1;
        requestCmd.os_status = -1;
        settingOs(requestCmd);
    }

    /**
     * 开启导航
     */
    public void settingOsNavigate() {
        OSRequestCmd requestCmd = new OSRequestCmd();
        requestCmd.system_cmd = -1;
        requestCmd.os_status = 2 | 8 | 16;
        settingOs(requestCmd);
    }

    /**
     * 开启建图
     */
    public void settingOsBuildMap() {
        OSRequestCmd requestCmd = new OSRequestCmd();
        requestCmd.system_cmd = -1;
        requestCmd.os_status = 4;
        settingOs(requestCmd);
    }

    /**
     * 地图数据获取的订阅服务开启
     */
    public void subscribeGetMapDataTopic() {
        manager.subscribeGetMapDataTopic();
    }

    /**
     * 地图数据获取的订阅服务关闭
     */
    public void unsubscribeGetMapDataTopic() {
        manager.unsubscribeGetMapDataTopic();
    }


    /**
     * 错误信息处理
     *
     * @param e 错误信息
     */
    public void onErrorInfo(Exception e) {
        LogUtils.d(TAG, "onErrorInfo:" + e.getMessage());
        // TODO
    }


    /**
     * 取消回充电桩
     */
    public synchronized void cancelGoHome() {
        try {
            manager.cancelGoHome();
        } catch (Exception e) {
            onErrorInfo(e);
        }

    }

    /**
     * 底盘连接状态监听
     */
    public ROSClient.ConnectionStatusListener connectionStatusListener = new ROSClient.ConnectionStatusListener() {
        @Override
        public void onConnect() {
            LogUtils.d(TAG, "onConnect:连接成功");
        }

        @Override
        public void onDisconnect(boolean normal, String reason, int code) {
            LogUtils.d(TAG, "连接断开：" + normal + ",reason:" + reason + ",code" + code);
            switch (code) {
                case -1://连接失败
                    //TODO
                    break;
                case 1006://连接断开
                    EventBus.getDefault().post(new RosDisconnectEvent());
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onError(Exception ex) {
            LogUtils.d(TAG, "底盘错误信息：" + ex.getMessage());
        }
    };


    /**
     * 关闭连接
     */
    public void closeConnect() {
        if (null != connectionStatusListener) {
            connectionStatusListener = null;
        }
        // TODO
    }


}
