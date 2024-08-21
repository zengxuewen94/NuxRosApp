package com.nudtit.lib_common.base;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nudtit.lib_common.BuildConfig;
import com.nudtit.lib_common.manager.ActivityManager;
import com.nudtit.lib_common.utils.GlideUtil;
import com.nudtit.lib_common.utils.Utils;
import com.tencent.bugly.crashreport.CrashReport;
//
//import me.jessyan.autosize.AutoSize;

/**
 * 要想使用ModuleBaseApplication，必须在组件中实现自己的Application，并且继承ModuleBaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 *
 * @author 2018/12/28
 * @version V1.0.0
 * @name ModuleBaseApplication
 */
public class ModuleBaseApplication extends Application {

    protected static ModuleBaseApplication instance;

    public static ModuleBaseApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        initSdk();
        //initCrashReport();

    }



    /**
     * 第三方sdk初始化
     */
    private void initSdk() {
        // Activity 栈管理初始化
        ActivityManager.getInstance().init(this);
        // 初始化阿里路由
        initARouter();
        GlideUtil.setGlideBuilder();
        //AutoSize.initCompatMultiProcess(this);
    }


    /**
     * 初始化阿里路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
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
                    // 上传错误日志
                    CrashReport.postCatchedException(e);
                    onError();
                }
            }
        });
        // try-catch子线程的所有异常
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            // 上传错误日志
            CrashReport.postCatchedException(e);
            onError();
        });
    }

    /**
     * 应用出错
     */
    protected void onError() {
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //释放路由资源（在onDestroy生命周期里面释放APP会报错崩溃）
        ARouter.getInstance().destroy();
        GlideUtil.clearMemory(this);
    }
}
