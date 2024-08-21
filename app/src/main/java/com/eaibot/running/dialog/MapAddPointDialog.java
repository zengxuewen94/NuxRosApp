package com.eaibot.running.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.eaibot.running.R;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.manger.BaseDataManager;
import com.hjq.toast.Toaster;
import com.nudtit.lib_common.base.BaseNewDialog;

import java.util.List;


/**
 * @author :  zengxuewen
 * @date :  2022/01/07
 * @desc :  位置点保存
 */
public class MapAddPointDialog {


    public static final class Builder
            extends BaseNewDialog.Builder<Builder> {

        private EditText etName;
        private OnMapAddPointDialogListener mListener;
        private boolean isUpdate = false;
        private SlamLocationBean slamLocationBean;


        public Builder(Context context) {
            super(context);
            setAnimStyle(BaseNewDialog.ANIM_IOS);
            setCanceledOnTouchOutside(false);
            setContentView(R.layout.map_add_point_dialog);
            etName = findViewById(R.id.et_point_add_name_input_name);
            findViewById(R.id.bt_map_add_point_cancel).setOnClickListener(this);
            findViewById(R.id.bt_map_add_point_confirm).setOnClickListener(this);
            addOnDismissListener(dialog -> {
                if (null != etName) {
                    etName.setText("");
                }

            });


        }


        public Builder setListener(OnMapAddPointDialogListener listener) {
            mListener = listener;
            return this;
        }

        public void setData(boolean isUpdate, SlamLocationBean slamLocationBean) {
            this.slamLocationBean = slamLocationBean;
            if (isUpdate) {
                this.isUpdate = true;
                etName.setText(slamLocationBean.getLocationNameChina());

            }
        }


        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.bt_map_add_point_confirm) {
                String pointName = etName.getText().toString().trim();
                if (TextUtils.isEmpty(pointName)) {
                    Toaster.showShort(getString(R.string.name_empty_tips));
                } else if (mListener != null) {
                    List<SlamLocationBean> slamLocationList = BaseDataManager.INSTANCE.getSlamLocationList();
                    // 地点名称是否存在
                    boolean nameExist = false;
                    Long tableId = null;
                    if (null != slamLocationList && !slamLocationList.isEmpty()) {
                        for (SlamLocationBean bean : slamLocationList) {
                            if (TextUtils.equals(bean.getLocationNameChina(), pointName)) {
                                nameExist = true;
                            }
                            tableId = bean.getTableId();

                        }
                    }
                    if (nameExist) {
                        if (!isUpdate || (isUpdate && tableId != null && slamLocationBean != null && !tableId.equals(slamLocationBean.getTableId()))) {
                            Toaster.showLong(getString(R.string.number_edit_fail_tips));
                            return;
                        }

                    }


                    if (slamLocationBean == null) {
                        slamLocationBean = new SlamLocationBean();
                    }

                    slamLocationBean.setLocationNameChina(pointName);
                    slamLocationBean.setMapName(BaseDataManager.INSTANCE.getLoadMapName());
                    mListener.onName(slamLocationBean, isUpdate);


                    dismiss();
                }
            } else if (id == R.id.bt_map_add_point_cancel) {
                dismiss();
            }
        }


    }

    public interface OnMapAddPointDialogListener {
        void onName(SlamLocationBean slamLocationBean, boolean isUpdate);
    }

}
