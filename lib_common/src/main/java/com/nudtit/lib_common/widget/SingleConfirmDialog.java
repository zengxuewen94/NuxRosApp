package com.nudtit.lib_common.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nudtit.lib_common.R;
import com.nudtit.lib_common.base.BaseNewDialog;

/**
 * @author :  zengxuewen
 * @date :  2021/7/5
 * @desc :  通用单个按钮弹框
 */
public class SingleConfirmDialog {
    public static class Builder extends BaseNewDialog.Builder<Builder> {
        private TextView tvContent;
        private OnConfirmListener onConfirmListener;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.common_single_ui_dialog);
            setCanceledOnTouchOutside(false);
            findViewById(R.id.bt_common_single_ui_dialog_confirm).setOnClickListener(this);
            tvContent = findViewById(R.id.tv_common_single_ui_dialog_content);
        }

        public Builder setContent(String content) {
            tvContent.setText(content);
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener onConfirmListener) {
            this.onConfirmListener = onConfirmListener;
            return this;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.bt_common_single_ui_dialog_confirm) {
                if (null != onConfirmListener) {
                    onConfirmListener.onConfirm();
                }
                dismiss();
            }
        }
    }

    public interface OnConfirmListener {

        /**
         * 点击确定时回调
         */
        void onConfirm();

    }
}
