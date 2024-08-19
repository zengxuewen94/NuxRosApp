package com.nudtit.lib_common.manager;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author :  zengxuewen
 * @date   :  2021/6/16
 * @desc   :  线程池管理工具类
 */
public class ThreadPoolManager extends ThreadPoolExecutor {
    /**
     * 单列
     */
    private static volatile ThreadPoolManager sInstance;

    public ThreadPoolManager() {
        super(0, Integer.MAX_VALUE,
                30L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    public static ThreadPoolManager getInstance() {
        if (sInstance == null) {
            synchronized (ActivityManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolManager();
                }
            }
        }
        return sInstance;
    }
}
