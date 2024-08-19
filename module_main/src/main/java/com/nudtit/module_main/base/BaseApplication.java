package com.nudtit.module_main.base;

import android.content.Intent;
import android.text.TextUtils;


import com.nudtit.lib_common.base.ModuleBaseApplication;
import com.nudtit.lib_common.constant.CommonConstant;
import com.nudtit.lib_common.utils.AppUtil;
import com.nudtit.lib_common.utils.FileUtil;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author :  zengxuewen
 * @date :  2021/6/25
 * @desc :  Application 基类
 */
public class BaseApplication extends ModuleBaseApplication {


    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        initData();
        super.onCreate();
    }

    private void initData() {
        //旧版本
        int oldAppVersion = SharedPreferencesUtils.getInstance(this).getInt(CommonConstant.APP_VERSION, 1);
        //当前app版本
        int newAppVersion = AppUtil.getPackageInfo(this, this.getPackageName()).versionCode;
        //当前app版本大于旧版本，视为软件更新，删除更新文件
        if (newAppVersion > oldAppVersion) {
            FileUtil.deleteFiles(FileUtil.getFilePath(FileUtil.FileName.UPDATE_APK));
            SharedPreferencesUtils.getInstance(this).putInt(CommonConstant.APP_VERSION, newAppVersion);
        }
    }


}
