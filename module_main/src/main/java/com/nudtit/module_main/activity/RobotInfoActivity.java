package com.nudtit.module_main.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivityRobotInfoBinding;
import com.nudtit.module_main.viewmodel.RobotInfoViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * @author :  zengxuewen
 * @date :  2022/8/16
 * @desc :  设备信息
 */
public class RobotInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding();
        createStatusBarConfig(R.color.white);
    }

    @NonNull
    @NotNull
    @Override
    protected ImmersionBar createStatusBarConfig(int navigationBarColor) {
        return super.createStatusBarConfig(navigationBarColor);
    }

    private void binding() {
        ActivityRobotInfoBinding robotInfoBinding=bindView(R.layout.activity_robot_info);
        RobotInfoViewModel robotInfoViewModel=getViewModel(RobotInfoViewModel.class);
        robotInfoBinding.setRobotInfoViewModel(robotInfoViewModel);
        getLifecycle().addObserver(robotInfoViewModel);
        robotInfoViewModel.getActivityFinishVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
    }
}
