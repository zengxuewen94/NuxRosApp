package com.nudtit.androidrosbridgeclient.ros.message.os_msgs;

import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;

@MessageType(string = "os_msgs/ResMsg")
public class ResMsg extends Message {

    public boolean success;
    public String status_message;

}
