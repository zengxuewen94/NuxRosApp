package com.nudtit.androidrosbridgeclient.ros.message.os_msgs;

import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;

@MessageType(string = "os_msgs/NProcess")
public class NProcess extends Message {

    public String system_cmd;
    public String[] args;

}