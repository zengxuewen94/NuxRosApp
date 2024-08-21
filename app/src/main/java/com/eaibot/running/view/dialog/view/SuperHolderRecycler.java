package com.eaibot.running.view.dialog.view;

import android.content.Context;


import android.view.View;

import androidx.annotation.LayoutRes;

import com.eaibot.running.view.dialog.config.ConfigBean;
import com.eaibot.running.view.dialog.config.ConfigBeanRecycler;


/**
 * Created by Administrator on 2016/4/15 0015.
 */
public abstract class SuperHolderRecycler {
    public View rootView;

    public SuperHolderRecycler(Context context){
        rootView = View.inflate(context, setLayoutRes(), null);
        findViews();

    }

    protected abstract void findViews();




    protected abstract  @LayoutRes
    int setLayoutRes();

    /**
     * 一般情况下，实现这个方法就足够了
     * @param context
     * @param bean
     */
    public  abstract void assingDatasAndEvents(Context context, ConfigBeanRecycler bean);


}
