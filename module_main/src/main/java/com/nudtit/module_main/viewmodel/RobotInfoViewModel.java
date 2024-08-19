package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.nudtit.control_common.base.BaseViewModel;
import com.nudtit.control_common.uitl.StringUtils;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseConstant;
import com.nudtit.module_main.bean.LocalPose;
import com.nudtit.module_main.manager.RosDeviceManager;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

/**
 * @author :  zengxuewen
 * @date :  2022/8/16
 * @desc :  设备信息
 */
public class RobotInfoViewModel extends BaseViewModel {

    public ObservableField<String> obSn = new ObservableField<>();
    public ObservableField<String> obFirmware = new ObservableField<>();
    public ObservableField<String> obHardware = new ObservableField<>();
    public ObservableField<String> obSystem = new ObservableField<>();
    public ObservableField<String> obIp = new ObservableField<>();
    public ObservableField<String> obPositioningQuality = new ObservableField<>();
    public ObservableField<String> obBatteryPower = new ObservableField<>();
    public ObservableField<String> obChargeStatus = new ObservableField<>();
    public ObservableField<String> obMapSize = new ObservableField<>();
    public ObservableField<String> obRobotPose = new ObservableField<>();
    public ObservableField<String> obChargingPose = new ObservableField<>();

    public RobotInfoViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        initTitle(Utils.getString(R.string.robot_info_title), R.color.white, R.drawable.ic_left_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (R.id.iv_view_setting_back == v.getId()) {
                    activityFinishVo.postValue(true);
                }
            }
        });
        setTitleBg(R.color.blue_color);
        if (!TextUtils.isEmpty(RosDeviceManager.INSTANCE.ip)) {
            obIp.set(RosDeviceManager.INSTANCE.ip.split(BaseConstant.CONNECT_HEADER)[1]);
        }
        int powerStatus = RosDeviceManager.INSTANCE.getPowerSupplyStatus();
        String chargeStatus = "未知";
        switch (powerStatus) {
            case 1:
                chargeStatus = "充电中";
                break;
            case 2:
                chargeStatus = "放电中";
                break;
            case 3:
                chargeStatus = "未充电";
                break;
            case 4:
                chargeStatus = "已充满";
                break;
            default:
                break;
        }
        obChargeStatus.set(chargeStatus);
        obBatteryPower.set(String.valueOf(RosDeviceManager.INSTANCE.getBatteryPercentage()));
        LocalPose pose = RosDeviceManager.INSTANCE.getPose();
        if (null != pose) {
            StringBuilder robotPose = new StringBuilder();
            LogUtils.d("Pose", pose.getX() + "--" + pose.getY() + "--" + pose.getZ());
            robotPose.append("[").append(StringUtils.decimal2(pose.getX())).append("，")
                    .append(StringUtils.decimal2(pose.getY())).append("，")
                    .append(StringUtils.decimal2(pose.getZ())).append("]");
            obRobotPose.set(robotPose.toString());
        }


    }



}
