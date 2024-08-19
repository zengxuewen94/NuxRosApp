package com.nudtit.module_main.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.androidrosbridgeclient.interfaces.OnNavigateListener;
import com.nudtit.control_common.base.BaseViewModel;
import com.nudtit.lib_common.manager.ActivityManager;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.module_main.R;
import com.nudtit.module_main.activity.MainActivity;
import com.nudtit.module_main.event.RosDisconnectEvent;
import com.nudtit.module_main.listener.MapOperateListener;
import com.nudtit.module_main.manager.RosDeviceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;


/**
 * @author :  zengxuewen
 * @date :  2022/8/9
 * @desc : 主ViewModel
 */
public class MainViewModel extends BaseViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    // 地图
    private MutableLiveData<Boolean> mapDialogVo = new MutableLiveData<>();
    // 动作
    private MutableLiveData<Boolean> actionDialogVo = new MutableLiveData<>();
    // 编辑
    private MutableLiveData<Boolean> editDialogVo = new MutableLiveData<>();
    // 虚拟墙编辑
    private MutableLiveData<Boolean> virtualWallDialogVo = new MutableLiveData<>();
    // 跳转至信息页面
    private MutableLiveData<Boolean> jumpToRobotInfo = new MutableLiveData<>();
    // 跳转至设置页面
    private MutableLiveData<Boolean> jumpToSetting = new MutableLiveData<>();

    public MutableLiveData<Boolean> getMapDialogVo() {
        return mapDialogVo;
    }

    public MutableLiveData<Boolean> getActionDialogVo() {
        return actionDialogVo;
    }

    public MutableLiveData<Boolean> getEditDialogVo() {
        return editDialogVo;
    }

    public MutableLiveData<Boolean> getVirtualWallDialogVo() {
        return virtualWallDialogVo;
    }

    public MutableLiveData<Boolean> getJumpToRobotInfo() {
        return jumpToRobotInfo;
    }

    public MutableLiveData<Boolean> getJumpToSetting() {
        return jumpToSetting;
    }

    public ObservableField<Boolean> obNavigateSelected = new ObservableField<>();

    public MainViewModel(@NonNull @NotNull Application application) {
        super(application);
        EventBus.getDefault().register(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        initTitle("", R.color.white, R.drawable.ic_main_title_info, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (R.id.iv_view_setting_back == v.getId()) {
                    jumpToRobotInfo.postValue(true);
                } else if (R.id.iv_view_setting_right == v.getId()) {
                    jumpToSetting.postValue(true);
                }
            }
        });
        setTitleIvBg(R.drawable.ic_main_title_setting);
        titleViewModel.setViewBg(R.color.blue_color);
        // 获取地图列表
//        List<NuxRosMapBean> mapList = MainDataManager.INSTANCE.getMapList();
//        if (null != mapList && !mapList.isEmpty()) {
//            for (NuxRosMapBean mapBean : mapList) {
//                LogUtils.d(TAG, mapBean.getMapName());
//            }
//        }
//        //加载地图
//        LogUtils.d(TAG, "加载地图：" + RosDeviceManager.INSTANCE.loadMap("gongsi2" + BaseConstant.FILE_NAME_SUFFIX));
    }

    /**
     * 点击事件-地图
     */
    public void onClickMap() {
        mapDialogVo.postValue(true);
    }


    /**
     * 点击事件-动作
     */
    public void onClickAction() {
        actionDialogVo.postValue(true);
    }

    /**
     * 点击事件-编辑
     */
    public void onClickEdit() {
        editDialogVo.postValue(true);
    }


    /**
     * 点击事件-停止动作
     */
    public void onClickStopAction() {
        RosDeviceManager.INSTANCE.actionCancel();
    }


    /**
     * 前后左右移动
     *
     * @param moveDirection
     */
    public void moveBy(MoveDirection moveDirection) {
        if (null == moveDirection) {
            RosDeviceManager.INSTANCE.actionCancel();
        } else {
            RosDeviceManager.INSTANCE.moveBy(moveDirection);
        }

    }

    /**
     * 底盘失去连接
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void rosDisConnect(RosDisconnectEvent event) {
        ToastUtil.showShort(Utils.getString(R.string.offline_login_disconnect));
        //关闭除主界面的其他界面
        ActivityManager.getInstance().finishAllActivities(MainActivity.class);
        activityFinishVo.postValue(true);
    }


    /**
     * 导航按钮选中状态监听
     */
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            obNavigateSelected.set(isChecked);
        }
    };





    public OnNavigateListener onNavigateListener = new OnNavigateListener() {
        @Override
        public void waitingForStart() {

        }

        @Override
        public void running() {

        }

        @Override
        public void finished() {
            LogUtils.d(TAG, " finished");
        }

        @Override
        public void paused() {

        }

        @Override
        public void stopped() {

        }

        @Override
        public void error() {
            LogUtils.d(TAG, " error");
        }
    };


    @Override
    protected void onCleared() {
        EventBus.getDefault().unregister(this);
        // 取消数据订阅
        RosDeviceManager.INSTANCE.unsubscribeGetMapDataTopic();
        super.onCleared();
    }
}
