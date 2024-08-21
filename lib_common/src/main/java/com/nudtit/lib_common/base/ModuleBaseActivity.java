package com.nudtit.lib_common.base;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.nudtit.lib_common.base.action.KeyboardAction;



public abstract class ModuleBaseActivity extends AppCompatActivity implements KeyboardAction {
    protected Context mContext;
    protected Activity activity;

    protected abstract void initView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activity = this;
        initSome(savedInstanceState);

    }

    protected void initSome(Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        if (null!=actionBar){
            actionBar.hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        // 取消保持屏幕常亮
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mContext = null;
        activity = null;
        super.onDestroy();
    }

}
