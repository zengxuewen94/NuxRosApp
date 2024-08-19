package com.nudtit.module_main.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.MapSaveListener;


/**
 * @author :  zengxuewen
 * @date :  2021/12/30
 * @desc :  地图保存
 */
public class MapSaveDialog {


    public static final class Builder
            extends BaseNewDialog.Builder<MapSaveDialog.Builder> {
        private EditText etName;
        private MapSaveListener mOnMapSaveListener;

        public Builder(Context context) {
            super(context);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setContentView(R.layout.map_save_name_input_dialog);
            etName = findViewById(R.id.et_map_control_name_input_name);

            addOnDismissListener(dialog -> {
                if (null != etName) {
                    etName.setText("");
                }

            });
            findViewById(R.id.bt_map_control_name_input_cancel).setOnClickListener(this);
            findViewById(R.id.bt_map_control_name_input_confirm).setOnClickListener(this);
            setCancelable(true);

        }


        public Builder setMapSaveListener(MapSaveListener listener) {
            mOnMapSaveListener = listener;
            return this;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.bt_map_control_name_input_confirm) {
                String mapName = etName.getText().toString().trim();
                if (TextUtils.isEmpty(mapName)) {
                    ToastUtil.showShort(getString(R.string.slam_map_save_name_hint));
                } else if (mOnMapSaveListener != null) {
                    mOnMapSaveListener.onSave(mapName);
                    dismiss();
                }
            } else if (view.getId() == R.id.bt_map_control_name_input_cancel) {
                dismiss();
            }
        }


    }



}
