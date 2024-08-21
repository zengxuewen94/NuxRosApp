package com.eaibot.running.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.SystemClock;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.nudtit.lib_common.utils.DisplayUtils;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;

import java.util.Locale;

/**
 * @author Yist
 */
public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    protected int windowX = 0;
    protected int windowY = 0;
    /** 状态栏沉浸 */
    protected ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepScreenLight();
        getMatrixSize();
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

        }
    }


    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig(int navigationBarColor) {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
                // 指定导航栏背景颜色
                .navigationBarColor(navigationBarColor)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }


    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return createStatusBarConfig(android.R.color.white);
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return false;
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }



    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private void getMatrixSize() {
        windowX = DisplayUtils.getScreenWidthPixels(this);
        windowY = DisplayUtils.getScreenHeightPixels(this);

    }

    private String mActivityJumpTag;
    private long mActivityJumpTime;

    protected boolean startActivitySelfCheck(Intent intent) {
        boolean result = true;
        String tag;
        if (intent.getComponent() != null) {
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) {
            tag = intent.getAction();
        } else {
            return result;
        }

        if (tag.equals(mActivityJumpTag) && mActivityJumpTime >= SystemClock.uptimeMillis() - 1500) {
            result = false;
        }

        mActivityJumpTag = tag;
        mActivityJumpTime = SystemClock.uptimeMillis();
        Log.e(TAG, "startActivitySelfCheck: " + (result ? "正常跳转,通过跳转请求" : "重复跳转,驳回跳转请求"));
        return result;
    }


    /**
     * 保持屏幕常量
     */
    protected void keepScreenLight() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected  void  showShortToast(CharSequence message){
        if (Build.MODEL.contains("Lenovo")){
            Toaster.showShort(message);
        }else {
            ToastUtil.showShort(message);
        }
    }

    protected  void  showLongToast(CharSequence message){
        if (Build.MODEL.contains("Lenovo")){
            Toaster.showLong(message);
        }else {
            ToastUtil.showLong(message);
        }
    }
}