package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.nudtit.control_common.base.BaseViewModel;
import com.nudtit.module_main.R;


/**
 * @author :  zengxuewen
 * @date :  2022/8/22
 * @desc :  地图编辑
 */
public class MapEditViewModel extends BaseViewModel {

    public ObservableField<String> obTitle = new ObservableField<>();

    // 虚拟墙编辑
    private MutableLiveData<Boolean> virtualWallDialogVo = new MutableLiveData<>();
    // 虚拟轨道编辑
    private MutableLiveData<Boolean> virtualTrackDialogVo = new MutableLiveData<>();
    // 橡皮檫编辑
    private MutableLiveData<Boolean> eraserDialogVo = new MutableLiveData<>();
    // 机器人位姿
    private MutableLiveData<Boolean> robotPoseDialogVo = new MutableLiveData<>();
    // 充电桩位姿
    private MutableLiveData<Boolean> charePoseDialogVo = new MutableLiveData<>();

    public MutableLiveData<Boolean> getVirtualWallDialogVo() {
        return virtualWallDialogVo;
    }

    public MutableLiveData<Boolean> getVirtualTrackDialogVo() {
        return virtualTrackDialogVo;
    }

    public MutableLiveData<Boolean> getEraserDialogVo() {
        return eraserDialogVo;
    }

    public MutableLiveData<Boolean> getRobotPoseDialogVo() {
        return robotPoseDialogVo;
    }

    public MutableLiveData<Boolean> getCharePoseDialogVo() {
        return charePoseDialogVo;
    }

    public MapEditViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 点击事件
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (R.id.iv_map_edit_title_back == id) {//返回
                activityFinishVo.postValue(true);
            } else if (R.id.iv_map_edit_upload == id) {//上传地图
                // todo
            } else if (R.id.iv_map_edit_save == id) {//保存地图
                // todo
            } else if (R.id.tv_map_edit_virtual_wall == id) {//虚拟轨道
                virtualWallDialogVo.postValue(true);
            } else if (R.id.tv_map_edit_virtual_track == id) {//虚拟墙
                virtualTrackDialogVo.postValue(true);
            } else if (R.id.tv_map_edit_eraser == id) {//橡皮檫
                eraserDialogVo.postValue(true);
            } else if (R.id.tv_map_edit_robot_pose == id) {//机器人位姿
                robotPoseDialogVo.postValue(true);
            } else if (R.id.tv_map_edit_charging_pose == id) {//充电桩位姿
                charePoseDialogVo.postValue(true);
            }
        }
    };
}
