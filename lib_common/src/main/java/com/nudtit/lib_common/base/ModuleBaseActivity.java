package com.nudtit.lib_common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.nudtit.lib_common.base.action.KeyboardAction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class ModuleBaseActivity extends RxAppCompatActivity implements KeyboardAction {
    protected Context mContext;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activity = this;
    }

    @Override
    protected void onResume() {

        super.onResume();
    }








}
