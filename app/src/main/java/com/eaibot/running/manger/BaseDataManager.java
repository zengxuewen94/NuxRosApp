package com.eaibot.running.manger;

import android.text.TextUtils;


import com.eaibot.running.bean.NuxRosMapBean;
import com.eaibot.running.constants.BaseConstant;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.nudtit.androidrosbridgeclient.ros.message.homer_mapnav_msgs.MapInfo;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.Utils;

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
    /** 建图时的地图数据 */
    private List<SlamLocationBean> mSlamLocationList;
    /**
     * @return 地图列表
     */
    public ArrayList<NuxRosMapBean> getMapList() {
        try {
            List<MapInfo> mapNameList = RosDeviceManager.INSTANCE.getMapList();
            if (null == mapNameList) {
                return null;
            }
            ArrayList<NuxRosMapBean> beanList = new ArrayList<>();
            for (MapInfo fileName : mapNameList) {
                if (!TextUtils.isEmpty(fileName.name)) {
                    NuxRosMapBean slamMapBean = new NuxRosMapBean();
                    String mapName = fileName.name;
                    if (mapName.contains(BaseConstant.MAP_NAME_SPLIT)) {
                        slamMapBean.setMapLevel(mapName.split(BaseConstant.MAP_NAME_SPLIT)[1]);
                    } else {
                        slamMapBean.setMapLevel("");
                    }
                    slamMapBean.setMapName(mapName);
                    slamMapBean.setMapPath(fileName.name);
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
     *
     * @return 位姿点列表
     */
    public List<SlamLocationBean> getSlamLocationList() {
        return mSlamLocationList;
    }

    public void setSlamLocationList(List<SlamLocationBean> mSlamLocationList) {
        this.mSlamLocationList = mSlamLocationList;
    }

    /**
     * 保存加载的地图
     * @param mapName
     */
    public void setLoadMapName(String mapName) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putString("loadMap", mapName);
    }

    public String getLoadMapName() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getString("loadMap", "");
    }


    /**
     * 获取用于获取地图的母体名称
     *
     * @param mapName “国科智能.yaml”
     * @return 国科智能
     */
    public String getBaseLoadMapName(String mapName) {
        if (!TextUtils.isEmpty(mapName) && mapName.contains(BaseConstant.FILE_NAME_SUFFIX)) {
            return mapName.split(BaseConstant.FILE_NAME_SUFFIX)[0];
        } else if(!TextUtils.isEmpty(mapName)){
            return mapName;
        }
        return mapName;
    }

    /**
     * 保存ros底盘连接的ip
     * @param ip
     */
    public void setRosIp(String ip) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putString("ip", ip);
    }

    public String getRosIp() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getString("ip", BaseConstant.CONNECT_IP);
    }

    /**
     * 设置手柄显示隐藏
     * @param show
     */
    public void setHandleViewShow(boolean show) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean("handleView", show);
    }

    public Boolean getHandleViewShow() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean("handleView", true);
    }

}
