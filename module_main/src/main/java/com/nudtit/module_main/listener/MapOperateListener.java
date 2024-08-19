package com.nudtit.module_main.listener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc : 地图操作监听
 */
public interface MapOperateListener {

    /**
     * 清空地图
     */
    default void onClearMap() {
    }

    /**
     * 保存地图
     */
    default void onSaveMap() {
    }

    /**
     * 重定位
     */
    default void onReLocation() {
    }

    /**
     * 地图编辑
     */
    default void onEdit() {
    }
}
