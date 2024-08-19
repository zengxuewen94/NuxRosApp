package com.nudtit.module_main.listener;

import com.nudtit.androidrosbridgeclient.action.MoveDirection;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc :  控制底盘移动
 */
public interface ControlMoveByListener {
    /**
     * 移动
     * @param action 移动方向
     */
    void controlMoveBy(MoveDirection action);
}
