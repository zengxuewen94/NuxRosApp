package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.nudtit.control_common.base.BaseViewModel;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseConstant;
import com.nudtit.module_main.manager.RosDeviceManager;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  离线登录ViewModel
 */
public class OfflineLoginViewModel extends BaseViewModel {
    public String TAG = OfflineLoginViewModel.class.getSimpleName();


    public MutableLiveData<Boolean> jumpToMainActivityVo=new MutableLiveData<>();
    //等待加载框
    public MutableLiveData<Boolean> waitingDialogVo=new MutableLiveData<>();
    public MutableLiveData<Boolean> getJumpToMainActivityVo() {
        return jumpToMainActivityVo;
    }

    public MutableLiveData<Boolean> getWaitingDialogVo() {
        return waitingDialogVo;
    }

    // 连接ip
    public ObservableField<String> obIp = new ObservableField<>();
    // 连接端口
    public ObservableField<String> obPort = new ObservableField<>();

    public OfflineLoginViewModel(@NonNull @NotNull Application application) {
        super(application);
        initData();
    }


    private void initData() {
        obIp.set(BaseConstant.CONNECT_IP);
        obPort.set(BaseConstant.CONNECT_PORT);
    }


    /**
     * 点击事件-登录
     */
    public void onClickLogin() {
        if (TextUtils.isEmpty(obIp.get())) {
            ToastUtil.showLong(Utils.getString(R.string.offline_login_input_ip));
            return;
        }
        if (TextUtils.isEmpty(obPort.get())) {
            ToastUtil.showLong(Utils.getString(R.string.offline_login_input_port));
            return;
        }
        //这里需要拼接连接底盘的uri "ws://192.168.31.119:9090"
        StringBuffer uri = new StringBuffer();
        uri.append(BaseConstant.CONNECT_HEADER).append(obIp.get())
                .append(BaseConstant.CONNECT_SPLIT).append(obPort.get());

        waitingDialogVo.postValue(true);
        Observable.create(e -> {
            boolean success = RosDeviceManager.INSTANCE.connect(uri.toString());
            if (success){
                RosDeviceManager.INSTANCE.subscribeGetMapDataTopic();
            }
            e.onNext(success);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull Object o) {
                boolean success= (boolean) o;
                if (success) {
                    LogUtils.d(TAG,  Utils.getString(R.string.offline_login_connect_success));
                    waitingDialogVo.postValue(false);
                    ToastUtil.showShort(Utils.getString(R.string.offline_login_connect_success));
                    jumpToMainActivityVo.postValue(true);
                }else {
                    LogUtils.d(TAG, Utils.getString(R.string.offline_login_connect_fail));
                    ToastUtil.showShort(Utils.getString(R.string.offline_login_connect_fail));
                    waitingDialogVo.postValue(false);
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


}
