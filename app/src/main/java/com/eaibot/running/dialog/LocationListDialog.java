package com.eaibot.running.dialog;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.eaibot.running.R;
import com.eaibot.running.adapter.LocationListAdapter;
import com.eaibot.running.db.SlamLocationDBManager;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.listener.OnPointListener;
import com.eaibot.running.manger.RosDeviceManager;
import com.hjq.toast.Toaster;
import com.nudtit.lib_common.base.BaseAdapter;
import com.nudtit.lib_common.base.BaseNewDialog;

import com.nudtit.lib_common.utils.Utils;
import com.nudtit.slam.bean.LocalPose;


import java.util.List;

/**
 * @author :  zengxuewen
 * @date :  2023/1/12
 * @desc :  位置点列表
 */
public class LocationListDialog {
    public static final class Builder extends BaseNewDialog.Builder<Builder> {

        private RecyclerView recyclerView;
        private LocationListAdapter adapter;
        private List<SlamLocationBean> mList;
        private OnPointListener onPointListener;
        private int mType;
        private MapAddPointDialog.Builder mMapAddPointDialog;
        private LocalPose pose;

        public Builder(Activity activity) {
            super(activity);
            initView();
        }

        private void initView() {
            setContentView(R.layout.dialog_location_list);
            setBackgroundDimEnabled(false);
            setCanceledOnTouchOutside(true);
            recyclerView = findViewById(R.id.rv_dialog_location_list);
            findViewById(R.id.btn_dialog_location_add_point).setOnClickListener(this);
            adapter = new LocationListAdapter(Utils.getContext());
            addOnDismissListener(new BaseNewDialog.OnDismissListener() {
                @Override
                public void onDismiss(BaseNewDialog dialog) {
                    if (null!=mMapAddPointDialog){
                        mMapAddPointDialog.dismiss();
                        mMapAddPointDialog=null;
                    }

                }
            });
            // 位置点item 点击 单个位置点导航
            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                    if (null != onPointListener) {
                        onPointListener.onMove(mList.get(position));
                    }
                }
            });
            adapter.setOnLocationListener(new LocationListAdapter.OnLocationListener() {

                @Override
                public void onEditLocation(SlamLocationBean data, int position) {

                }

                @Override
                public void onDeleteLocation(SlamLocationBean data, int position) {
                    if (mList != null && mList.size() >= position) {
                        // 直接移除
                        mList.remove(position);
                        // 删除数据库记录
                        SlamLocationDBManager.getInstance(Utils.getContext()).deleteLocationListById(data.getTableId());
                        adapter.setNewData(mList);
                        // list大小为0 ，dialog消失
                        if (mList.isEmpty()) {
                            dismiss();
                        }
                    }
                }
            });
            addOnDismissListener(new BaseNewDialog.OnDismissListener() {
                @Override
                public void onDismiss(BaseNewDialog dialog) {
                    if (null != adapter) {
                        adapter.clearData();
                    }
                    if (null != mMapAddPointDialog) {
                        mMapAddPointDialog.dismiss();
                        mMapAddPointDialog = null;
                    }
                }
            });
        }

        public Builder setData(int type, List<SlamLocationBean> list) {
            mList = list;
            mType = type;
            if (null != mList && !mList.isEmpty()) {
                adapter.setNewData(mList);
            }
            recyclerView.setAdapter(adapter);
            return this;
        }

        public Builder setOnPointListener(OnPointListener onPointListener) {
            this.onPointListener = onPointListener;
            return this;
        }

        @Override
        public void show() {
            super.show();
            if (1 == mType) {//如果是添加位置点
                showAddPointDialog(false, new SlamLocationBean());
            }
        }


        @Override
        public void onClick(View view) {
            if (R.id.btn_dialog_location_add_point == view.getId()) {
                // 请求当前pose
                showAddPointDialog(false, null);
            }
        }

        /**
         * 显示新增位置点弹窗
         *
         * @param isUpdate
         * @param slamLocationBean
         */
        private void showAddPointDialog(boolean isUpdate, SlamLocationBean slamLocationBean) {
            pose = RosDeviceManager.INSTANCE.getPose();
            if (null == mMapAddPointDialog) {
                mMapAddPointDialog = new MapAddPointDialog.Builder(getActivity());
            }
            if (mMapAddPointDialog.isShowing()) {
                mMapAddPointDialog.dismiss();
            }
            mMapAddPointDialog.setListener((locationBean, isUpdate1) -> {

                if (pose == null) {
                    pose = RosDeviceManager.INSTANCE.getPose();
                    Toaster.showLong(getString(R.string.pose_get_fail_tips));
                } else {
                    locationBean.setX(pose.getLocation().getX());
                    locationBean.setY(pose.getLocation().getY());
                    locationBean.setYaw(pose.getRotation().getYaw());
                    //此处返回的是tableId
                    long tableId = SlamLocationDBManager.getInstance(Utils.getContext()).insert(locationBean);
                    //插入更新成功
                    if (tableId > 0) {
                        locationBean.setTableId(tableId);
                        mList.add(locationBean);
                        adapter.setNewData(mList);

                    }

                }

            });
            mMapAddPointDialog.setData(isUpdate, slamLocationBean);
            mMapAddPointDialog.show();
        }

    }
}
