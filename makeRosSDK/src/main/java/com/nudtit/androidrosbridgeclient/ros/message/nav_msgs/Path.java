package com.nudtit.androidrosbridgeclient.ros.message.nav_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Header;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
import com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs.PoseStamped;

@MessageType(string = "nav_msgs/Path")
public class Path extends Message {
    public Header header;
    public PoseStamped[] poses; //机器人姿态
}