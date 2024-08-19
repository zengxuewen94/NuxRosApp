package com.nudtit.lib_common.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.nudtit.lib_common.R;
import com.nudtit.lib_common.base.BaseNewDialog;

/**
 * @author :  zengxuewen
 * @date :  2021/7/5
 * @desc :  等待(加载)对话框
 */
public final class WaitDialog {

    public static final class Builder
            extends BaseNewDialog.Builder<Builder> {

        private final TextView mMessageView;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.common_wait_dialog);
            setAnimStyle(BaseNewDialog.ANIM_TOAST);
            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_wait_message);
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }
    }
}