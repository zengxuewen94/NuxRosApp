package com.nudtit.module_main.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivitySettingBinding;
import com.nudtit.module_main.viewmodel.SettingViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * @author :  zengxuewen
 * @date :  2022/8/17
 * @desc : 设置界面
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        createStatusBarConfig(R.color.white);
    }

    @NonNull
    @NotNull
    @Override
    protected ImmersionBar createStatusBarConfig(int navigationBarColor) {
        return super.createStatusBarConfig(navigationBarColor);
    }


    private void bindView() {
        ActivitySettingBinding settingBinding = bindView(R.layout.activity_setting);
        SettingViewModel settingViewModel =getViewModel(SettingViewModel.class);
        settingBinding.setSettingViewModel(settingViewModel);
        observeLiveDataChange(settingViewModel);
    }

    private void observeLiveDataChange(SettingViewModel settingViewModel){
        settingViewModel.getActivityFinishVo().observe(this, aBoolean -> finish());
        settingViewModel.getJumpRunControlVo().observe(this, aBoolean -> onStartActivity(RosSlamControlActivity.class));
    }
}
