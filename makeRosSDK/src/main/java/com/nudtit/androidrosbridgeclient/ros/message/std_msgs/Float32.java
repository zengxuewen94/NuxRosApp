package com.nudtit.androidrosbridgeclient.ros.message.std_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
/**
 * @author Administrator
 */
@MessageType(string = "std_msgs/Float32")
public class Float32 extends Message {
    public float data;//传0°~360°范围的角度
}