package com.nudtit.module_main.dialog;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.MapEditListener;



/**
 * @author :  zengxuewen
 * @date :  2022/5/16
 * @desc : 地图编辑【虚拟墙，虚拟轨道，橡皮擦】dialog
 */
public final class MapEditDialog {
    public static final class Builder extends BaseNewDialog.Builder<MapEditDialog.Builder> {

        private String TAG = MapEditDialog.class.getSimpleName();
        private MapEditListener mapEditListener;

        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.map_edit_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM | Gravity.END);
            setBackgroundDimEnabled(false);
            initView();
        }

        private void initView() {
            findViewById(R.id.tv_map_edit_dialog_virtual_wall).setOnClickListener(onClickListener);
            findViewById(R.id.tv_map_edit_dialog_virtual_tracks).setOnClickListener(onClickListener);
            findViewById(R.id.tv_map_edit_dialog_rubber).setOnClickListener(onClickListener);
        }

        public Builder setMapEditListener(MapEditListener mapEditListener) {
            this.mapEditListener = mapEditListener;
            return this;
        }


        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                dismiss();
                if (null != mapEditListener) {
                    if (id == R.id.tv_map_edit_dialog_virtual_wall) {//虚拟墙
                        mapEditListener.onVirtualWall();
                    } else if (id == R.id.tv_map_edit_dialog_virtual_tracks) {//虚拟轨道
                        mapEditListener.onVirtualTracks();
                    } else if (id == R.id.tv_map_edit_dialog_rubber) {//重定位
                        mapEditListener.onRubber();
                    }
                }
            }
        };


    }

}
