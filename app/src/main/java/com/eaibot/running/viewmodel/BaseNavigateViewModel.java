package com.eaibot.running.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.eaibot.running.base.BaseViewModel;
import com.eaibot.running.event.NavigateResultEvent;
import com.eaibot.running.manger.RosDeviceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author :  zengxuewen
 * @date :  2023/1/31
 * @desc :  导航基类 ViewModel
 */
public abstract class BaseNavigateViewModel extends BaseViewModel {
    public BaseNavigateViewModel(@NonNull  Application application) {
        super(application);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onCleared() {
        EventBus.getDefault().unregister(this);
        super.onCleared();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void  onPause(){
        //应用置于后台，停止导航
        RosDeviceManager.INSTANCE.actionCancel();

    }


    /**
     * 导航结果
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNavigateEvent(NavigateResultEvent event) {
        if (event.isSuccess()) {
            onNavigateSuccess();
        } else {
            onNavigateError();
        }
    }

    /**
     * 导航失败
     */
    protected abstract void onNavigateError();

    /**
     * 导航成功
     */
    protected abstract void onNavigateSuccess();


    //判断是否是快速点击
    private static long lastClickTime = 0L; //上一次点击的时间
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if(timeD<800L){
            //写自己的双击逻辑
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
