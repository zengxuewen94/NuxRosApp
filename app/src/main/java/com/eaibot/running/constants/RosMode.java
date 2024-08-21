package com.eaibot.running.constants;

/**
 * Created by Administrator on 2018/5/4.
 */

public class RosMode {

    public static final int GMAPPINGIMUSTART = 1;                    //带陀螺仪带回充的建图服务

    public static final int GMAPPINGSTART = 2;                       //不带陀螺仪带回充的建图服务

    public static final int GMAPPINGIMUNORECHARGESTART = 3;          //带陀螺仪不带回充的建图服务

    public static final int GMAPPINGNORECHARGESTART = 4;             //不带陀螺仪不带回充的建图服务

    public static final int NAVIGATIONIMUSTART = 5;                  //带陀螺仪带回充的导航服务

    public static final int NAVIGATIONSTART = 6;                     //不带陀螺仪带回充的导航服务

    public static final int NAVIGATIONIMUNORECHARGESTART = 7;        //带陀螺仪不带回充的导航服务

    public static final int NAVIGATIONNORECHARGESTART = 8;           //不带陀螺仪不带回充的导航服务

    public static final int CARTOGRAPHERSTART = 9;                   //带回充的谷歌建图服务

    public static final int CARTOGRAPHERNORECHARGESTART = 10;        //不带回充的谷歌建图服务

    public static final int GMAPPINGIMUSTOP = 11;                    //关闭带陀螺仪的建图服务

    public static final int GMAPPINGSTOP = 12;                       //关闭不带陀螺仪的建图服务

    public static final int NAVIGATIONIMUSTOP = 13;                  //关闭带陀螺仪的导航服务

    public static final int NAVIGATIONSTOP = 14;                     //关闭不带陀螺仪的导航服务

    public static final int CARTOGRAPHERSTOP = 15;                   //关闭谷歌建图服务

    public static final int AUTOGMAPPINGIMUSTART = 16;               //启动带imu带回充的自动建图

    public static final int AUTOGMAPPINGIMUNORECHARGESTART = 17;     //启动带imu不带回充的自动建图

    public static final int AUTOGMAPPINGSTART = 18;                  //启动不带imu带回充的自动建图

    public static final int AUTOGMAPPINGNORECHARGESTART = 19;        //启动不带imu不带回充的自动建图

}
