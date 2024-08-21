package com.eaibot.running.constants;

/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  常量
 */
public class BaseConstant {
    // 底盘连接ip
    public static final String CONNECT_HEADER="ws://";
    // 底盘连接ip
    public static final String CONNECT_IP="ws://192.168.0.200";
    // 底盘连接端口号
    public static final String CONNECT_PORT="9090";
    // 底盘连接冒号分隔符
    public static final String CONNECT_SPLIT=":";


    //地图文件格式
    public static final String FILE_NAME_SUFFIX = ".yaml";
    //跨楼层地图分割符号
    public static final String MAP_NAME_SPLIT = "-";


    //底盘是否连接
    public static boolean rosConnected=false;

}
