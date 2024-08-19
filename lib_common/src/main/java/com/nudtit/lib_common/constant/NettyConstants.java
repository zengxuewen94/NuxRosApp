package com.nudtit.lib_common.constant;

/**
 * @author song
 * @date 2022/04/01
 * @desc netty 相关的全局变量
 */

public class NettyConstants {
    //正式服务器
    public static String NETTY_HOST_FORMAL = "119.23.44.248";
    //测试服务器
    public static String NETTY_HOST_TEST = "120.79.96.196";

    //app 使用的服务器-默认使用正式服务器
    public static String NETTY_HOST = NETTY_HOST_FORMAL;


    public static int NETTY_PORT = 40310;

}
