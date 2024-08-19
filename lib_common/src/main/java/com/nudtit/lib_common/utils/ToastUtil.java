package com.nudtit.lib_common.utils;

import android.app.ActionBar;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nudtit.lib_common.R;
import com.nudtit.lib_common.base.ModuleBaseApplication;


/**
 * author: wuht
 * date:   2020/9/23
 * Toast统一管理类
 */
public class ToastUtil {

    private static Toast toast;
    private static Toast toastImg;
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast(message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        initToast(Utils.getString(strResId), Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(Utils.getString(strResId), Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        initToast(message, duration);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param strResId
     * @param duration
     */
    public static void show(int strResId, int duration) {
        initToast(Utils.getString(strResId), duration);
    }


    private static void initToast(CharSequence message, int duration) {
        if (toast == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {//判断当前是否在UI主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        toast = Toast.makeText(ModuleBaseApplication.getApplication(), message, duration);
                        toast.setGravity(Gravity.TOP, 0, getYoffest());
                        toast.show();
                    }
                });
            } else {
                toast = Toast.makeText(ModuleBaseApplication.getApplication(), message, duration);
                toast.setGravity(Gravity.TOP, 0, getYoffest());
                toast.show();
            }
        } else {
            if (Looper.myLooper() != Looper.getMainLooper()) {//判断当前是否在UI主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                        toast = Toast.makeText(ModuleBaseApplication.getApplication(), message, duration);
                        toast.setGravity(Gravity.TOP, 0, getYoffest());
                        toast.show();
                    }
                });
            } else {
                toast.cancel();
                toast = Toast.makeText(ModuleBaseApplication.getApplication(), message, duration);
                toast.setGravity(Gravity.TOP, 0, getYoffest());
                toast.show();
            }
        }
    }

    /**
     * 显示有image的toast
     *
     * @param message
     * @return
     */
    public static void showToast(CharSequence message, int duration) {
        View view = LayoutInflater.from(ModuleBaseApplication.getApplication()).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(message);
        if (toast == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {//判断当前是否在UI主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        toast = new Toast(ModuleBaseApplication.getApplication());
                        toast.setGravity(Gravity.TOP, 0, getYoffest());
                        toast.setView(view);
                        toast.setDuration(duration);
                        toast.show();
                    }
                });
            } else {
                toast = new Toast(ModuleBaseApplication.getApplication());
                toast.setGravity(Gravity.TOP, 0, getYoffest());
                toast.setView(view);
                toast.setDuration(duration);
                toast.show();
            }
        } else {
            if (Looper.myLooper() != Looper.getMainLooper()) {//判断当前是否在UI主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                        toast = new Toast(ModuleBaseApplication.getApplication());
                        toast.setGravity(Gravity.TOP, 0, getYoffest());
                        toast.setView(view);
                        toast.setDuration(duration);
                        toast.show();
                    }
                });
            } else {
                toast.cancel();
                toast = new Toast(ModuleBaseApplication.getApplication());
                toast.setGravity(Gravity.TOP, 0, getYoffest());
                toast.setView(view);
                toast.setDuration(duration);
                toast.show();
            }
        }
    }

    private static int getYoffest() {
        return (int) ((DisplayUtils.getScreenHeightPixels(ModuleBaseApplication.getApplication()) / 5.4));

    }

}
