package com.nudtit.androidrosbridgeclient.ros.message.move_base_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Header;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
import com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs.PoseStamped;

@MessageType(string = "move_base_msgs/MoveBaseActionGoal")
public class MoveBaseGoal extends Message {
public PoseStamped target_pose;//目标点位置信息
}
