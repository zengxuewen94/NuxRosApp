package com.nudtit.module_main.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.MapEditListener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/18
 * @desc :  橡皮檫  dialog
 */
public final class EraserDialog {
    public static final class Builder extends BaseNewDialog.Builder<EraserDialog.Builder> {

        private String TAG = EraserDialog.class.getSimpleName();
        private MapEditListener mapEditListener;
        private ConstraintLayout clColor;
        private ConstraintLayout clSize;
        private TextView tvColor;
        private TextView tvSize;
        private TextView tvEraser;

        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.eraser_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM);
            setBackgroundDimEnabled(false);
            setCanceledOnTouchOutside(false);
            initView();
        }

        private void initView() {
            tvColor = findViewById(R.id.tv_eraser_dialog_color);
            tvColor.setOnClickListener(onClickListener);
            tvSize = findViewById(R.id.tv_eraser_dialog_size);
            tvSize.setOnClickListener(onClickListener);
            tvEraser = findViewById(R.id.tv_eraser_dialog_confirm);
            tvEraser.setOnClickListener(onClickListener);
            clColor = findViewById(R.id.cl_eraser_color);
            clColor.setOnClickListener(this);
            clSize = findViewById(R.id.cl_eraser_size);
            clSize.setOnClickListener(this);
            findViewById(R.id.iv_eraser_dialog_cancel).setOnClickListener(onClickListener);
            findViewById(R.id.iv_eraser_dialog_selected).setOnClickListener(onClickListener);

            RadioButton rbGray = findViewById(R.id.rb_eraser_color_gray);
            rbGray.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbWhite = findViewById(R.id.rb_eraser_color_white);
            rbWhite.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbBlack = findViewById(R.id.rb_eraser_color_black);
            rbBlack.setOnCheckedChangeListener(onCheckedChangeListener);

            RadioButton rbSizeXs = findViewById(R.id.rb_eraser_size_xs);
            rbSizeXs.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbSizeS = findViewById(R.id.rb_eraser_size_s);
            rbSizeS.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbSizeM = findViewById(R.id.rb_eraser_size_m);
            rbSizeM.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbSizeL = findViewById(R.id.rb_eraser_size_l);
            rbSizeL.setOnCheckedChangeListener(onCheckedChangeListener);
            RadioButton rbSizeXl = findViewById(R.id.rb_eraser_size_xl);
            rbSizeXl.setOnCheckedChangeListener(onCheckedChangeListener);
        }


        public EraserDialog.Builder setMapEditListener(MapEditListener mapEditListener) {
            this.mapEditListener = mapEditListener;
            return this;
        }


        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.iv_eraser_dialog_cancel) {//取消
                    dismiss();
                } else if (id == R.id.iv_eraser_dialog_selected) {//确定
                    dismiss();
                } else if (id == R.id.tv_eraser_dialog_color) {//颜色选择
                    clColor.setVisibility(View.VISIBLE);
                    clSize.setVisibility(View.GONE);
                    tvColor.setSelected(true);
                    tvSize.setSelected(false);
                } else if (id == R.id.tv_eraser_dialog_size) {//大小选择
                    clSize.setVisibility(View.VISIBLE);
                    clColor.setVisibility(View.GONE);
                    tvColor.setSelected(false);
                    tvSize.setSelected(true);
                }

            }
        };

        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                if (isChecked) {
                    if (R.id.rb_eraser_color_gray == id) {
                        ToastUtil.showShort("点击了灰色");
                    } else if (R.id.rb_eraser_color_white == id) {
                        ToastUtil.showShort("点击了白色");
                    } else if (R.id.rb_eraser_color_black == id) {
                        ToastUtil.showShort("点击了黑色");
                    }
                }
            }
        };

        private void onClickCallBack() {
        }

    }
}
