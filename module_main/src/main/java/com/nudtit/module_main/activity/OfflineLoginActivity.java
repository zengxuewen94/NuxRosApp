package com.nudtit.module_main.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nudtit.lib_common.utils.Utils;
import com.nudtit.lib_common.widget.WaitDialog;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivityOfflineLoginBinding;
import com.nudtit.module_main.viewmodel.OfflineLoginViewModel;

/**
 * @author :  zengxuewen
 * @date :  2022/8/8
 * @desc :  离线登录
 */
public class OfflineLoginActivity extends BaseActivity {

    // 等待弹窗
    private WaitDialog.Builder mWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding();
    }


    private void binding() {
        ActivityOfflineLoginBinding offlineLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_offline_login);
        OfflineLoginViewModel offlineLoginViewModel = new ViewModelProvider(this).get(OfflineLoginViewModel.class);
        offlineLoginBinding.setLifecycleOwner(this);
        offlineLoginBinding.setOfflineLogin(offlineLoginViewModel);
        offlineLoginViewModel.getJumpToMainActivityVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                onStartActivity(MainActivity.class);
                finish();
            }
        });
        offlineLoginViewModel.getWaitingDialogVo().observe(this, aBoolean -> {
            if (aBoolean) {
                showWaitingDialog();
            } else {
                closeWaitingDialog();
            }
        });
    }

    private void showWaitingDialog() {
        if (null == mWaitDialog) {
            mWaitDialog = new WaitDialog.Builder(this).setMessage(Utils.getString(R.string.offline_login_connecting));
        }
        mWaitDialog.show();
    }

    private void closeWaitingDialog() {
        if (null != mWaitDialog) {
            mWaitDialog.dismiss();
        }

    }

    private void clearData() {
        if (null != mWaitDialog) {
            mWaitDialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        closeWaitingDialog();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        closeWaitingDialog();
        finish();
    }

    @Override
    protected void onDestroy() {
        clearData();
        super.onDestroy();
    }
}
