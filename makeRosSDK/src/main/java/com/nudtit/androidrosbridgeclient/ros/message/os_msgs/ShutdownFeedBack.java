package com.nudtit.androidrosbridgeclient.ros.message.os_msgs;

import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;

@MessageType(string = "os_msgs/ShutdownFeedBack")
public class ShutdownFeedBack extends Message {

    public float battery_percentage;
    public float time_remaining;
    public int shutdown_time;

}
