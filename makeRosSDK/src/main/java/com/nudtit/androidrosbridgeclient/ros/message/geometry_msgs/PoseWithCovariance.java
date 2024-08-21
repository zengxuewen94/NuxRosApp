package com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "geometry_msgs/PoseWithCovariance ")
public class PoseWithCovariance extends Message {
  public Pose pose;
  public double[] covariance;
}