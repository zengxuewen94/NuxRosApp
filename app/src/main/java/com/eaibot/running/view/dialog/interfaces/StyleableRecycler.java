package com.eaibot.running.view.dialog.interfaces;

import android.app.Dialog;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.eaibot.running.view.dialog.config.ConfigBeanRecycler;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface StyleableRecycler {

    ConfigBeanRecycler setBtnColor(@ColorRes int btn1Color, @ColorRes int btn2Color, @ColorRes int btn3Color);

    ConfigBeanRecycler setListItemColor(@ColorRes int lvItemTxtColor, Map<Integer, Integer> colorOfPosition);

    ConfigBeanRecycler setTitleColor(@ColorRes int colorRes);
    ConfigBeanRecycler setMsgColor(@ColorRes int colorRes);
    ConfigBeanRecycler seInputColor(@ColorRes int colorRes);



    ConfigBeanRecycler setTitleSize(int sizeInSp);
    ConfigBeanRecycler setMsgSize(int sizeInSp);
    ConfigBeanRecycler setBtnSize(int sizeInSp);
    ConfigBeanRecycler setLvItemSize(int sizeInSp);
    ConfigBeanRecycler setInputSize(int sizeInSp);

    Dialog show();

    //内容设置
    ConfigBeanRecycler setBtnText(CharSequence btn1Text, @Nullable CharSequence btn2Text, @Nullable CharSequence btn3Text);

    ConfigBeanRecycler setBtnText(CharSequence positiveTxt, @Nullable CharSequence negtiveText);

    ConfigBeanRecycler setListener(MyDialogListener listener);



    ConfigBeanRecycler setCancelable(boolean cancelable, boolean outsideCancelable);







}
