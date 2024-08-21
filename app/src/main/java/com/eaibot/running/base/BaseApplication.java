package com.eaibot.running.base;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;

import com.eaibot.running.manger.ActivityManager;
import com.eaibot.running.service.MainService;
import com.hjq.toast.Toaster;
import com.nudtit.lib_common.base.ModuleBaseApplication;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.Utils;
import com.tencent.bugly.crashreport.CrashReport;

import me.jessyan.autosize.AutoSize;

/**
 * @author :  zengxuewen
 * @date :  2022/9/1
 * @desc :  application
 */
public class BaseApplication extends Application {

    protected static BaseApplication instance;

    public static BaseApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AutoSize.initCompatMultiProcess(this);
        //initCrashReport();
        LogUtils.d("tttt", "onc");
        Utils.init(this);

        // 初始化 Toast 框架
        Toaster.init(this);
        Toaster.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
        ActivityManager.getInstance().init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtils.d("tttt", "x销毁了");
    }

    /**
     * 拦截所有异常错误奔溃信息，跳转错误信息Activity,并上传错误日志
     */
    private void initCrashReport() {
        new Handler(getMainLooper()).post(() -> {
            while (true) {
                try {
                    // try-catch主线程的所有异常；Looper.loop()内部是一个死循环，出现异常时才会退出，所以这里使用while(true)
                    Looper.loop();
                } catch (Throwable e) {
                    Log.e("Error", "initCrashReport: " + e.getMessage());
                    // 上传错误日志
                    CrashReport.postCatchedException(e);

                }
            }
        });
        // try-catch子线程的所有异常
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Log.e("Error", "initCrashReport: " + e.getMessage());
            // 上传错误日志
            CrashReport.postCatchedException(e);

        });
    }
}
