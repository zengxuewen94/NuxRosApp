package com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "geometry_msgs/TwistWithCovariance")
public class TwistWithCovariance extends Message {
  public Twist twist;
  public double[] covariance;
}