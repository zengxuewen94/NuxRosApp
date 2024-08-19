package com.nudtit.module_main.listener;

/**
 * @author :  zengxuewen
 * @date :  2022/8/23
 * @desc :  调整坐标监听
 */
public interface TrimPoseListener extends BaseDialogListener{

    /**
     *
     * @param poseType 调整类型【充电桩/机器人】
     *
     * @param angle 角度
     */
    default void onAngle(int poseType,double angle){}

    /**
     *
     * @param poseType 调整类型【充电桩/机器人】
     *
     */
    default void onPose(int poseType){}
}
