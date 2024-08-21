package com.nudtit.androidrosbridgeclient.ros.message.actionlib_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
import com.nudtit.androidrosbridgeclient.ros.message.Time;

@MessageType(string = "actionlib_msgs/GoalID")
public class GoalID extends Message {
  public Time stamp;//时间
  public String id;//任务id
}
