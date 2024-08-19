package com.nudtit.module_main.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.nudtit.lib_common.base.ModuleBaseActivity;

/**
 * @author :  zengxuewen
 * @date :  2022/8/12
 * @desc :  activity 基类
 */
public class BaseActivity extends ModuleBaseActivity {
    protected static final String BUNDLE_DATA = "bundleData";
    /** 状态栏沉浸 */
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createStatusBarConfig();

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

        }
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig(int navigationBarColor) {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
                // 指定导航栏背景颜色
                .navigationBarColor(navigationBarColor)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }


    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return createStatusBarConfig(android.R.color.white);
    }

    /**
     * 状态栏字体深色模式
     */
    protected boolean isStatusBarDarkFont() {
        return false;
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }


    /**
     * activity间跳转
     *
     * @param mClass 要跳转的activity
     */
    protected void onStartActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    /**
     * activity间跳转
     *
     * @param mClass 要跳转的activity
     * @param bundle 携带的参数
     */
    protected void onStartActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        intent.putExtra(BUNDLE_DATA, bundle);
        startActivity(intent);
    }

    /**
     * 绑定布局
     *
     * @param layoutId
     * @param <T>
     * @return databinding
     */
    protected <T extends ViewDataBinding> T bindView(int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    /**
     * @param viewModel
     * @param <T>
     * @return ViewModel 对象
     */
    protected <T extends ViewModel> T getViewModel(Class<T> viewModel) {
        return new ViewModelProvider(this).get(viewModel);
    }
}
