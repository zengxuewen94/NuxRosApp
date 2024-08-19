package com.nudtit.lib_common.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nudtit.lib_common.R;

import com.nudtit.lib_common.base.BaseNewDialog;

/**
 * @author :  zengxuewen
 * @date :  2021/6/17
 * @desc :  通用确认弹框
 */
public final class ConfirmDialog {
    public static final class Builder
            extends CommonDialog.Builder<Builder> {
        private TextView tvContent;
        private OnListener mListener;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.common_confirm_dialog);
            setCancelable(false);
            tvContent = findViewById(R.id.tv_common_confirm_dialog_content);
        }

        public Builder setContent(String content) {
            tvContent.setText(content);
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_ui_confirm) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onConfirm(getDialog());
                }
            } else if (view.getId() == R.id.tv_ui_cancel) {
                autoDismiss();
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseNewDialog dialog);

        /**
         * 点击取消时回调
         */
        void onCancel(BaseNewDialog dialog) ;
    }
}
