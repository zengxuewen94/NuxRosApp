package com.eaibot.running.event;

/**
 * @author :  zengxuewen
 * @date :  2022/8/11
 * @desc :  底盘连接状态event
 */
public class RosConnectStatusEvent {
    private boolean connectStatus;
    private String reason;
    public RosConnectStatusEvent(boolean connectStatus){
        this.connectStatus=connectStatus;
    }


    public RosConnectStatusEvent(boolean connectStatus,String reason){
        this.connectStatus=connectStatus;
        this.reason=reason;
    }

    public boolean isConnect() {
        return connectStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
