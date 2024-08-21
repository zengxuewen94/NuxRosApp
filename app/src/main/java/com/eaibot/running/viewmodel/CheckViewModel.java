package com.eaibot.running.viewmodel;


import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.eaibot.running.R;
import com.eaibot.running.constants.HandlerMsgCode;
import com.eaibot.running.constants.NumberCode;
import com.eaibot.running.event.GoHomeEvent;
import com.eaibot.running.event.RobotInfoEvent;
import com.eaibot.running.manger.BaseDataManager;
import com.eaibot.running.manger.RosDeviceManager;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.lib_common.manager.ThreadPoolManager;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.StringUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.slam.bean.LocalPose;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

/**
 * @author :  zengxuewen
 * @date :  2022/9/7
 * @desc :  检查页面
 */
public class CheckViewModel extends BaseNavigateViewModel {
    // 直行按钮背景
    public ObservableField<Drawable> obBtnDirectMoveBg = new ObservableField<>();
    // 360°旋转按钮背景
    public ObservableField<Drawable> obBtnRotateBg = new ObservableField<>();
    // 充电按钮背景
    public ObservableField<Drawable> obBtnChargeBg = new ObservableField<>();
    //传感器按钮背景
    public ObservableField<Drawable> obBtnChangeLaserScanBg = new ObservableField<>();


    // 设备连接状态
    public ObservableField<String> obRobotState = new ObservableField<>();
    // 设备连接状态
    public ObservableField<String> obImuText = new ObservableField<>();
    //电量
    public ObservableField<String> obVoltageText = new ObservableField<>();
    //机器位姿
    public ObservableField<String> obRobotPose = new ObservableField<>();

    // 急停按钮背景
    public ObservableField<Integer> obStopStatusBg = new ObservableField<>();
    //电量文字颜色
    public ObservableField<Integer> obVoltageTextColor = new ObservableField<>();
    //充电状态控件显示
    public ObservableField<Integer> obChargeShow = new ObservableField<>();
    //超声波文字颜色
    public ObservableField<Integer> obSonarTitleColor = new ObservableField<>();
    // 超声波显示
    public ObservableField<Integer> obLlDistanceShow = new ObservableField<>(View.GONE);
    // 超声波提示信息显示
    public ObservableField<Integer> obLlHintSonarShow = new ObservableField<>(View.GONE);
    //手柄显示
    public ObservableField<Boolean> obHandleViewShow = new ObservableField<>();

    // 是否在直行
    private boolean isGo = false;
    // 是否在旋转
    private boolean isTurn;
    // 是否回去充电
    private boolean isRecharging;
    private int walkType;
    private int chargeType;
    private int laserScan;
    //保留2位小数
    private String imuAngle = "-/-";
    private CheckHandler handler;
    // 直行倒计时
    private long goWalkTime = 1;

    public CheckViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onNavigateError() {
        if (1 == walkType || 3 == walkType) {
            ToastUtil.showShort(Utils.getString(R.string.navigate_error));
        }
    }

    @Override
    protected void onNavigateSuccess() {
        if (walkType == 3) {
            obBtnChargeBg.set(getDrawable(R.drawable.btn_recharge2));
            ToastUtil.showShort(Utils.getString(R.string.charging_success));
            isRecharging = false;
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

        handler = new CheckHandler(Looper.getMainLooper(), new WeakReference<>(this));
        obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_start));
        obBtnRotateBg.set(getDrawable(R.drawable.button_reset_selectet_drawline));
        obBtnChangeLaserScanBg.set(getDrawable(R.drawable.btn_to_bottom_laserscan));
        obBtnChargeBg.set(getDrawable(R.drawable.btn_recharge2));
        obRobotState.set(Utils.getString(R.string.deviceConnected));
        obChargeShow.set(View.GONE);
        obLlDistanceShow.set(View.GONE);
        obLlHintSonarShow.set(View.GONE);
        obSonarTitleColor.set(Color.parseColor("#0dca00"));
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (chargeType == 1 || chargeType == NumberCode.CHARGE_WAY_ALL) {
//                    isRecharging = true;
//                    obBtnChargeBg.set(getDrawable(R.drawable.button_close_002));
//                }
//            }
//        }, 8000);
        obHandleViewShow.set(BaseDataManager.INSTANCE.getHandleViewShow());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    /**
     * 点击事件-直行
     */
    public void onClickDirectMove() {
        if (!isGo) {
            if (walkType == NumberCode.TURN360CODE) {
                isTurn = false;
                obBtnRotateBg.set(getDrawable(R.drawable.button_reset_selectet_drawline));
                RosDeviceManager.INSTANCE.actionCancel();
            }
            ToastUtil.showShort(Utils.getString(R.string.doOneMeterGo));
            walkType = 1;
            obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_stop));
            isGo = true;
            // 直行1米，连续调用向前移动方法，持续时间3s
            ThreadPoolManager.getInstance().execute(() -> {
                while (isGo && goWalkTime % (3 * 10) != 0) {
                    try {
                        RosDeviceManager.INSTANCE.moveBy(MoveDirection.FORWARD);
                        Thread.sleep(100);
                        goWalkTime++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                goWalkTime = 1;
                showSomeInfo();
            });
        } else {
            walkType = 0;
            obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_start));
            RosDeviceManager.INSTANCE.actionCancel();
            isGo = false;
        }

    }

    /**
     * 点击事件-旋转角度
     */
    public void onClickRotate() {
        if (!isTurn) {
            if (walkType == 1) {
                isGo = false;
                obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_start));
                RosDeviceManager.INSTANCE.actionCancel();
            }
            walkType = 2;
            ToastUtil.showShort(Utils.getString(R.string.do360DegreeTun));
            obBtnRotateBg.set(getDrawable(R.drawable.button_close_002));
            // 此方法无效
            //RosDeviceManager.INSTANCE.rotateTo(60);
            isTurn = true;
        } else {
            walkType = 0;
            obBtnRotateBg.set(getDrawable(R.drawable.button_reset_selectet_drawline));
            RosDeviceManager.INSTANCE.actionCancel();
            showSomeInfo();
            isTurn = false;
        }
    }


    /**
     * 点击事件-充电
     */
    public void onClickCharge() {
        if (!isRecharging) {
            if (1 != RosDeviceManager.INSTANCE.getPowerSupplyStatus()) {
                if (walkType == 1) {
                    RosDeviceManager.INSTANCE.actionCancel();
                    obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_start));
                    isGo = false;
                }
                ToastUtil.showShort(Utils.getString(R.string.goCharge));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d("RosService", "GoHome");
                        EventBus.getDefault().post(new GoHomeEvent());
                    }
                }, walkType == 1 ? 100 : 0);

                obBtnChargeBg.set(getDrawable(R.drawable.button_close_002));
                isRecharging = true;
                walkType = 3;
            }else {
                ToastUtil.showShort("正在充电中");
            }
        } else {
            ToastUtil.showShort(Utils.getString(R.string.stopCharging));
            RosDeviceManager.INSTANCE.cancelGoHome();
            obBtnChargeBg.set(getDrawable(R.drawable.btn_recharge2));
            isRecharging = false;
        }
    }


    /**
     * 激光层显示
     */
    public void onClickChangeLaserScan() {
        if (laserScan == 0) {
            ToastUtil.showShort(Utils.getString(R.string.changeToTheDownLidar));
            obBtnChangeLaserScanBg.set(getDrawable(R.drawable.btn_to_top_laserscan));
            //scanLayer.setIsView(false);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //scan2Layer.setIsView(true);
                    laserScan = 1;
                }
            }, 1000);
        } else {
            ToastUtil.showShort(Utils.getString(R.string.changeToTheUpLidar));
            obBtnChangeLaserScanBg.set(getDrawable(R.drawable.btn_to_bottom_laserscan));
            //scan2Layer.setIsView(false);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //scanLayer.setIsView(true);
                    laserScan = 0;
                }
            }, 1000);
        }
    }


    /**
     * 陀螺仪
     */
    private void showImuInfo() {
        obImuText.set(Utils.getString(R.string.imuAngle) + imuAngle + " rad]");
    }

    /**
     * 充电状态
     *
     * @param msg
     */
    private void showPowerSupplyStatus(Message msg) {
        //0 未知;1 充电中;2 放电中;3 没有充电;4 电池电量已满
        chargeType = (int) msg.obj;
        switch (chargeType) {
            case 1:
                obVoltageTextColor.set(ContextCompat.getColor(Utils.getContext(), R.color.green_color));
                break;
            case 2:
            case 3:
                obVoltageTextColor.set(ContextCompat.getColor(Utils.getContext(), R.color.gray_color));
                break;
            default:
                break;
        }
    }

    /**
     * 急停按钮状态
     *
     * @param msg
     */
    private void showStopStatus(Message msg) {
        if ((int) msg.obj == 0) {
            obStopStatusBg.set(R.drawable.stop_greenicon);
        } else {
            obStopStatusBg.set(R.drawable.stop_redicon);
        }
    }


    /**
     * 显示一些数据
     */
    private void showSomeInfo() {
        if (walkType == 1) {
            ToastUtil.showShort(Utils.getString(R.string.oneMeterRunOver));
            obBtnDirectMoveBg.set(getDrawable(R.drawable.btn_start));
            isGo = false;
        } else if (walkType == NumberCode.TURN360CODE) {
            ToastUtil.showShort(Utils.getString(R.string.degree360RotationOver));
            obBtnRotateBg.set(getDrawable(R.drawable.button_reset_selectet_drawline));
            isTurn = false;
        } else if (walkType == 0) {
            ToastUtil.showShort(Utils.getString(R.string.terminatedTheAction));
        }
    }


    //显示/更新电量
    public void showBatteryPower(Message msg) {
        obVoltageText.set((int) msg.obj + "%");
    }


    /**
     * 显示机器当前位姿点坐标
     *
     * @param pose 位姿点坐标
     */
    public void showRobotPose(LocalPose pose) {
        if (null != pose) {
            StringBuilder robotPose = new StringBuilder();
            robotPose.append("[").append(StringUtils.decimal2(pose.getX())).append("，")
                    .append(StringUtils.decimal2(pose.getY())).append("，")
                    .append(StringUtils.decimal2(pose.getYaw())).append("]");
            obRobotPose.set(robotPose.toString());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotInfo(RobotInfoEvent event) {
        if (null != event && null != handler) {
            handler.obtainMessage(10000, event.getPowerSupplyStatus()).sendToTarget();
            handler.obtainMessage(5111, event.getBatteryPercentage()).sendToTarget();
            handler.obtainMessage(5113, event.getButtonStatus()).sendToTarget();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotPose(LocalPose localPose) {
        if (null != localPose && null != handler) {
            handler.obtainMessage(HandlerMsgCode.CURRENTROBOTPOSE, localPose).sendToTarget();
        }
    }

    /**
     * 电量信息提示
     */
    public View.OnTouchListener onChargeMarkTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    obChargeShow.set(View.VISIBLE);
                    break;
                case MotionEvent.ACTION_UP:
                    obChargeShow.set(View.GONE);
                    break;
                default:
                    break;
            }
            return true;

        }
    };

    /**
     * 超声波信息显示
     */
    public View.OnTouchListener sonarTitleTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    obSonarTitleColor.set(Color.parseColor("#043000"));
                    if (obLlDistanceShow.get() == View.VISIBLE) {
                        obLlDistanceShow.set(View.GONE);
                    } else {
                        obLlDistanceShow.set(View.VISIBLE);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    obSonarTitleColor.set(Color.parseColor("#0dca00"));
                    break;
                default:
                    break;
            }
            return true;
        }
    };


    /**
     * 超声波信息提示按钮监听
     */
    public View.OnTouchListener showHintonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    obLlHintSonarShow.set(View.VISIBLE);
                    break;
                case MotionEvent.ACTION_UP:
                    obLlHintSonarShow.set(View.GONE);
                    break;
                default:
                    break;
            }
            return true;
        }
    };


    //处理消息的handler
    private static class CheckHandler extends Handler {
        CheckViewModel checkViewModel;

        public CheckHandler(Looper looper, WeakReference<CheckViewModel> weakReference) {
            super(looper);
            checkViewModel = weakReference.get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (checkViewModel == null) {
                return;
            }
            switch (msg.what) {
                case 10000://充电模式
                    checkViewModel.showPowerSupplyStatus(msg);
                    break;
                //急停开关
                case 5113:
                    checkViewModel.showStopStatus(msg);
                    break;
                //电量显示
                case 5111:
                    checkViewModel.showBatteryPower(msg);
                    break;
                case 5555:
                    if (msg.obj != null) {
                        //checkViewModel.checkSonar((Float) msg.obj, msg.arg1);
                    }
                    break;
                case 5556:
                    checkViewModel.showSomeInfo();
                    break;
                case 20000:
                    checkViewModel.showImuInfo();
                    break;
                case HandlerMsgCode.CURRENTROBOTPOSE://当前位姿
                    checkViewModel.showRobotPose((LocalPose) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }


}
