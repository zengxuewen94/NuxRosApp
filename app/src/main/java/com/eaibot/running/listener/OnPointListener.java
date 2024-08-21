package com.eaibot.running.listener;

import com.eaibot.running.db.dao.SlamLocationBean;

/**
 * @author :  zengxuewen
 * @date :  2023/1/12
 * @desc :  位置点列表-位置点操作监听
 */
public interface OnPointListener {
    void onMove(SlamLocationBean slamLocationBean);

}
