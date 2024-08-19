package com.nudtit.module_main.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.module_main.R;

import com.nudtit.module_main.listener.TrimPoseListener;
import com.nudtit.module_main.ruler.ScaleView;

/**
 * @author :  zengxuewen
 * @date :  2022/8/23
 * @desc :  调整【充电桩/机器人】位姿
 */
public final class TrimPoseDialog {
    public static final class Builder extends BaseNewDialog.Builder<TrimPoseDialog.Builder> {

        private static double mAngel = 0;
        private String TAG = TrimPoseDialog.class.getSimpleName();
        private TrimPoseListener trimPoseListener;
        private ScaleView scaleView;
        private int type;
        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.trim_pose_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM);
            setBackgroundDimEnabled(false);
            setCanceledOnTouchOutside(false);
            initView();
        }

        private void initView() {
            scaleView = findViewById(R.id.ruler_trim_pose_dialog_view);
            scaleView.setMinIndex(-180);
            scaleView.setMaxIndex(180);
            scaleView.setNowIndex(mAngel);
            scaleView.setOnScaleChangeListener(new ScaleView.OnScaleChangeListener() {
                @Override
                public void onChange(double index) {
                    if (null != trimPoseListener) {
                        trimPoseListener.onAngle(type, index);
                    }
                }
            });
            findViewById(R.id.iv_trim_pose_dialog_cancel).setOnClickListener(onClickListener);
            CheckBox checkBox=findViewById(R.id.iv_trim_pose_dialog_pose);
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
            findViewById(R.id.iv_trim_pose_dialog_selected).setOnClickListener(onClickListener);
        }

        public Builder setAngel(double angel) {
            scaleView.setNowIndex(angel);
            return this;
        }
        public Builder setType(int type) {
            this.type=type;
            return this;
        }


        public TrimPoseDialog.Builder setTrimPoseListener(TrimPoseListener trimPoseListener) {
            this.trimPoseListener = trimPoseListener;
            return this;
        }

        public View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();

                if (R.id.iv_trim_pose_dialog_cancel == id) {
                    dismiss();
                }  else if (R.id.iv_trim_pose_dialog_selected == id) {

                }
            }
        };

        public CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        };
    }
}
