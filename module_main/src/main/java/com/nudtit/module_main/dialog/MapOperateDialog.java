package com.nudtit.module_main.dialog;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.MapOperateListener;


/**
 * @author :  zengxuewen
 * @date :  2022/5/16
 * @desc : 地图操作dialog
 */
public final class MapOperateDialog {
    public static final class Builder extends BaseNewDialog.Builder<MapOperateDialog.Builder> {

        private String TAG = MapOperateDialog.class.getSimpleName();
        private MapOperateListener mapOperateListener;

        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.map_operate_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM | Gravity.LEFT);
            setBackgroundDimEnabled(false);
            initView();
        }

        private void initView() {
            findViewById(R.id.tv_map_operate_dialog_clear).setOnClickListener(onClickListener);
            findViewById(R.id.tv_map_operate_dialog_save).setOnClickListener(onClickListener);
            findViewById(R.id.tv_map_operate_dialog_relocation).setOnClickListener(onClickListener);
            findViewById(R.id.tv_map_operate_dialog_mapEdit).setOnClickListener(onClickListener);
        }

        public Builder setMapOperateListener(MapOperateListener mapOperateListener) {
            this.mapOperateListener = mapOperateListener;
            return this;
        }


        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                dismiss();
                if (null != mapOperateListener) {
                    if (id == R.id.tv_map_operate_dialog_clear) {//清空

                        mapOperateListener.onClearMap();

                    } else if (id == R.id.tv_map_operate_dialog_save) {//保存

                        mapOperateListener.onSaveMap();

                    } else if (id == R.id.tv_map_operate_dialog_relocation) {//重定位

                        mapOperateListener.onReLocation();

                    } else if (id == R.id.tv_map_operate_dialog_mapEdit) {//地图编辑
                        mapOperateListener.onEdit();
                    }
                }
            }
        };


    }

}
