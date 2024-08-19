package com.nudtit.module_main.base;

import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;

import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.bean.NuxRosMapBean;
import com.nudtit.module_main.manager.RosDeviceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc : 数据管理类
 */
public enum BaseDataManager {
    /**
     * 单例
     */
    INSTANCE;

    /**
     * @return 地图列表
     */
    public ArrayList<NuxRosMapBean> getMapList() {
        try {
            List<String> mapNameList = RosDeviceManager.INSTANCE.getMapList();
            if (null == mapNameList) {
                return null;
            }
            ArrayList<NuxRosMapBean> beanList = new ArrayList<>();
            for (String fileName : mapNameList) {
                if (!TextUtils.isEmpty(fileName)) {
                    NuxRosMapBean slamMapBean = new NuxRosMapBean();
                    String mapName = fileName;
                    if (mapName.contains(BaseConstant.MAP_NAME_SPLIT)) {
                        slamMapBean.setMapLevel(mapName.split(BaseConstant.MAP_NAME_SPLIT)[1]);
                    } else {
                        slamMapBean.setMapLevel("");
                    }
                    slamMapBean.setMapName(mapName);
                    slamMapBean.setMapPath(fileName);
                    beanList.add(slamMapBean);
                }
            }
            return beanList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 导航模式
     */
    public int getNavigateModel() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getInt(SettingConstant.SETTING_NAVIGATE_MODEL, SettingConstant.SETTING_NAVIGATE_FREE);
    }

    /**
     * 导航模式
     */
    public void setNavigateModel(int navigateModel) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putInt(SettingConstant.SETTING_NAVIGATE_MODEL, navigateModel);
    }


    /**
     * 到点模式
     */
    public int getArrivePointModel() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getInt(SettingConstant.SETTING_ARRIVE_POINT_MODEL, SettingConstant.SETTING_ARRIVE_POINT_COMMON);

    }


    /**
     * 到点模式
     */
    public void setArrivePointModel(int arrivePointModel) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putInt(SettingConstant.SETTING_ARRIVE_POINT_MODEL, arrivePointModel);

    }


    /**
     * @return 雷达图层显示开关状态
     */
    public boolean getRadarSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_RADAR, true);
    }

    /**
     * 雷达图层显示开关
     *
     * @param isOpen 是否开启
     */
    public void setRadarSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_RADAR, isOpen);
    }

    /**
     * @return 虚拟墙显示开关状态
     */
    public boolean getVirtualWallSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_VIRTUAL_WALL, true);
    }

    /**
     * 虚拟墙显示开关
     *
     * @param isOpen 是否开启
     */
    public void setVirtualWallSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_VIRTUAL_WALL, isOpen);
    }

    /**
     * @return 虚拟轨道显示开关状态
     */
    public boolean getVirtualTracksSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_VIRTUAL_TRACKS, true);
    }

    /**
     * 虚拟轨道显示开关
     *
     * @param isOpen 是否开启
     */
    public void setVirtualTracksSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_VIRTUAL_TRACKS, isOpen);
    }

    /**
     * @return 传感器图层显示开关状态
     */
    public boolean getSensorLayerSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_SENSOR_LAYER, true);
    }

    /**
     * 传感器图层显示开关
     *
     * @param isOpen 是否开启
     */
    public void setSensorLayerSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_SENSOR_LAYER, isOpen);
    }

    /**
     * @return 清扫图层显示开关状态
     */
    public boolean getCleaningLayerSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_CLEANING_LAYER, true);
    }

    /**
     * 清扫图层显示开关
     *
     * @param isOpen 是否开启
     */
    public void setCleaningLayerSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_CLEANING_LAYER, isOpen);
    }

    /**
     * @return 坐标系显示开关状态
     */
    public boolean getWcsSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_WCS, true);
    }

    /**
     * 坐标系显示开关
     *
     * @param isOpen 是否开启
     */
    public void setWcsSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_WCS, isOpen);
    }

    /**
     * @return 目标点显示开关状态
     */
    public boolean getTargetSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_TARGET, true);
    }

    /**
     * 目标点显示开关
     *
     * @param isOpen 是否开启
     */
    public void setTargetSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_TARGET, isOpen);
    }

    /**
     * @return 路径点显示开关状态
     */
    public boolean getWayPointSwitch() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean(SettingConstant.SETTING_DISPLAY_WAY_POINT, true);
    }

    /**
     * 路径点显示开关
     *
     * @param isOpen 是否开启
     */
    public void setWayPointSwitch(boolean isOpen) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean(SettingConstant.SETTING_DISPLAY_WAY_POINT, isOpen);
    }
}
