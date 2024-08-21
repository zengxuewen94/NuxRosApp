package com.eaibot.running.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.event.GoHomeEvent;
import com.eaibot.running.event.MoveToEvent;
import com.eaibot.running.event.NavigateResultEvent;
import com.eaibot.running.event.RobotInfoEvent;
import com.eaibot.running.event.RosConnectStatusEvent;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.manger.ThreadPoolManager;
import com.nudtit.androidrosbridgeclient.interfaces.OnNavigateListener;

import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.slam.bean.LocalPose;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author :  zengxuewen
 * @date :  2022/9/2
 * @desc :  service
 */
public class RosService extends Service {
    // 底盘连接状态
    private boolean isConnect = true;
    // 是否是充电
    private boolean isCharge = false;

    @Override
    public void onCreate() {
        super.onCreate();
        getRobotInfo();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    /**
     * 底盘连接状态
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void connectStatus(RosConnectStatusEvent event) {
        isConnect = event.isConnect();
    }


    /**
     * 移动导航
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void moveTo(MoveToEvent event) {
        isCharge = false;
        SlamLocationBean mSlamLocationBean = event.getSlamLocationBean();
        RosDeviceManager.INSTANCE.moveTo(mSlamLocationBean.getX(), mSlamLocationBean.getY(), mSlamLocationBean.getYaw(), onNavigateListener);
    }

    /**
     * 回充
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goHome(GoHomeEvent event) {
        isCharge = true;
        RosDeviceManager.INSTANCE.goHome(onNavigateListener);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取机器人信息
     */
    private void getRobotInfo() {

        ThreadPoolManager.getInstance().execute(() -> {
            int timeSpan = 0;
            while (isConnect) {
                try {

                    if (timeSpan % 5 == 0) {
                        EventBus.getDefault().post(new RobotInfoEvent(RosDeviceManager.INSTANCE.getBatteryPercentage(),
                                RosDeviceManager.INSTANCE.getPowerSupplyStatus(),
                                RosDeviceManager.INSTANCE.getButtonStatus()));
                    }
                    if (timeSpan % 3 == 0) {
                        LocalPose localPose = RosDeviceManager.INSTANCE.getPose();
                        if (localPose != null) {
                            EventBus.getDefault().post(localPose);
                        }
                    }
                    Thread.sleep(100);
                    timeSpan++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 导航事件监听
     */
    private final OnNavigateListener onNavigateListener = new OnNavigateListener() {
        @Override
        public void waitingForStart() {

        }

        @Override
        public void running() {
            LogUtils.d("RosService", "running()");
        }

        @Override
        public void finished() {
            LogUtils.d("RosService", "finished()");
            onNavigateResult(true);
        }

        @Override
        public void paused() {
            LogUtils.d("RosService", "paused()");
        }

        @Override
        public void stopped() {
            LogUtils.d("RosService", "stopped()");
        }

        @Override
        public void error() {
            onNavigateResult(false);
        }
    };


    /**
     * 导航结果
     *
     * @param success
     */
    private void onNavigateResult(boolean success) {
        // 充电时，机器到达设置的虚拟充点电就回调finished()方法，因此需要判断机器是否真的在充电中
        if (isCharge && success) {

            ThreadPoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (RosDeviceManager.INSTANCE.getPowerSupplyStatus() == 1) {
                            EventBus.getDefault().post(new NavigateResultEvent(true, true));
                            break;
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            EventBus.getDefault().post(new NavigateResultEvent(success, false));
        }


    }
}
