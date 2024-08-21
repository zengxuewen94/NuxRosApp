package com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "geometry_msgs/Pose")
public class Pose extends Message {
  public Point position;//位姿信息
  public Quaternion orientation;//方向信息



}
