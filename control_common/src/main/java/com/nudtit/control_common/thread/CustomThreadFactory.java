package com.nudtit.control_common.thread;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 * @author Wzz
 */
public class CustomThreadFactory implements ThreadFactory {
    private int counter;
    private String name;
    private List<String> stats;

    public CustomThreadFactory(String name) {
        counter = 0;
        this.name = name;
        stats = new ArrayList<>();
    }


    @SuppressLint("DefaultLocale")
    @Override
    public Thread newThread(@NonNull Runnable r) {
        Thread t = new Thread(r, name + "-Thread-" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on%s\n", t.getId(), t.getName(), new Date()));
        return t;
    }
}
