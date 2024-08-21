package com.nudtit.androidrosbridgeclient.ros.message.homer_mapnav_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.nav_msgs.MapMetaData;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "homer_mapnav_msgs/MapInfo")
public class MapInfo extends Message {
    public MapMetaData info;
    public String name;//地图名
}