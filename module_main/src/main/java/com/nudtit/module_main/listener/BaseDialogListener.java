package com.nudtit.module_main.listener;


/**
 * @author :  zengxuewen
 * @date :  2022/8/23
 * @desc :  TODO
 */
public  interface BaseDialogListener {
    /**
     * 关闭弹窗
     */
    default void close(){}

    /**
     * 确认动作
     */
    default void confirm(){}

    /**
     * 确认动作
     */
    default void confirm(int type){}
}
