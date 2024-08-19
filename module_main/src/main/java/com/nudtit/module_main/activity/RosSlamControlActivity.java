package com.nudtit.module_main.activity;

import android.os.Bundle;

import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivityRosSlamControlBinding;
import com.nudtit.module_main.viewmodel.RosSlamControlViewModel;

/**
 * @author :  zengxuewen
 * @date :  2022/8/18
 * @desc :  运动控制页
 */
public class RosSlamControlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bingView();
    }

    private void bingView() {
        ActivityRosSlamControlBinding rosSlamControlBinding = bindView(R.layout.activity_ros_slam_control);
        RosSlamControlViewModel slamControlViewModel = getViewModel(RosSlamControlViewModel.class);
        rosSlamControlBinding.setSlamControlViewModel(slamControlViewModel);
        getLifecycle().addObserver(slamControlViewModel);
        slamControlViewModel.getActivityFinishVo().observe(this, aBoolean -> finish());
    }

}
