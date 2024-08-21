package com.eaibot.running.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eaibot.running.BuildConfig;
import com.eaibot.running.R;
import com.eaibot.running.databinding.ActivityLoginBinding;
import com.eaibot.running.view.ToastWindowView;
import com.eaibot.running.viewmodel.LoginViewModel;
import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.lib_common.utils.BrandUtil;
import com.nudtit.lib_common.utils.IntentUtils;
import com.nudtit.lib_common.utils.PermissionUtil;
import com.nudtit.lib_common.widget.ConfirmDialog;

/**
 * @author Yist
 * @date 2019/7/9
 */

public class LoginActivity extends BaseActivity {
    private static final int PERMISSION_RESULT_CODE = 1100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        ActivityLoginBinding loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginBinding.setLoginViewModel(loginViewModel);
        getLifecycle().addObserver(loginViewModel);
        loginViewModel.getGotoConnectActivityVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(LoginActivity.this, ConnectActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginViewModel.getChangeLanguageVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        initView();
        checkAndRequestPermission();
        ToastWindowView toastWindowView=new ToastWindowView(this);

    }

    private void initView() {
        TextView changeLanguage = (TextView) findViewById(R.id.changeLanguage);
        changeLanguage.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

    }


    /**
     * 申请后台打开应用的权限
     * 不同厂商的权限名称不一致,例如小米:后台弹出界面; 华为:悬浮窗; 其他:锁屏界面弹框控制等.
     */
    private void checkAndRequestPermission() {
        if (!PermissionUtil.hasPermission(this)&& !BuildConfig.LOG_DEBUG) {
            ConfirmDialog.Builder confirmDialog = new ConfirmDialog.Builder(this);
            confirmDialog.setTitle("提示").setContent("确保应用在使用过程中显示提示信息，请打开悬浮窗权限");
            confirmDialog.setListener(new ConfirmDialog.OnListener() {
                @Override
                public void onConfirm(BaseNewDialog baseDialog) {
                    //            //vivo的后台权限界面跳转
                    if (BrandUtil.isBrandVivo()) {
                        Intent localIntent;
                        if (((Build.MODEL.contains("Y85")) && (!Build.MODEL.contains("Y85A")))
                                || (Build.MODEL.contains("vivo Y53L"))) {
                            localIntent = new Intent();
                            localIntent.setClassName("com.vivo.permissionmanager",
                                    "com.vivo.permissionmanager.activity.PurviewTabActivity");
                            localIntent.putExtra("packagename", getPackageName());
                            localIntent.putExtra("tabId", "1");
                            IntentUtils.safeStartActivity(LoginActivity.this, localIntent);
                        } else {
                            localIntent = new Intent();
                            localIntent.setClassName("com.vivo.permissionmanager",
                                    "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
                            localIntent.setAction("secure.intent.action.softPermissionDetail");
                            localIntent.putExtra("packagename", getPackageName());
                            IntentUtils.safeStartActivity(LoginActivity.this, localIntent);
                        }
                        return;
                    } else if (BrandUtil.isBrandXiaoMi()) {
                        final Dialog dialog = new Dialog(LoginActivity.this, R.style.logoutDialogStyle);
                        dialog.setContentView(R.layout.app_show_tip_dialog_confirm);
                        TextView tvMessage = dialog.findViewById(R.id.tv_message);
                        Button btnOk = dialog.findViewById(R.id.btn_negative);
                        tvMessage.setText("确保应用在使用过程中显示提示信息，请打开悬浮窗权限");
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.show();
                        //弹出一次提示后,应用未杀死前不再进行提示了
                        PermissionUtil.mHasPermissionOrHasHinted = true;
                        return;
                    }
                    //其他厂商
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, PERMISSION_RESULT_CODE);
                    }
                }

                @Override
                public void onCancel(BaseNewDialog dialog) {
                    dialog.dismiss();
                }
            });
            confirmDialog.show();


        } else {
            //已经有权限
            PermissionUtil.mHasPermissionOrHasHinted = true;
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_RESULT_CODE) {
            if (PermissionUtil.hasPermission(this)) {
                PermissionUtil.mHasPermissionOrHasHinted = true;
            } else {
                PermissionUtil.mHasPermissionOrHasHinted = false;

            }
        }
    }
}
