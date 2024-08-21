package com.eaibot.running.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.eaibot.running.view.dialog.config.ConfigBean;
import com.eaibot.running.view.dialog.config.ConfigBeanRecycler;
import com.eaibot.running.view.dialog.config.DefaultConfig;
import com.eaibot.running.view.dialog.interfaces.Assignable;
import com.eaibot.running.view.dialog.interfaces.AssignableRecycler;
import com.eaibot.running.view.dialog.interfaces.MyDialogListener;
import com.eaibot.running.view.dialog.interfaces.MyItemDialogListener;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class DialogAssignerRecycler implements AssignableRecycler {

    private static DialogAssignerRecycler instance;

    private DialogAssignerRecycler(){

    }

    public static DialogAssignerRecycler getInstance(){
        if (instance == null){
            instance = new DialogAssignerRecycler();
        }
        return instance;
    }


    @Override
    public ConfigBeanRecycler assignMdLoading(Context context, CharSequence msg, boolean cancelable, boolean outsideTouchable) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.msg = msg;
        bean.type = DefaultConfig.TYPE_MD_LOADING;
        bean.cancelable = cancelable;
        bean.outsideTouchable = outsideTouchable;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignMdAlert(Activity activity, CharSequence title, CharSequence msg, MyDialogListener listener) {

        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = activity;
        bean.msg = msg;
        bean.title = title;
        bean.listener = listener;
        bean.type = DefaultConfig.TYPE_MD_ALERT;
        bean.btn1Color = DefaultConfig.mdBtnColor;
        bean.btn2Color = DefaultConfig.mdBtnColor;
        bean.btn3Color = DefaultConfig.mdBtnColor;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignMdSingleChoose(Activity context, CharSequence title, int defaultChosen, CharSequence[] words, MyItemDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.title = title;
        bean.itemListener = listener;
        bean.wordsMd = words;
        bean.type = DefaultConfig.TYPE_MD_SINGLE_CHOOSE;
        bean.defaultChosen = defaultChosen;

        bean.btn1Color = DefaultConfig.mdBtnColor;
        bean.btn2Color = DefaultConfig.mdBtnColor;
        bean.btn3Color = DefaultConfig.mdBtnColor;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignMdMultiChoose(Activity context, CharSequence title, CharSequence[] words, boolean[] checkedItems, MyDialogListener btnListener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.msg = title;
        bean.title = title;
        bean.listener = btnListener;
        bean.wordsMd = words;
        bean.checkedItems = checkedItems;
        bean.type = DefaultConfig.TYPE_MD_MULTI_CHOOSE;

        bean.btn1Color = DefaultConfig.mdBtnColor;
        bean.btn2Color = DefaultConfig.mdBtnColor;
        bean.btn3Color = DefaultConfig.mdBtnColor;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignIosAlert(Context activity, CharSequence title, CharSequence msg, MyDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = activity;
        bean.msg = msg;
        bean.title = title;
        bean.listener = listener;
        bean.type = DefaultConfig.TYPE_IOS_HORIZONTAL;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignIosAlertVertical(Context activity, CharSequence title, CharSequence msg, MyDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = activity;
        bean.msg = msg;
        bean.title = title;
        bean.listener = listener;
        bean.type = DefaultConfig.TYPE_IOS_VERTICAL;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignIosSingleChoose(Context context, List<? extends CharSequence> words, MyItemDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.itemListener = listener;
        bean.wordsIos = words;
        bean.type = DefaultConfig.TYPE_IOS_CENTER_LIST;

        return bean;
    }

    @Override
    public ConfigBeanRecycler assignBottomItemDialog(Context context, List<? extends CharSequence> words, CharSequence bottomTxt, MyItemDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.itemListener = listener;
        bean.wordsIos = words;
        bean.type = DefaultConfig.TYPE_IOS_BOTTOM;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignNormalInput(Context context, CharSequence title, CharSequence hint1, CharSequence hint2,
                                        CharSequence firstTxt, CharSequence secondTxt, MyDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.listener = listener;
        bean.title = title;
        bean.hint1 = hint1;
        bean.hint2 = hint2;
        bean.text1 = firstTxt;
        bean.text2 = secondTxt;
        bean.type = DefaultConfig.TYPE_IOS_INPUT;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignCustom(Context context, View contentView, int gravity) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.customView = contentView;
        bean.gravity = gravity;
        bean.type = DefaultConfig.TYPE_CUSTOM_VIEW;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignCustomBottomSheet(Activity context, View contentView) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.customView = contentView;
        bean.type = DefaultConfig.TYPE_BOTTOM_SHEET_CUSTOM;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignLoading(Context context, CharSequence msg, boolean cancelable, boolean outsideTouchable) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.msg = msg;
        bean.type = DefaultConfig.TYPE_LOADING;

        bean.cancelable = cancelable;
        bean.outsideTouchable = outsideTouchable;

        return bean;
    }

    @Override
    public ConfigBeanRecycler assignBottomSheetLv(Context context, CharSequence title, List datas, CharSequence bottomTxt, MyItemDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.title = title;
        bean.lvDatas = datas;
        bean.bottomTxt = bottomTxt;
        bean.itemListener = listener;
        bean.type = DefaultConfig.TYPE_BOTTOM_SHEET_LIST;
        return bean;
    }

    @Override
    public ConfigBeanRecycler assignBottomSheetGv(Context context, CharSequence title, List datas, CharSequence bottomTxt, int columnsNum, MyItemDialogListener listener) {
        ConfigBeanRecycler bean = new ConfigBeanRecycler();
        bean.context = context;
        bean.title = title;
        bean.lvDatas = datas;
        bean.bottomTxt = bottomTxt;
        bean.itemListener = listener;
        bean.gridColumns = columnsNum;
        bean.type = DefaultConfig.TYPE_BOTTOM_SHEET_GRID;
        return bean;
    }


}
