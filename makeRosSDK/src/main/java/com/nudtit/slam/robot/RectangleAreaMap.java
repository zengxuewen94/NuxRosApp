package com.nudtit.slam.robot;

import java.util.ArrayList;

/**
 * @author :  zengxuewen
 * @date :  2024/6/7
 * @desc :  TODO
 */
public class RectangleAreaMap extends MapLayer {
    private ArrayList<RectangleArea> areas_;
    private String id_;

    public RectangleAreaMap() {
    }

    public ArrayList<RectangleArea> getAreas() {
        return this.areas_;
    }

    public void setAreas(ArrayList<RectangleArea> areas) {
        this.areas_ = areas;
    }

    public String getId() {
        return this.id_;
    }

    public void setId(String id) {
        this.id_ = id;
    }

    public void clear() {
        super.clear();
        this.areas_.clear();
    }
}