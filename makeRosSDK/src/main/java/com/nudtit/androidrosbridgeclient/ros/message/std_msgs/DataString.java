package com.nudtit.androidrosbridgeclient.ros.message.std_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
/**
 * @author Administrator
 */
@MessageType(string = "std_msgs/String")
public class DataString extends Message {
    public String data;
}