package com.nudtit.module_main.listener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc :  虚拟墙/轨道编辑监听
 */
public interface VirtualWallEditListener {

    /**
     * 删除虚拟墙/轨道
     */
    default void delete(){}

    /**
     * 区域删除虚拟墙/轨道
     */
    default void areaDelete(){}

    /**
     * 添加直线虚拟墙/轨道
     */
    default void addSteepWall(){}

    /**
     * 添加曲线虚拟墙/轨道
     */
    default void addCurveWall(){}
}
