package com.nudtit.module_main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.nudtit.lib_common.base.ModuleBaseActivity;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;


/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  启动页
 */
public class SplashActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        onStartActivity(OfflineLoginActivity.class);
        finish();
    }
}
