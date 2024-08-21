package com.eaibot.running.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eaibot.running.R;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.nudtit.lib_common.base.BaseModuleAppAdapter;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * @author :  zengxuewen
 * @date :  2023/1/12
 * @desc : 位置点列表
 */
public class LocationListAdapter extends BaseModuleAppAdapter<SlamLocationBean> {
    private OnLocationListener mListener;

    public LocationListAdapter(@NotNull Context context) {
        super(context);
    }

    public void setOnLocationListener(OnLocationListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(R.layout.dialog_location_list_item);
    }

    private final class ViewHolder extends BaseViewHolder {

        private final TextView mTvPointName;
        //        private final Button mBtEdit;
        private final Button mBtDelete;

        public ViewHolder(int id) {
            super(id);
            mTvPointName = findViewById(R.id.tv_location_list_item_name);
            mBtDelete = findViewById(R.id.btn_location_list_item_delete);

        }

        @Override
        public void onBindView(final int position) {
            final SlamLocationBean locationBean = getItem(position);
            mTvPointName.setText(locationBean.getLocationNameChina());
            mBtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onDeleteLocation(locationBean, position);
                    }
                }
            });
        }
    }

    public interface OnLocationListener {

        /**
         * 编辑位置
         *
         * @param data
         * @param position
         */
        void onEditLocation(SlamLocationBean data, int position);

        /**
         * 删除位置
         *
         * @param data
         * @param position
         */
        void onDeleteLocation(SlamLocationBean data, int position);
    }
}
