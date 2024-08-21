package com.nudtit.androidrosbridgeclient.ros.message.homer_mapnav_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "homer_mapnav_msgs/NavTask")
public class NavTask extends Message {
    public String name;
    public int seq;
    public PointOfInterest[] pois;

}