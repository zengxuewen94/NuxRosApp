package com.eaibot.running.view.dialog;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * @author :  zengxuewen
 * @date :  2022/8/29
 * @desc :  自定义BottomSheetDialog
 * <p>
 *  自定义的原因：BottomSheetDialog 在AndroidX中显示不全，阅读源码发现onStart中设置了状态为BottomSheetBehavior.STATE_COLLAPSED：折叠状态
 *  因此重写onStart 设置状态为BottomSheetBehavior.STATE_EXPANDED 展开状态
 * </p>
 */
public class CustomBottomSheetDialog extends BottomSheetDialog {
    public CustomBottomSheetDialog(Context context) {
        super(context);
    }

    public CustomBottomSheetDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CustomBottomSheetDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setCanceledOnTouchOutside(true);
        BottomSheetBehavior behavior = getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
}
