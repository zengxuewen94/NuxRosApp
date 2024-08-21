package com.eaibot.running.viewmodel;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.eaibot.running.R;
import com.eaibot.running.bean.ShowAddPointDialogVo;
import com.eaibot.running.constants.HandlerMsgCode;
import com.eaibot.running.db.SlamLocationDBManager;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.event.GoHomeEvent;
import com.eaibot.running.event.MoveToEvent;
import com.eaibot.running.event.RobotInfoEvent;
import com.eaibot.running.listener.OnPointListener;
import com.eaibot.running.manger.BaseDataManager;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.manger.ThreadPoolManager;
import com.eaibot.running.widget.RockerView;
import com.eaibot.running.widget.RoundMenuView;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.StringUtils;

import com.nudtit.lib_common.utils.Utils;
import com.nudtit.slam.bean.LocalPose;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :  zengxuewen
 * @date :  2022/9/6
 * @desc :  主页viewmodel
 */
public class MainViewModel extends BaseNavigateViewModel implements LifecycleObserver {

    // 头部文字
    public ObservableField<String> obTitle = new ObservableField<>();
    // 机器位姿
    public ObservableField<String> obRobotPose = new ObservableField<>();
    public ObservableField<Drawable> obMoveOrClickResource = new ObservableField<>();
    //电量
    public ObservableField<String> obBatteryValue = new ObservableField<>();
    //手柄显示
    public ObservableField<Boolean> obHandleViewShow=new ObservableField<>();


    //充电状态
    private MutableLiveData<Boolean> chargeStatusVo = new MutableLiveData<>();
    //电量
    private MutableLiveData<Integer> batteryValueVo = new MutableLiveData<>();

    private MutableLiveData<ShowAddPointDialogVo> showAddPointDialogVo = new MutableLiveData<>();
    private MutableLiveData<Integer> requestPer=new MutableLiveData<>();
    public MutableLiveData<Boolean> getChargeStatusVo() {
        return chargeStatusVo;
    }

    public MutableLiveData<Integer> getBatteryValueVo() {
        return batteryValueVo;
    }

    public MutableLiveData<ShowAddPointDialogVo> getShowAddPointDialogVo() {
        return showAddPointDialogVo;
    }

    public MutableLiveData<Integer> getRequestPer() {
        return requestPer;
    }

    private boolean isMapAddPose = true;                            //点击地图是否为设置点
    private boolean isMapClick = false;                              //点击地图是否为移动/缩放地图
    private int navType = 0;    //0:停止  1:多点导航  2:单点导航  3:充电导航
    public static List<SlamLocationBean> markPoseList = new ArrayList<>();  //导航点集合
    public MainHandler mainHandler;

    private static final int TIME_CONTROL_MOVE_INTERVAL = 80;
    private static final int TIME_CONTROL_ROTATE_INTERVAL = 100;
    private boolean isStart;
    private RockerView.Direction mDirection;
    private int mClickCount;
    private MoveRunnable mMoveRunnable;
    public MainViewModel(@NonNull Application application) {
        super(application);

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        initData();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {

        if (null != mainHandler) {
            mainHandler.removeCallbacksAndMessages(null);
            mainHandler = null;
        }
    }

    public void initData() {
        obTitle.set(Utils.getString(R.string.robotIsConnected));
        mainHandler = new MainHandler(Looper.getMainLooper(), new WeakReference<>(this));
        obMoveOrClickResource.set(ContextCompat.getDrawable(Utils.getContext(), R.drawable.unlocked));
        obHandleViewShow.set(BaseDataManager.INSTANCE.getHandleViewShow());
    }


    /**
     * 点击事件-添加位置点
     */
    public void onClickAddCurrentPoseGoal(int type) {
        requestPer.postValue(type);

    }





    /**
     * 查询位置点列表
     *
     * @param type
     */
    public void queryLocationList(int type) {
        Observable.create(subscriber -> {
            //根据地图名称查询位置点列表
            LogUtils.d("地图",BaseDataManager.INSTANCE.getLoadMapName());
            List<SlamLocationBean> slamLocationBeanList = SlamLocationDBManager.getInstance(Utils.getContext()).queryLocationListByMap(BaseDataManager.INSTANCE.getLoadMapName());
            subscriber.onNext(slamLocationBeanList);
            subscriber.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<Object>() {

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object data) {
                        List<SlamLocationBean> mList = (List<SlamLocationBean>) data;
                        BaseDataManager.INSTANCE.setSlamLocationList(mList);
                        showAddPointDialogVo.postValue(new ShowAddPointDialogVo(type, mList));
                    }
                });
    }





    /**
     * 位置点列表
     */
    public void onClickPoseList(int type) {

        requestPer.postValue(type);


    }

    /**
     * 开始导航
     */
    public void onClickStartNav() {
        // TODO 配合地图使用

        if (markPoseList.size() > 0) {
            moveTo(markPoseList.get(0));
        } else {
            obTitle.set(Utils.getString(R.string.noPoseToGo));
            showShortToast(Utils.getString(R.string.noPoseToGo));
        }

        //RosDeviceManager.INSTANCE.moveTo(0f, 0f, 0f, onNavigateListener);
    }

    /**
     * 停止导航
     */
    public void onClickStopNav() {
        showShortToast(Utils.getString(R.string.stoppingNav));
        obTitle.set(Utils.getString(R.string.stoppingNav));
        RosDeviceManager.INSTANCE.actionCancel();
    }




    /**
     * 回充
     */
    public void onClickGoHome() {
        if (RosDeviceManager.INSTANCE.getPowerSupplyStatus() == 1) {
            showShortToast("正在充电中");
            return;
        }
        navType = 3;
        obTitle.set(Utils.getString(R.string.goForCharge));
        showShortToast(Utils.getString(R.string.goForCharge));
        if (null != markPoseList) {
            markPoseList.clear();
        }
        EventBus.getDefault().post(new GoHomeEvent());

    }


    /**
     * 取消回充
     */
    public void onClickCancelGoHome() {
        showShortToast(Utils.getString(R.string.charge_stopped));
        obTitle.set(Utils.getString(R.string.charge_stopped));
        navType = 0;
        RosDeviceManager.INSTANCE.cancelGoHome();
    }


    private void showPose(LocalPose pose) {
        if (null != pose) {
            StringBuilder robotPose = new StringBuilder();
            robotPose.append("[").append(StringUtils.decimal2(pose.getX())).append("，")
                    .append(StringUtils.decimal2(pose.getY())).append("，")
                    .append(StringUtils.decimal2(pose.getZ())).append("]");
            obRobotPose.set(robotPose.toString());
        }
    }


    /**
     * 机器人信息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotInfo(RobotInfoEvent event) {
        if (null != event && null != mainHandler) {
            mainHandler.obtainMessage(HandlerMsgCode.GETVOLTAGE, event.getBatteryPercentage()).sendToTarget();
            mainHandler.obtainMessage(HandlerMsgCode.ISCHARGING, event.getPowerSupplyStatus()).sendToTarget();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotPose(LocalPose robotPose) {
        if (null != robotPose && null != mainHandler) {
            mainHandler.obtainMessage(HandlerMsgCode.CURRENTROBOTPOSE, robotPose).sendToTarget();
        }
    }



    public View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (markPoseList.size() > 0) {
                showShortToast(Utils.getString(R.string.resetNavGo));
                obTitle.set(Utils.getString(R.string.resetNavGo));
                if (navType == 2) {
                    moveTo(markPoseList.get(0));
                }
                //HttpRequestTool.delGoalQueue(ip,handler,HandlerMsgCode.MULTI_NAV_DELGOALQUEUE);
            } else {
                showShortToast(Utils.getString(R.string.noPoseToGo));
            }
            return false;
        }
    };


    /**
     * 导航成功
     */
    @Override
    protected void onNavigateSuccess() {
        if (navType == 3) {
            //if (RosDeviceManager.INSTANCE.getPowerSupplyStatus() == 1) {
            showShortToast("充电成功");
            obTitle.set("充电成功");
            //}
        } else if (navType == 2) {
            obTitle.set("导航成功");
            showShortToast("导航成功");
            if (null != markPoseList && !markPoseList.isEmpty()) {
                markPoseList.clear();
            }
        }
    }

    /**
     * 导航失败
     */
    @Override
    protected void onNavigateError() {
        if (navType == 3) {
            showShortToast("充电失败");
            obTitle.set("充电失败");
        } else if (navType == 2) {
            showShortToast("导航失败了");
            obTitle.set("导航失败了");
        }

    }

    /**
     * 导航
     * @param mSlamLocationBean
     */
    private void moveTo(SlamLocationBean mSlamLocationBean) {
        obTitle.set("导航中...");
        EventBus.getDefault().post(new MoveToEvent(mSlamLocationBean));
    }


    /**
     * 位置点列表弹窗监听
     */
    public OnPointListener onPointListener = new OnPointListener() {
        @Override
        public void onMove(SlamLocationBean slamLocationBean) {
            if (null == markPoseList) {
                markPoseList = new ArrayList<>();
            }
            markPoseList.clear();
            markPoseList.add(slamLocationBean);
            navType = 2;
            moveTo(slamLocationBean);
        }
    };


    public RoundMenuView.OnMenuClickListener onMenuClickListener = new RoundMenuView.OnMenuClickListener() {

        @Override
        public void onStart() {

        }

        @Override
        public void onClick(int i) {
            isStart = true;
            mClickCount = 0;
            mMoveRunnable = new MoveRunnable(i);
            ThreadPoolManager.getInstance().execute(mMoveRunnable);
        }

        @Override
        public void onFinish() {
            isStart = false;
            ThreadPoolManager.getInstance().cancel(mMoveRunnable);
        }
    };

    private class MoveRunnable implements Runnable {
        int i;

        public MoveRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (isStart) {

                try {
                    long delayTime;
                    // 左右控制的时候，间隔发送
                    if (i == 1 || i == 3) {
                        delayTime = TIME_CONTROL_ROTATE_INTERVAL;
                        if (mClickCount >= 1) {
                            mClickCount = 0;
                        } else {
                            mClickCount++;
                            controlMove(i);
                        }
                    } else {
                        delayTime = TIME_CONTROL_MOVE_INTERVAL;
                        controlMove(i);
                    }

                    Thread.sleep(delayTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }

            }

        }
    }

    private void controlMove(int direction) {
        LogUtils.d("TAG","direction"+direction);
        MoveDirection moveDirection;
        switch (direction) {
            case 1:
                moveDirection = MoveDirection.TURN_LEFT;
                break;
            case 3:
                moveDirection = MoveDirection.TURN_RIGHT;
                break;
            case 2:
                moveDirection = MoveDirection.FORWARD;
                break;
            case 0:
                moveDirection = MoveDirection.BACKWARD;
                break;
            default:
                moveDirection = null;
                break;
        }
        if (moveDirection != null) {
            RosDeviceManager.INSTANCE.moveBy(moveDirection);
        }

    }

    public static class MainHandler extends Handler {
        MainViewModel mainViewModel;

        MainHandler(Looper looper, WeakReference<MainViewModel> weakReference) {
            super(looper);
            mainViewModel = weakReference.get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HandlerMsgCode.GETVOLTAGE://获取电量
                    mainViewModel.obBatteryValue.set(msg.obj + "%");
                    mainViewModel.batteryValueVo.postValue((int) msg.obj);
                    break;
                case HandlerMsgCode.ISCHARGING://是否在充电
                    mainViewModel.chargeStatusVo.postValue(((int) msg.obj) == 1);
                    break;
                case HandlerMsgCode.CURRENTROBOTPOSE://当前位姿
                    mainViewModel.showPose((LocalPose) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }


}
