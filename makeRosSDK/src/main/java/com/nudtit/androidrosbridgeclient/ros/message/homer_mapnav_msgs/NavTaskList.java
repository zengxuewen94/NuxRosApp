package com.nudtit.androidrosbridgeclient.ros.message.homer_mapnav_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "homer_mapnav_msgs/NavTaskList")
public class NavTaskList extends Message {
    public NavTask[] tasks;
}