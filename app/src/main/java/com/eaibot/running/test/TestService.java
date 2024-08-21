package com.eaibot.running.test;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Yist
 * @date 2019/2/26
 */
public class TestService extends Service{

//    public TestService(){
//
//    }
//
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TestService","onCreate");
    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e("TestService","onStartCommand");
//        HttpServiceTool.navigationImuStart("192.168.31.200", handler, 0);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    connectROS();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    //连接ROS服务端
//    private TimerTask connTask;
//    private Timer connTimer;
//    private RosService rosService;
//    private int connectRosFailedTimes = 0;
//    private void connectROS() {
//        connTimer = new Timer();
//        connTask = new TimerTask() {
//            @Override
//            public void run() {
//                new RosClient(TestService.this, "192.168.31.200", new OnTaskListener() {
//                    @Override
//                    public void onSuccess() {
//                        if (connTimer != null) {
//                            connTimer.cancel();
//                            connTimer = null;
//                        }
//                        if (connTask != null) {
//                            connTask.cancel();
//                            connTask = null;
//                        }
//                        Log.e("TestService", "onSuccess: ");
//                        rosService = RosService.getRosServiceInstance(TestService.this);
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        connectRosFailedTimes++;
//                        if (connectRosFailedTimes >= 2) {
//                            connectRosFailedTimes = 0;
//                            if (connTimer != null) {
//                                connTimer.cancel();
//                                connTimer = null;
//                            }
//                            if (connTask != null) {
//                                connTask.cancel();
//                                connTask = null;
//                            }
//                        }
//                    }
//                });
//            }
//        };
//        connTimer.schedule(connTask, 2000, 2000);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("TestService","onDestroy");
//    }
}
