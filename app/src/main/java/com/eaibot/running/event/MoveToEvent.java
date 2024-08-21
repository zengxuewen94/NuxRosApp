package com.eaibot.running.event;

import com.eaibot.running.db.dao.SlamLocationBean;

/**
 * @author :  zengxuewen
 * @date :  2023/1/30
 * @desc :  移动导航event
 */
public class MoveToEvent {


    SlamLocationBean slamLocationBean;
    public MoveToEvent(SlamLocationBean slamLocationBean){
        this.slamLocationBean=slamLocationBean;
    }

    public SlamLocationBean getSlamLocationBean() {
        return slamLocationBean;
    }
}
