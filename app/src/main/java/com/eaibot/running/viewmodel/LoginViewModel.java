package com.eaibot.running.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.eaibot.running.R;
import com.eaibot.running.base.BaseViewModel;
import com.eaibot.running.constants.LoginUser;
import com.eaibot.running.constants.TimeStamps;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;

/**
 * @author :  zengxuewen
 * @date :  2022/9/8
 * @desc :  登录页
 */
public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> gotoConnectActivityVo = new MutableLiveData<>();
    public MutableLiveData<Boolean> changeLanguageVo = new MutableLiveData<>();

    public MutableLiveData<Boolean> getGotoConnectActivityVo() {
        return gotoConnectActivityVo;
    }

    public MutableLiveData<Boolean> getChangeLanguageVo() {
        return changeLanguageVo;
    }

    public ObservableField<String> obUserName = new ObservableField<>();
    public ObservableField<String> obPassword = new ObservableField<>();
    public ObservableField<String> obLanguageText = new ObservableField<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        obUserName.set("admin");
        obPassword.set("admin");
        int language = SharedPreferencesUtils.getInstance(Utils.getContext()).getInt("language", 0);
        if (language == 0) {
            obLanguageText.set("English");
        } else {
            obLanguageText.set("中文");
        }
    }

    /**
     * 登录
     */
    public void onLogin() {
        ToastUtil.showShort(Utils.getString(R.string.loggingIn));
        if (LoginUser.getAllUsers().contains(obUserName.get() + "," + obPassword.get())) {
            ToastUtil.showShort(Utils.getString(R.string.loginSuccessfully));
            gotoConnectActivityVo.postValue(true);
        } else {
            ToastUtil.showShort(Utils.getString(R.string.userNameOrPasswordError));
        }
    }


    /**
     * 切换语言
     */
    public void changeLanguage() {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp - TimeStamps.lastChangeTime < 5000) {
            ToastUtil.showShort(Utils.getString(R.string.dontChangeFrequently));
            return;
        }
        TimeStamps.lastChangeTime = currentTimeStamp;
        if ("中文".equals(obLanguageText.get())) {
            obLanguageText.set("English");
            SharedPreferencesUtils.getInstance(Utils.getContext()).putInt("language", 0);
        } else {
            obLanguageText.set("中文");
            SharedPreferencesUtils.getInstance(Utils.getContext()).putInt("language", 1);
        }
        changeLanguageVo.postValue(true);
    }

}
