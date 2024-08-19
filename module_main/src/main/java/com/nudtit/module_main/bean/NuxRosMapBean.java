package com.nudtit.module_main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author :  zengxuewen
 * @date :  2022/08/11
 * @desc :  地图 Bean
 */
public class NuxRosMapBean implements Parcelable{
    private String mapName;//地图名称
    private String mapPath;//地图本地的地址
    private boolean selected;//选中状态
    private String mapLevel;//地图所在楼层/区域
    public NuxRosMapBean(){

    }


    protected NuxRosMapBean(Parcel in) {
        mapName = in.readString();
        mapPath = in.readString();
        selected = in.readByte() != 0;
        mapLevel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mapName);
        dest.writeString(mapPath);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(mapLevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NuxRosMapBean> CREATOR = new Creator<NuxRosMapBean>() {
        @Override
        public NuxRosMapBean createFromParcel(Parcel in) {
            return new NuxRosMapBean(in);
        }

        @Override
        public NuxRosMapBean[] newArray(int size) {
            return new NuxRosMapBean[size];
        }
    };

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(String mapLevel) {
        this.mapLevel = mapLevel;
    }
}
