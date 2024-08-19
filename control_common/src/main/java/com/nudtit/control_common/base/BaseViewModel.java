package com.nudtit.control_common.base;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.nudtit.control_common.thread.CustomThreadFactory;
import com.nudtit.control_common.widget.TitleViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  ViewModel 基类
 */
public abstract class BaseViewModel extends AndroidViewModel implements LifecycleObserver {
    public MutableLiveData<Boolean> activityFinishVo = new MutableLiveData<>();

    public MutableLiveData<Boolean> getActivityFinishVo() {
        return activityFinishVo;
    }

    // 头部导航
    public TitleViewModel titleViewModel;


    public BaseViewModel(@NonNull @NotNull Application application) {
        super(application);
    }




    /**
     * @param title             标题
     * @param color             标题颜色
     * @param leftBackgroundRes 左侧按钮背景
     * @param onClickListener   点击是事件
     */
    protected void initTitle(String title, int color, int leftBackgroundRes, View.OnClickListener onClickListener) {
        titleViewModel = new TitleViewModel(title, color, leftBackgroundRes, onClickListener);
    }


    /**
     * @param title           标题
     * @param color           标题颜色
     * @param onClickListener 点击事件
     */
    protected void initTitle(String title, int color, View.OnClickListener onClickListener) {
        titleViewModel = new TitleViewModel(title, color, onClickListener);
    }

    protected void setTitleIvBg(int rightBackgroundRes) {
        if (null != titleViewModel) {
            titleViewModel.setRightIvBg(rightBackgroundRes);
        }
    }

    protected void setTitleBg(int rightBackgroundRes) {
        if (null != titleViewModel) {
            titleViewModel.setViewBg(rightBackgroundRes);
        }

    }
}
