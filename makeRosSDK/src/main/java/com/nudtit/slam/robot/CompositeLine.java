package com.nudtit.slam.robot;

import com.nudtit.slam.bean.Location;

/**
 * @author :  zengxuewen
 * @date :  2024/6/7
 * @desc :
 */

public final class CompositeLine {
    private String name;
    private Location start;
    private Location end;
    private MapMetaData metaData;

    public CompositeLine() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getStart() {
        return this.start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return this.end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public MapMetaData getMetaData() {
        return this.metaData;
    }

    public void setMetaData(MapMetaData metaData) {
        this.metaData = metaData;
    }
}