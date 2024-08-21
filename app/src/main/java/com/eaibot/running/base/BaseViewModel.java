package com.eaibot.running.base;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;

import com.hjq.toast.Toaster;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;

/**
 * @author :  zengxuewen
 * @date :  2022/9/7
 * @desc :  ViewModel 基类
 */
public class BaseViewModel extends AndroidViewModel implements LifecycleObserver {
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(Utils.getContext(), resId);
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
