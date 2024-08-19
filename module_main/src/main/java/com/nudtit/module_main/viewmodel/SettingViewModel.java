package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.nudtit.control_common.base.BaseViewModel;

import com.nudtit.lib_common.manager.ThreadPoolManager;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseDataManager;
import com.nudtit.module_main.base.SettingConstant;
import com.nudtit.module_main.manager.RosDeviceManager;


import org.jetbrains.annotations.NotNull;

/**
 * @author :  zengxuewen
 * @date :  2022/8/17
 * @desc : 设置ViewModel
 */
public class SettingViewModel extends BaseViewModel {

    // 导航速度值
    public ObservableField<String> obSpeedValue = new ObservableField<>();
    // 导航速度最大值
    public ObservableInt obMaxSpeed = new ObservableInt();
    // 导航速度seekbar Progress
    public ObservableInt obSpeedProgress = new ObservableInt();
    // 导航模式-自由导航
    public ObservableBoolean obNavigateFree = new ObservableBoolean();
    // 导航模式-轨道导航
    public ObservableBoolean obNavigateTrack = new ObservableBoolean();
    // 导航模式-导航优先
    public ObservableBoolean obNavigateTrackPrior = new ObservableBoolean();
    // 到点模式-精确到点
    public ObservableBoolean obArrivePointExact = new ObservableBoolean();
    // 到点模式-普通到点
    public ObservableBoolean obArrivePointCommon = new ObservableBoolean();
    // 暂停建图
    public ObservableBoolean obPauseBuildMap = new ObservableBoolean();
    // 继续建图
    public ObservableBoolean obContinueBuildMap = new ObservableBoolean();
    // 继续定位
    public ObservableBoolean obPauseOrientation = new ObservableBoolean();
    // 继续定位
    public ObservableBoolean obContinueOrientation = new ObservableBoolean();

    // 雷达显示
    public ObservableBoolean obRadar = new ObservableBoolean();
    // 虚拟墙示
    public ObservableBoolean obVirtualWall = new ObservableBoolean();
    // 虚拟轨道显示
    public ObservableBoolean obVirtualTracks = new ObservableBoolean();
    // 传感器显示
    public ObservableBoolean obSensorLayer = new ObservableBoolean();
    // 清扫图层显示
    public ObservableBoolean obCleaningLayer = new ObservableBoolean();
    // 坐标点显示
    public ObservableBoolean obWcs = new ObservableBoolean();
    // 目标点显示
    public ObservableBoolean obTarget = new ObservableBoolean();
    // 路径点显示
    public ObservableBoolean obWayPoint = new ObservableBoolean();

    private MutableLiveData<Boolean> jumpRunControlVo = new MutableLiveData<>();

    public MutableLiveData<Boolean> getJumpRunControlVo() {
        return jumpRunControlVo;
    }

    public SettingViewModel(@NonNull @NotNull Application application) {
        super(application);
        initData();
    }


    public void initData() {
        initTitle(Utils.getString(R.string.setting_title), R.color.white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (R.id.iv_view_setting_back == v.getId()) {
                    activityFinishVo.postValue(true);
                }
            }
        });
        setSpeed();
        setNavigateModel();
        setArrivePointModel();
        setDisplaySwitch();
    }

    private void setSpeed() {
        obMaxSpeed.set(100);
        obSpeedProgress.set(40);
        obSpeedValue.set("0.2");
    }


    /**
     * 导航模式
     */
    private void setNavigateModel() {
        int navigateModel = BaseDataManager.INSTANCE.getNavigateModel();
        if (SettingConstant.SETTING_NAVIGATE_FREE == navigateModel) {
            obNavigateFree.set(true);
            obNavigateTrack.set(false);
            obNavigateTrackPrior.set(false);
        } else if (SettingConstant.SETTING_NAVIGATE_TRACK == navigateModel) {
            obNavigateFree.set(false);
            obNavigateTrack.set(true);
            obNavigateTrackPrior.set(false);
        } else if (SettingConstant.SETTING_NAVIGATE_TRACK_PRIOR == navigateModel) {
            obNavigateFree.set(false);
            obNavigateTrack.set(false);
            obNavigateTrackPrior.set(true);
        }

    }


    /**
     * 到点模式
     */
    private void setArrivePointModel() {
        int navigateModel = BaseDataManager.INSTANCE.getArrivePointModel();
        if (SettingConstant.SETTING_ARRIVE_POINT_EXACT == navigateModel) {
            obArrivePointExact.set(true);
            obArrivePointCommon.set(false);

        } else if (SettingConstant.SETTING_ARRIVE_POINT_COMMON == navigateModel) {
            obArrivePointExact.set(false);
            obArrivePointCommon.set(true);
        }

    }

    /**
     * 信息开关
     */
    private void setDisplaySwitch() {
        // 雷达显示
        obRadar.set(BaseDataManager.INSTANCE.getRadarSwitch());
        // 虚拟墙示
        obVirtualWall.set(BaseDataManager.INSTANCE.getVirtualWallSwitch());
        // 虚拟轨道显示
        obVirtualTracks.set(BaseDataManager.INSTANCE.getVirtualTracksSwitch());
        // 传感器显示
        obSensorLayer.set(BaseDataManager.INSTANCE.getSensorLayerSwitch());
        // 清扫图层显示
        obCleaningLayer.set(BaseDataManager.INSTANCE.getCleaningLayerSwitch());
        // 坐标点显示
        obWcs.set(BaseDataManager.INSTANCE.getWcsSwitch());
        // 目标点显示
        obTarget.set(BaseDataManager.INSTANCE.getTargetSwitch());
        // 路径点显示
        obWayPoint.set(BaseDataManager.INSTANCE.getWayPointSwitch());
    }


    /**
     * 点击事件-运动控制
     */
    public void onClickRunControl() {
        jumpRunControlVo.postValue(true);
    }


    /**
     * 最大运动速度seekbar 监听
     */
    public SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.getProgress() == 0) {
                ToastUtil.showShort("速度不能位置为0");
                seekBar.setProgress(40);
                obSpeedValue.set("0.2");
            } else {
                setMaxSpeed(seekBar.getProgress());
            }

        }
    };

    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            if (id == R.id.switch_setting_radar) {//雷达
                BaseDataManager.INSTANCE.setRadarSwitch(isChecked);
            } else if (id == R.id.switch_setting_virtual_wall) {//虚拟墙
                BaseDataManager.INSTANCE.setVirtualWallSwitch(isChecked);
            } else if (id == R.id.switch_setting_virtual_tracks) {//虚拟轨道
                BaseDataManager.INSTANCE.setVirtualTracksSwitch(isChecked);
            } else if (id == R.id.switch_setting_sensor_layer) {//传感器图层
                BaseDataManager.INSTANCE.setSensorLayerSwitch(isChecked);
            } else if (id == R.id.switch_setting_cleaning_layer) {//清扫图层
                BaseDataManager.INSTANCE.setCleaningLayerSwitch(isChecked);
            } else if (id == R.id.switch_setting_wcs) {//坐标系
                BaseDataManager.INSTANCE.setWcsSwitch(isChecked);
            } else if (id == R.id.switch_setting_target) {//目标点
                BaseDataManager.INSTANCE.setTargetSwitch(isChecked);
            } else if (id == R.id.switch_setting_waypoint) {//路径点
                BaseDataManager.INSTANCE.setWayPointSwitch(isChecked);
            }
        }
    };


    /**
     * 设置最大移动速度(线速度，不是导航速度)
     *
     * @param progress
     */
    public void setMaxSpeed(int progress) {

        //todo
        if (0 < progress && progress <= 25) {
            obSpeedValue.set("0.1");
        } else if (25 < progress && progress <= 50) {
            obSpeedValue.set("0.2");
        } else if (50 < progress && progress <= 75) {
            obSpeedValue.set("0.3");
        } else if (75 < progress && progress <= 99) {
            obSpeedValue.set("0.4");
        } else if (progress == 100) {
            obSpeedValue.set("0.5");
        }

        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //RosDeviceManager.INSTANCE.setSpeed(obSpeedValue.get());
            }
        });


    }
}
