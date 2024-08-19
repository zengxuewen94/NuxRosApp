package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.control_common.base.BaseViewModel;
import com.nudtit.lib_common.manager.ThreadPoolManager;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.R;
import com.nudtit.module_main.manager.RosDeviceManager;

import org.jetbrains.annotations.NotNull;

/**
 * @author :  zengxuewen
 * @date :  2022/8/18
 * @desc :  运动控制
 */
public class RosSlamControlViewModel extends BaseViewModel {
    public RosSlamControlViewModel(@NonNull @NotNull Application application) {
        super(application);


    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        initTitle(Utils.getString(R.string.slam_control_title), R.color.white, v -> {
            if (R.id.iv_view_setting_back == v.getId()) {
                activityFinishVo.postValue(true);
            }
        });
    }


    public void onClick(View view) {
        int id = view.getId();
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                if (R.id.bt_slam_control_goCharge == id) {// 回充电桩
                    RosDeviceManager.INSTANCE.goHome(null);
                } else if (R.id.bt_slam_control_cancelAction == id) {// 取消所有动作
                    RosDeviceManager.INSTANCE.actionCancel();
                } else if (R.id.bt_slam_control_forward == id) {// 向前
                    RosDeviceManager.INSTANCE.moveBy(MoveDirection.FORWARD);
                } else if (R.id.bt_slam_control_back == id) {// 向后
                    RosDeviceManager.INSTANCE.moveBy(MoveDirection.BACKWARD);
                } else if (R.id.bt_slam_control_left == id) {//左转
                    RosDeviceManager.INSTANCE.moveBy(MoveDirection.TURN_LEFT);
                } else if (R.id.bt_slam_control_right == id) {//右转
                    RosDeviceManager.INSTANCE.moveBy(MoveDirection.TURN_RIGHT);
                }
            }
        });
    }


}
