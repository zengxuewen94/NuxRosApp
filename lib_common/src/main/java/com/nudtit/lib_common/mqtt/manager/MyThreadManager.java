package com.nudtit.lib_common.mqtt.manager;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @desc :  线程池管理工具类
 */
public class MyThreadManager extends ThreadPoolExecutor {
    /**
     * 单列
     */
    static int cpuNum;
    static int maximumPoolSize;
    private static volatile MyThreadManager sInstance;

    public MyThreadManager() {
        // 核心线程为芯片cpu数量，最大线程数量为核心线程*2+1，
        super(cpuNum, maximumPoolSize,
                30L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20), new AbortPolicy() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                        super.rejectedExecution(r, e);
                    }
                });
    }

    public static MyThreadManager getInstance() {
        if (sInstance == null) {
            cpuNum = Runtime.getRuntime().availableProcessors();
            maximumPoolSize = cpuNum * 2 + 1;
            synchronized (MyThreadManager.class) {
                if (sInstance == null) {
                    sInstance = new MyThreadManager();
                }
            }
        }
        return sInstance;
    }
}
