package com.nudtit.androidrosbridgeclient.ros.message.rosapi;

import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;

@MessageType(string = "rosapi/GetParam")
public class GetParamResponse extends Message {

  public String value;//返回值

}