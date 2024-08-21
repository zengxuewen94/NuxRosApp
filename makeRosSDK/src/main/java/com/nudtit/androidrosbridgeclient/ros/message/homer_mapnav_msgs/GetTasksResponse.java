package com.nudtit.androidrosbridgeclient.ros.message.homer_mapnav_msgs;
import com.nudtit.androidrosbridgeclient.ros.message.Message;
import com.nudtit.androidrosbridgeclient.ros.message.MessageType;
@MessageType(string = "homer_mapnav_msgs/GetTasks")
public class GetTasksResponse extends Message {
    public NavTaskList task_list;//获取任务列表不需要传值
}
