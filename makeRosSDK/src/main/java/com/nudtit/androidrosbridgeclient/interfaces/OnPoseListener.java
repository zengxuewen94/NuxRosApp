package com.nudtit.androidrosbridgeclient.interfaces;

import com.nudtit.androidrosbridgeclient.ros.message.geometry_msgs.Pose;

public interface OnPoseListener {
    //动作成功完成。
    void finished(Pose pose);

}
