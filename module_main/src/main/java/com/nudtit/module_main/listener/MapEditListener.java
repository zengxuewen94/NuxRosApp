package com.nudtit.module_main.listener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc : 地图编辑监听
 */
public interface MapEditListener {

    /**
     * 虚拟墙
     */
    default void onVirtualWall() {
    }

    /**
     * 虚拟轨道
     */
    default void onVirtualTracks() {
    }

    /**
     * 橡皮擦
     */
    default void onRubber() {
    }

}
