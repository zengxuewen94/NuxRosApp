package com.eaibot.running.view.dialog.interfaces;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.eaibot.running.view.dialog.config.ConfigBean;
import com.eaibot.running.view.dialog.config.ConfigBeanRecycler;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public interface AssignableRecycler {

    ConfigBeanRecycler assignMdLoading(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable);

    ConfigBeanRecycler assignMdAlert(Activity activity, CharSequence title, CharSequence msg, final MyDialogListener listener);

    ConfigBeanRecycler assignMdSingleChoose(Activity context, CharSequence title, final int defaultChosen, final CharSequence[] words,
                                    final MyItemDialogListener listener);

    ConfigBeanRecycler assignMdMultiChoose(Activity context, CharSequence title, final CharSequence[] words, final boolean[] checkedItems,
                                   final MyDialogListener btnListener);

    ConfigBeanRecycler assignIosAlert(Context activity, CharSequence title, CharSequence msg, final MyDialogListener listener);

    ConfigBeanRecycler assignIosAlertVertical(Context activity, CharSequence title, CharSequence msg, final MyDialogListener listener);

    ConfigBeanRecycler assignIosSingleChoose(Context context, List<? extends CharSequence> words, final MyItemDialogListener listener);

    ConfigBeanRecycler assignBottomItemDialog(Context context, List<? extends CharSequence> words, CharSequence bottomTxt, final MyItemDialogListener listener);


    ConfigBeanRecycler assignNormalInput(Context context, CharSequence title, CharSequence hint1, CharSequence hint2,
                                 CharSequence firstTxt, CharSequence secondTxt, final MyDialogListener listener);

    ConfigBeanRecycler assignCustom(Context context, View contentView, int gravity);

    ConfigBeanRecycler assignCustomBottomSheet(Activity context, View contentView);


    ConfigBeanRecycler assignLoading(Context context, CharSequence msg, boolean cancleable, boolean outsideTouchable);


    ConfigBeanRecycler assignBottomSheetLv(Context context, CharSequence title, List datas, CharSequence bottomTxt, MyItemDialogListener listener);

    ConfigBeanRecycler assignBottomSheetGv(Context context, CharSequence title, List datas, CharSequence bottomTxt, int columnsNum, MyItemDialogListener listener);





    
}
