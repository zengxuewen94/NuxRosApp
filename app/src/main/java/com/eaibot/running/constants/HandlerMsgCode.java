package com.eaibot.running.constants;

public class HandlerMsgCode {

    public static final int SERVICESTART = 0x000;               //启动服务

    public static final int GMAPPINGSTART = 0x001;              //启动建图服务

    public static final int NAVIGATIONSTART = 0x002;            //启动导航服务

    public static final int GETMAPS = 0X003;                    //获取地图的回调

    public static final int CHECKSTART = 0x004;                 //进入验收模式

    public static final int DOWNLOAD_MAP_SUCCESS = 0x005;       //下载地图成功

    public static final int DOWNLOAD_MAP_FAILED = 0x006;        //下载地图失败

    public static final int DOWNLOAD_YAML_SUCCESS = 0x007;      //下载地图配置文件成功

    public static final int DOWNLOAD_YAML_FAILED = 0x008;       //下载地图配置文件失败

    public static final int DOWNLOAD_POSE_SUCCESS = 0x009;      //下载目标点文件成功

    public static final int DOWNLOAD_POSE_FAILED = 0x010;       //下载目标点文件失败

    public static final int UPLOAD_MAP_SUCCESS = 0x011;         //下载地图成功

    public static final int UPLOAD_MAP_FAILED = 0x012;          //下载地图失败

    public static final int UPLOAD_YAML_SUCCESS = 0x013;        //下载地图配置文件成功

    public static final int UPLOAD_YAML_FAILED = 0x014;         //下载地图配置文件失败

    public static final int UPLOAD_POSE_SUCCESS = 0x015;        //下载目标点文件成功

    public static final int UPLOAD_POSE_FAILED = 0x016;         //下载目标点文件失败

    public static final int ROS_CONNECT_FAILED = 0x017;         //连接ROS服务端失败

    public static final int NAV_CONTINUE = 0x018;               //继续导航

    public static final int NAV_STOP = 0x019;                   //停止导航

    public static final int IP_PING_SUCCESS = 0x020;            //ip ping通

    public static final int SERVICESTOP = 0x021;                //关闭服务

    public static final int IP_PING_FAILED = 0x022;             //ip ping不通

    public static final int JUST_RUN = 0x023;                   //仅限移动控制

    public static final int DELETEMAP = 0x024;                  //删除地图

    public static final int GETVOLTAGE = 0x025;                 //获取到电量百分比

    public static final int NOCHARGING = 0x026;                 //未充电

    public static final int ISCHARGING = 0x027;                 //正在充电

    public static final int ADDPOSEGOALALIASES = 0x028;         //添加点到别名队列

    public static final int CHARGE_NAV_ADDGOALQUEUE = 0x029;    //充电导航,添加目标点

    public static final int CHARGE_NAV_SETCURRENTGOAL = 0x030;  //充电导航,切换当前即将要去的目标点的回调

    public static final int CHARGE_NAV_STOP = 0x031;            //充电导航前,先停止之前的队列

    public static final int SAVEMAP = 0x032;                    //建图时保存地图

    public static final int MUILT_NAV_STOP = 0x033;             //停止导航

    public static final int FINDCHARGEPILE = 0x034;             //正在与充电桩对接

    public static final int CURRENTROBOTPOSE = 0x035;           //获取当前坐标信息

    public static final int MULTI_NAV_SETLOOPMODE = 0x036;      //获取当前坐标信息

    public static final int MULTI_NAV_SETCURRENTGOAL = 0x037;   //多点导航时,切换当前即将要去的目标点的回调

    public static final int NAV_RUN = 0x038;                    //普通的开始导航的回调

    public static final int SINGLE_NAV_FINISH = 0x039;          //单点导航停止并结束任务

    public static final int SINGLE_NAV_ADDGOALQUEUE = 0x040;    //单点导航,添加目标点

    public static final int SINGLE_NAV = 0x041;                 //发起单点导航

    public static final int CHARGE_STOP = 0x042;                //停止充电

    public static final int SINGLE_NAV_FINISHED = 0x043;        //单点导航已经停止

    public static final int SINGLE_NAV_SUCCESS = 0x044;         //单点导航成功

    public static final int SETMAPIMU = 0x045;                  //切换地图

    public static final int SETMAPODOM = 0x046;                  //切换地图

    public static final int GETPROCESSSTATE = 0x047;

    public static final int GETALLGOALALIASES = 0x048;

    public static final int NAV_CANCLED = 0x049;

    public static final int SAVECHARGEPOSE = 0x050;

    public static final int DELSAVEPOSE = 0x051;

    public static final int RECHARGE_NAV = 0x052;

    public static final int INITMAPGOALPOSE = 0x053;

    public static final int RESET_MULTI_NAV = 0x054;

    public static final int DELMAPGOALPOSE = 0x055;

    public static final int SPECIAL_SINGLE_NAV = 0x056;

    public static final int SPECIAL_SINGLE_STOP = 0x057;

    public static final int CHARGE_NAV_GOAL = 0x058;

    public static final int NOTIFY_SPINNER = 0x059;

    public static final int DEL_USELESS_CHARGEPOSE = 0x060;

    public static final int GETGOALSTATEQUEUE = 0x061;

    public static final int SETLOOPMODE = 0x062;

    public static final int SETMAP = 0x063;

    public static final int STOP_FOR_SINGLE_START = 0x064;

    public static final int CONTINUE_NAV_GOAL = 0x065;

    public static final int STOP_FOR_CONTINUE_NAV = 0x066;

    public static final int STOP_FOR_RESET_NAV = 0x067;

    public static final int RESTART_NAVIGATION = 0x068;

    public static final int NAV_SUCCESS = 10000;                //导航成功

    public static final int SERVICESTATING = 10001;             //正在连接服务

    public static final int MULTI_NAV_STOP = 20000;             //停止导航的回调,开始新的多点导航任务前,先停止之前的队列

    public static final int MULTI_NAV_DELGOALQUEUE = 20002;     //多点导航时,删除之前的导航队列的回调

    public static final int SINGLE_NAV_START = 40000;           //单点导航开始

    public static final int SINGLE_NAV_STOP = 40001;            //单点导航去新的目标点时,先停止之前的任务

    public static final int SINGLE_NAV_SETORDERMODE = 40002;    //单点导航,需设置单次导航

    public static final int SINGLE_NAV_DELGOALQUEUE = 40003;    //单点导航开始新任务前,先删除之前的任务

    public static final int SINGLE_NAV_SETCURRENTGOAL = 40005;  //单点导航,切换当前即将要去的目标点的回调

    public static final int CHARGE_NAV_SETORDERMODE = 50001;    //充电导航,需设置单次导航模式

    public static final int CHARGE_NAV_DELGOALQUEUE = 50002;    //充电导航前,需删除之前的队列

    public static final int NAV_FAILED = 70000;                 //导航异常

    public static final int CHARGE_PILE_WRONG = 70001;          //充电桩设置不合理

    public static final int AUTOGMAPPINGIMUSTOP = 90010;        //关闭自动建图服务

    public static final int AUTOGMAPPINGIMUSTART = 90011;       //启动自动建图服务

    public static final int GMAPPINGSTOP = 90007;               //关闭普通/谷歌建图服务

    public static final int NAVIGATIONSTOP = 90006;             //关闭导航服务

    public static final int GETSAVEPOSES = 90003;               //获取已保存的目标点数据

    public static final int SAVEPOSE = 90004;                   //保存单个目标点到目标点文件(不覆盖)

    public static final int SAVEPOSES = 90005;                  //保存目标点集合到目标点文件(覆盖)

}
