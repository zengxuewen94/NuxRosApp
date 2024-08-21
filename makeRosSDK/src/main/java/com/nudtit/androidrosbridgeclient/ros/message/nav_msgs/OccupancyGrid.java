package com.nudtit.androidrosbridgeclient.ros.message.nav_msgs;

import com.nudtit.androidrosbridgeclient.ros.message.Header;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;

@MessageType(string = "nav_msgs/OccupancyGrid")


public class OccupancyGrid extends Message {
    /**
     * 时间戳信息
     */
    public Header header;
    /**
     *地图的一些基本信息，宽高、分辨率
     */
    public MapMetaData info;
    /**
     * 地图数据
     */
    public byte[] data;

}