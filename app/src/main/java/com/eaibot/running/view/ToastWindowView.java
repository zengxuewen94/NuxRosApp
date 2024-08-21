package com.eaibot.running.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.eaibot.running.R;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author :  zengxuewen
 * @date :  2023/4/11
 * @desc :  TODO
 */
public class ToastWindowView {
    protected Context mContext;
    protected WindowManager mWindowManager;
    private TextView textView;
    private View mView;
    private WindowManager.LayoutParams params;
    private AlphaAnimation disappearAnimation;
    private LinearLayout layout;
    public ToastWindowView(Context context) {

        mView=LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        // 获取WindowManager
        layout=mView.findViewById(R.id.toast_custom_layout);
        textView=mView.findViewById(R.id.toast_custom_tv);
        mWindowManager= (WindowManager) context.getSystemService(WINDOW_SERVICE);

        // 创建布局参数

        params = new WindowManager.LayoutParams();

        /** 设置参数 */

        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?

                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :

                WindowManager.LayoutParams.TYPE_PHONE;

        params.format = PixelFormat.RGBA_8888;

        // 设置窗口的行为准则

        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置透明度

        params.alpha = 1.0f;

        //设置内部视图对齐方式，这边位置为左边靠上

        params.gravity = Gravity.CENTER | Gravity.BOTTOM;

        //窗口的左上角坐标

        params.x = 0;

        params.y =100;

//设置窗口的宽高,这里为自动

        params.width = WindowManager.LayoutParams.WRAP_CONTENT;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

// 添加进WindowManager


    }

    public void removeView() {
        if (mWindowManager != null && mView != null) {
            mWindowManager.removeView(mView);
            mWindowManager = null;
        }
    }

    public void show(String text,int duration){
        if (null!=mWindowManager&&mView!=null&&params!=null){
            mWindowManager.addView(mView, params);
            if (textView!=null){
                textView.setText(text);
            }

        }
        disappearAnimation = new AlphaAnimation(1, 0);
        disappearAnimation.setDuration(duration);
        layout.startAnimation(disappearAnimation);
        disappearAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override

            public void onAnimationStart(Animation animation) {}

            @Override

            public void onAnimationRepeat(Animation animation) {}

            @Override

            public void onAnimationEnd(Animation animation) {

               removeView();

            }

        });

    }
}
