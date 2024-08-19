package com.nudtit.module_main.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.VirtualWallEditListener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc :  虚拟轨道编辑dialog
 */
public final class VirtualTracksEditDialog {
    public static final class Builder extends BaseNewDialog.Builder<VirtualTracksEditDialog.Builder> {

        private String TAG = MapOperateDialog.class.getSimpleName();
        private VirtualWallEditListener virtualWallEditListener;

        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.virtual_wall_edit_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM);
            setBackgroundDimEnabled(false);
            setCanceledOnTouchOutside(false);
            initView();
        }

        private void initView() {
            findViewById(R.id.iv_virtual_cancel).setOnClickListener(onClickListener);
            findViewById(R.id.iv_virtual_selected).setOnClickListener(onClickListener);
            findViewById(R.id.tv_virtual_wall_area_delete).setOnClickListener(onClickListener);
            findViewById(R.id.tv_virtual_wall_delete).setOnClickListener(onClickListener);
            TextView tvSteep=findViewById(R.id.tv_virtual_steep_wall);
            tvSteep.setOnClickListener(onClickListener);
            tvSteep.setText(R.string.main_virtual_tracks_steep);
            TextView tvCurve=findViewById(R.id.tv_virtual_curve_wall);
            tvCurve.setOnClickListener(onClickListener);
            tvCurve.setText(R.string.main_virtual_tracks_curve);
        }

        public VirtualTracksEditDialog.Builder setVirtualWallEditListener(VirtualWallEditListener virtualWallEditListenerr) {
            this.virtualWallEditListener = virtualWallEditListenerr;
            return this;
        }


        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (null != virtualWallEditListener) {
                    if (id == R.id.tv_virtual_wall_area_delete) {//区域删除
                        virtualWallEditListener.areaDelete();
                    } else if (id == R.id.tv_virtual_wall_delete) {//删除
                        virtualWallEditListener.delete();
                    } else if (id == R.id.tv_virtual_steep_wall) {//直线
                        virtualWallEditListener.addSteepWall();
                    } else if (id == R.id.tv_virtual_curve_wall) {//曲线
                        virtualWallEditListener.addCurveWall();
                    } else if (id == R.id.iv_virtual_selected) {

                    }else if (id == R.id.iv_virtual_cancel) {//取消
                        dismiss();
                    }
                }

          }
        };


    }
}
