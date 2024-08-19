package com.nudtit.lib_common.utils;

import android.util.Log;

import com.nudtit.lib_common.BuildConfig;


/**
 * @author song
 * @date 2020/6/23
 */

public class LogUtils {

    private static final boolean DEBUG = BuildConfig.LOG_DEBUG;

    private LogUtils() {
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.i(tag, msg, throwable);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            LogUtils.d(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }
}
