package com.eaibot.running.db.dao;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * @author song
 * @date 2021/09/18
 * @desc 思岚底盘地图地点信息 LocationBean
 */
@Entity
public class SlamLocationBean implements Parcelable, Cloneable {
    @Id(autoincrement = true)
    /**唯一标识*/
    private Long tableId;
    @NotNull
    private String mapName;
    /**
     * 地点的排序编号
     */

    private String locationNumBer;
    /**
     * 位置点所在的楼层或者区域
     */
    private String locationLevel;
    /**
     * 地点英文名
     */
    private String locationNameEnglish;
    /**
     * 地点中文名
     */
    @NotNull
    private String locationNameChina;
    /**
     * 备注
     */
    private String content;
    @NotNull
    private float x;
    @NotNull
    private float y;
    @NotNull
    private float yaw;
    private int type;
    private int sensorStatus;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private long time;

    protected SlamLocationBean(Parcel in) {
        if (in.readByte() == 0) {
            tableId = null;
        } else {
            tableId = in.readLong();
        }
        mapName = in.readString();
        locationNumBer = in.readString();
        locationLevel = in.readString();
        locationNameEnglish = in.readString();
        locationNameChina = in.readString();
        content = in.readString();
        x = in.readFloat();
        y = in.readFloat();
        yaw = in.readFloat();
        type = in.readInt();
        sensorStatus = in.readInt();
        startX = in.readFloat();
        startY = in.readFloat();
        endX = in.readFloat();
        endY = in.readFloat();
        time = in.readLong();
    }

    @Generated(hash = 2087544327)
    public SlamLocationBean(Long tableId, @NotNull String mapName, String locationNumBer,
            String locationLevel, String locationNameEnglish,
            @NotNull String locationNameChina, String content, float x, float y, float yaw,
            int type, int sensorStatus, float startX, float startY, float endX, float endY,
            long time) {
        this.tableId = tableId;
        this.mapName = mapName;
        this.locationNumBer = locationNumBer;
        this.locationLevel = locationLevel;
        this.locationNameEnglish = locationNameEnglish;
        this.locationNameChina = locationNameChina;
        this.content = content;
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.type = type;
        this.sensorStatus = sensorStatus;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.time = time;
    }

    @Generated(hash = 250354834)
    public SlamLocationBean() {
    }
    
    public static final Creator<SlamLocationBean> CREATOR = new Creator<SlamLocationBean>() {
        @Override
        public SlamLocationBean createFromParcel(Parcel in) {
            return new SlamLocationBean(in);
        }

        @Override
        public SlamLocationBean[] newArray(int size) {
            return new SlamLocationBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (tableId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(tableId);
        }
        parcel.writeString(mapName);
        parcel.writeString(locationNumBer);
        parcel.writeString(locationLevel);
        parcel.writeString(locationNameEnglish);
        parcel.writeString(locationNameChina);
        parcel.writeString(content);
        parcel.writeFloat(x);
        parcel.writeFloat(y);
        parcel.writeFloat(yaw);
        parcel.writeInt(type);
        parcel.writeInt(sensorStatus);
        parcel.writeFloat(startX);
        parcel.writeFloat(startY);
        parcel.writeFloat(endX);
        parcel.writeFloat(endY);
        parcel.writeLong(time);
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getLocationNumBer() {
        return locationNumBer;
    }

    public void setLocationNumBer(String locationNumBer) {
        this.locationNumBer = locationNumBer;
    }

    public String getLocationLevel() {
        return locationLevel;
    }

    public void setLocationLevel(String locationLevel) {
        this.locationLevel = locationLevel;
    }

    public String getLocationNameEnglish() {
        return locationNameEnglish;
    }

    public void setLocationNameEnglish(String locationNameEnglish) {
        this.locationNameEnglish = locationNameEnglish;
    }

    public String getLocationNameChina() {
        return locationNameChina;
    }

    public void setLocationNameChina(String locationNameChina) {
        this.locationNameChina = locationNameChina;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(int sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Long getTableId() {
        return this.tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
