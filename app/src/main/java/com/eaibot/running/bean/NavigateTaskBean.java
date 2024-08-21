package com.eaibot.running.bean;

/**
 * @author :  zengxuewen
 * @date :  2024/5/15
 * @desc :  导航任务实体类
 */
public class NavigateTaskBean {

    String taskName;//任务名称
    String points;//位置点，多个
    int count;//任务次数

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
