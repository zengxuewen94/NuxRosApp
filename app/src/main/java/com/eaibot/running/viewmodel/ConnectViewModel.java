package com.eaibot.running.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.eaibot.running.R;
import com.eaibot.running.activity.ConnectActivity;
import com.eaibot.running.bean.NuxRosMapBean;
import com.eaibot.running.constants.BaseConstant;
import com.eaibot.running.constants.HandlerMsgCode;
import com.eaibot.running.constants.RosMode;
import com.eaibot.running.event.GoHomeEvent;
import com.eaibot.running.event.RobotInfoEvent;
import com.eaibot.running.event.RosConnectStatusEvent;
import com.eaibot.running.manger.ActivityManager;
import com.eaibot.running.manger.BaseDataManager;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.manger.ThreadPoolManager;
import com.eaibot.running.service.RosService;
import com.eaibot.running.view.dialog.interfaces.MyItemDialogListener;
import com.eaibot.running.widget.RoundMenuView;
import com.nudtit.androidrosbridgeclient.RosBridgeClientManager;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.androidrosbridgeclient.ros.message.nav_msgs.OccupancyGrid;
import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.StringUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.lib_common.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :  zengxuewen
 * @date :  2022/9/5
 * @desc :  连接页面ViewModel
 */
public class ConnectViewModel extends BaseNavigateViewModel {

    // 工具箱弹窗
    private final MutableLiveData<Boolean> toolBoxVo = new MutableLiveData<>();
    // 去往主界面
    private final MutableLiveData<Bundle> goToMainActivityVo = new MutableLiveData<>();
    // 去往主界面
    private final MutableLiveData<String> goToCheckActivityVo = new MutableLiveData<>();

    private final MutableLiveData<Integer> mapSpannerSelectionVo = new MutableLiveData<>();

    //电量
    private final MutableLiveData<Integer> batteryValueVo = new MutableLiveData<>();
    //充电状态
    private final MutableLiveData<Boolean> chargeStatusVo = new MutableLiveData<>();
    public MutableLiveData<OccupancyGrid> obOccupancyGrid = new MutableLiveData<>();


    public MutableLiveData<Boolean> getToolBoxVo() {
        return toolBoxVo;
    }

    public MutableLiveData<Bundle> getGoToMainActivityVo() {
        return goToMainActivityVo;
    }

    public MutableLiveData<String> getGoToCheckActivityVo() {
        return goToCheckActivityVo;
    }

    public MutableLiveData<Integer> getMapSpannerSelectionVo() {
        return mapSpannerSelectionVo;
    }

    public MutableLiveData<Integer> getBatteryValueVo() {
        return batteryValueVo;
    }

    public MutableLiveData<Boolean> getChargeStatusVo() {
        return chargeStatusVo;
    }

    public MutableLiveData<OccupancyGrid> getObOccupancyGrid() {
        return obOccupancyGrid;
    }

    //机器连接状态
    public ObservableField<Integer> obRosConnectStatus = new ObservableField<>();
    //机器连接状态
    public ObservableField<Integer> obWifiStatus = new ObservableField<>();
    //ip地址
    public ObservableField<String> obIp = new ObservableField<>();
    //电量
    public ObservableField<String> obBatteryValue = new ObservableField<>();
    //连接button 文字
    public ObservableField<String> obBtConnectText = new ObservableField<>();

    //连接button 可编辑状态
    public ObservableField<Boolean> obBtConnectEnable = new ObservableField<>();
    //上传地图button 可编辑状态
    public ObservableField<Boolean> obUploadEnable = new ObservableField<>();
    //下载地图button 可编辑状态
    public ObservableField<Boolean> obDownloadEnable = new ObservableField<>();
    //imu选中状态
    public ObservableField<Boolean> obImuChecked = new ObservableField<>();
    //回充选中状态
    public ObservableField<Boolean> obChargeChecked = new ObservableField<>();
    //建图选中状态
    public ObservableField<Boolean> obMappingChecked = new ObservableField<>();
    //导航选中状态
    public ObservableField<Boolean> obNavigateChecked = new ObservableField<>();
    //google选中状态
    public ObservableField<Boolean> obGoogleChecked = new ObservableField<>();
    //检验选中状态
    public ObservableField<Boolean> obCheckChecked = new ObservableField<>();
    //手柄显示
    public ObservableField<Boolean> obHandleViewShow = new ObservableField<>();

    public ObservableField<String> obLoadMap = new ObservableField<>();


    //是否在连接中
    private boolean isConnecting;
    private boolean canGo;
    private boolean isFirst = true;
    public boolean isCharging;
    // 底盘连接成功
    private boolean connectSuccess;
    //是否需要连接机器人
    private boolean needConnectRos = true;
    // 是否首次加载地图
    private boolean firstLoadMap = true;
    private String serviceType = "gmapping";
    private String selectedMapName;
    private int mode;
    //连接失败的次数
    private int connectRosFailedTimes = 0;
    private int btnConnectType = 1;
    // 选中的地图标识
    private int selectMapIndex = 0;
    private int currentMapIndex = 0;
    private ConnectHandler connectHandler;
    private List<NuxRosMapBean> mapFiles = new ArrayList<>();
    public List<String> mapNames = new ArrayList<>();

    private static final int TIME_CONTROL_MOVE_INTERVAL = 80;
    private static final int TIME_CONTROL_ROTATE_INTERVAL = 100;
    private boolean isStart;
    private int mClickCount;
    private MoveRunnable mMoveRunnable;


    public ConnectViewModel(@NonNull Application application) {
        super(application);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        initData();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        RosDeviceManager.INSTANCE.closeConnect();
    }

    public void initData() {

        if (connectHandler == null) {
            connectHandler = new ConnectHandler(Looper.getMainLooper(), new WeakReference<>(this));
        }
        String ip = BaseDataManager.INSTANCE.getRosIp();
        obIp.set(ip);
        obBtConnectText.set(Utils.getString(R.string.start));
        obBtConnectEnable.set(false);
        obWifiStatus.set(R.drawable.wifi_signal_full);
        obRosConnectStatus.set(R.drawable.emoticon_sleep);
        obImuChecked.set(getImuInfo());
        obChargeChecked.set(getRechargeInfo());
        obHandleViewShow.set(BaseDataManager.INSTANCE.getHandleViewShow());
        serviceType = getServiceType();
        int index = 0;
        switch (serviceType) {
            case "gmapping":
                index = 0;
                obMappingChecked.set(true);
                break;
            case "navigation":
                obNavigateChecked.set(true);
                index = 1;
                break;
            case "google":
                obGoogleChecked.set(true);
                index = 2;
                break;
            case "check":
                obCheckChecked.set(true);
                index = 3;
                break;
            default:
                break;
        }
        setCheck(index);

    }

    private void setCheck(int index) {
        if (index != 0) {
            obMappingChecked.set(false);
        }
        if (index != 1) {
            obNavigateChecked.set(false);
        }
        if (index != 2) {
            obGoogleChecked.set(false);
        }
        if (index != 3) {
            obCheckChecked.set(false);
        }
    }


    /**
     * wifi连接状态
     *
     * @param wifiConnected
     */
    public void wifiConnect(boolean wifiConnected) {
        if (wifiConnected) {
            connectRosFailedTimes = 0;
            showLongToast(Utils.getString(R.string.checkingNetwork));
            if (isFirst) {// wifi只有首次变动才主动刷新
                isFirst = false;
                connectROS();
            }
            obWifiStatus.set(R.drawable.wifi_signal_full);
        } else {
            obBtConnectEnable.set(false);
            obWifiStatus.set(R.drawable.wifi_signal_miss);
            showLongToast(Utils.getString(R.string.wifiDisconnected));
            needConnectRos = true;
        }
    }


    /**
     * 连接ROS底盘
     */
    private void connectROS() {
        BaseDataManager.INSTANCE.setRosIp(obIp.get());
        Observable.create(emitter -> {
            //这里失败后连接两次不成功放弃连接
            while (needConnectRos && connectRosFailedTimes < 2) {
                isConnecting = true;

                connectSuccess = RosDeviceManager.INSTANCE.connect(obIp.get().trim() + BaseConstant.CONNECT_SPLIT + BaseConstant.CONNECT_PORT);


                if (connectSuccess) {
                    RosDeviceManager.INSTANCE.startNavigate();
                    Utils.getContext().startService(new Intent(Utils.getContext(), RosService.class));
                    needConnectRos = false;
                    obLoadMap.set(RosDeviceManager.INSTANCE.getCurrentMap());
                    //保存底盘当前加载的地图
                    BaseDataManager.INSTANCE.setLoadMapName(RosDeviceManager.INSTANCE.getCurrentMap());
                    //订阅地图数据
                    RosDeviceManager.INSTANCE.subscribeGetMapDataTopic();
                    RosDeviceManager.INSTANCE.subscribeLaserScanData();
                    RosDeviceManager.INSTANCE.subscribeLocalLine();

                    //getMapList();
                }
                emitter.onNext(connectSuccess);
                Thread.sleep(2000);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                BaseConstant.rosConnected = connectSuccess;
                isConnecting = false;
                if (connectSuccess) {
                    //Utils.getContext().startService(new Intent(Utils.getContext(), RosService.class));
                    initRosNode();
                    onComplete();

                } else {
                    LogUtils.d("TAG", Utils.getString(R.string.robotNoService));
                    connectRosFailedTimes++;
                    if (connectRosFailedTimes >= 2) {
                        needConnectRos = true;
                        obBtConnectEnable.set(false);
                        showLongToast(Utils.getString(R.string.robotNoService));
                        obRosConnectStatus.set(R.drawable.emoticon_sleep);
                        onComplete();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    //ROS服务端初始化成功后,添加节点
    private void initRosNode() {
        showLongToast(Utils.getString(R.string.robotIsConnected));
        obRosConnectStatus.set(R.drawable.emoticon);
        obBtConnectEnable.set(true);
        connectSuccess = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        OccupancyGrid occupancyGrid = RosDeviceManager.INSTANCE.getMapData();
                        if (occupancyGrid != null) {
                            obOccupancyGrid.postValue(occupancyGrid);
                        }
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    /**
     * 获取地图列表
     */
    private void getMapList() {
        List<NuxRosMapBean> mapList = BaseDataManager.INSTANCE.getMapList();
        LogUtils.d("getMap", mapList.size() + "--");
        connectHandler.obtainMessage(HandlerMsgCode.GETMAPS, mapList).sendToTarget();
    }


    /**
     * 点击事件-启动
     */
    public void onClickConnect() {
        if (connectSuccess) {
            if (btnConnectType == 1) {
                obBtConnectText.set(Utils.getString(R.string.cancel));
                btnConnectType = 2;
                canGo = true;
                startLocalService();
            } else {
                canGo = false;
                showShortToast(Utils.getString(R.string.startupCancelled));
                switch (mode) {
                    case RosMode.GMAPPINGIMUSTART:
                        //HttpServiceTool.gmappingImuStop(ip, handler, HandlerMsgCode.SERVICESTOP);
                        break;
                    case RosMode.GMAPPINGSTART:
                        // HttpServiceTool.gmappingStop(ip, handler, HandlerMsgCode.SERVICESTOP);
                        break;
                    case RosMode.NAVIGATIONIMUSTART:
                    case RosMode.NAVIGATIONIMUNORECHARGESTART:
                        //HttpServiceTool.navigationImuStop(ip, handler, HandlerMsgCode.SERVICESTOP);
                        break;
                    case RosMode.NAVIGATIONSTART:
                    case RosMode.NAVIGATIONNORECHARGESTART:
                        //HttpServiceTool.navigationStop(ip, handler, HandlerMsgCode.SERVICESTOP);
                        break;
                    case RosMode.CARTOGRAPHERSTART:
                        //HttpServiceTool.cartographerStop(ip, handler, HandlerMsgCode.SERVICESTOP);
                        break;
                    default:
                        break;
                }
                btnConnectType = 1;
                obBtConnectText.set(Utils.getString(R.string.start));
            }
        } else {
            showShortToast(Utils.getString(R.string.robotNoConnect));
        }
    }

    /**
     * 点击事件工具包
     */
    public void onClickToolBox() {
        if (!isFastDoubleClick()) {
            toolBoxVo.postValue(true);
        }

    }

    /**
     * 停止导航
     */
    public void onClickStopNavigate() {
        if (connectSuccess) {
            showShortToast(Utils.getString(R.string.stopNavigation));
            RosBridgeClientManager.INSTANCE.cancelGoHome();
            RosBridgeClientManager.INSTANCE.actionCancel();
        } else {
            showShortToast(Utils.getString(R.string.robotNoConnect));
        }
    }

    /**
     * 继续导航
     */
    public void onClickContinueNavigate() {
        if (connectSuccess) {
            showShortToast(Utils.getString(R.string.continueNavigation));
        } else {
            showShortToast(Utils.getString(R.string.robotNoConnect));
        }
    }


    private void goToMainActivity() {
        obBtConnectText.set(Utils.getString(R.string.start));
        btnConnectType = 1;
        Bundle bundle = new Bundle();
        bundle.putString("selectedMapName", selectedMapName);
        bundle.putString("ip", obIp.get());
        bundle.putInt("mode", mode);
        goToMainActivityVo.postValue(bundle);
    }

    //前往验收界面
    private void goToCheckActivity() {
        obBtConnectText.set(Utils.getString(R.string.start));
        btnConnectType = 1;
        goToCheckActivityVo.postValue(obIp.get());
    }

    //启动服务
    private void startLocalService() {
        switch (serviceType) {
            case "gmapping":
                if (obImuChecked.get()) {
                    mode = RosMode.GMAPPINGIMUSTART;
                } else {
                    mode = RosMode.GMAPPINGSTART;
                }
                //开启建图
                //RosDeviceManager.INSTANCE.settingOsBuildMap();
                goToMainActivity();
                break;
            case "navigation":
                if (obImuChecked.get()) {
                    if (obChargeChecked.get()) {
                        mode = RosMode.NAVIGATIONIMUSTART;
                    } else {
                        mode = RosMode.NAVIGATIONIMUNORECHARGESTART;
                    }
                    //HttpRequestTool.setMap(ip, selectedMapName, handler, HandlerMsgCode.SETMAPIMU);
                    goToMainActivity();
                } else {
                    if (obChargeChecked.get()) {
                        mode = RosMode.NAVIGATIONSTART;
                    } else {
                        mode = RosMode.NAVIGATIONNORECHARGESTART;
                    }
                    if (!"".equals(selectedMapName)) {
                        // HttpRequestTool.setMap(ip, selectedMapName, handler, HandlerMsgCode.SETMAPODOM);
                    } else {
                        showShortToast(Utils.getString(R.string.noSelectableMapToNavi));
                        //开启导航
                        //NviceManager.INSTANCE.settingOsNavigate();
                    }
                }
                break;
            case "google":
                mode = RosMode.CARTOGRAPHERSTART;
                //HttpServiceTool.cartographerStart(ip, handler, HandlerMsgCode.SERVICESTART);
                break;
            case "check":
                if (obImuChecked.get()) {
                    //HttpServiceTool.navigationImuStart(ip, handler, HandlerMsgCode.CHECKSTART);
                } else {
                    //HttpServiceTool.navigationStart(ip, handler, HandlerMsgCode.CHECKSTART);
                }
                if (canGo) {
                    goToCheckActivity();
                }
                //RosDeviceManager.INSTANCE.settingOsNavigate();
                break;
            default:
                break;
        }
    }


    /**
     * 地图选择监听
     */
    public AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (connectSuccess && 1 == RosDeviceManager.INSTANCE.getPowerSupplyStatus()) {
                selectMapIndex = position;
                selectedMapName = parent.getSelectedItem().toString();
                boolean loadSuccess = RosDeviceManager.INSTANCE.loadMap(selectedMapName + BaseConstant.FILE_NAME_SUFFIX);
                if (loadSuccess) {
                    BaseDataManager.INSTANCE.setLoadMapName(selectedMapName);
                    showShortToast("地图加载成功");

                }
            } else {
                if (selectMapIndex != position) {
                    if (!connectSuccess) {
                        showShortToast(Utils.getString(R.string.robotNoConnect));
                    } else {
                        showShortToast("切换地图请在充电桩上进行");
                    }
                }

                mapSpannerSelectionVo.postValue(selectMapIndex);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = buttonView.getId();
            switch (id) {
                case R.id.cb_imu:
                    obImuChecked.set(isChecked);
                    setImuInfo(isChecked);
                    break;
                case R.id.cb_recharge:
                    obChargeChecked.set(isChecked);
                    setRechargeInfo(isChecked);
                    break;
                case R.id.cbGmapping:
                    setBoxFalseWithout(0, isChecked);
                    obMappingChecked.set(isChecked);
                    break;
                case R.id.cbNavigation:
                    setBoxFalseWithout(1, isChecked);
                    obNavigateChecked.set(isChecked);
                    break;
                case R.id.cbGoogle:
                    setBoxFalseWithout(2, isChecked);
                    obGoogleChecked.set(isChecked);
                    break;
                case R.id.cbCheck:
                    setBoxFalseWithout(3, isChecked);
                    obCheckChecked.set(isChecked);
                    break;
                default:
                    break;
            }
        }
    };

    //刷新页面
    private void refreshActivity() {
        if (StringUtils.isEmpty(obIp.get())) {
            showShortToast("ip地址不能为空");
            return;
        }
        if (obIp.get().trim().contains(" ")) {
            showShortToast("ip地址不合法,请检查ip是否正确或者带有空格");
            return;
        }
        if (isConnecting) {
            showShortToast("正在连接中，请稍后...");
            return;
        }
        String oldIp = BaseDataManager.INSTANCE.getRosIp();
        // ip地址发生变更点击刷新则重新连接底盘
        if (!TextUtils.equals(oldIp, obIp.get())) {
            needConnectRos = true;
        }
        if (needConnectRos) {
            connectRosFailedTimes = 0;

            if (connectSuccess) {//判断底盘是否连接成功，连接成功才断开连接
                // 先关闭连接
                RosDeviceManager.INSTANCE.closeConnect();
            }
            connectROS();
        }

        showShortToast(Utils.getString(R.string.refreshingNow));
    }


    /**
     * 工具箱
     */
    boolean startNewMap = false;
    int opMapType = 0;
    public MyItemDialogListener myItemDialogListener = new MyItemDialogListener() {
        @Override
        public void onItemClick(CharSequence text, int position) {
            switch (position) {
                case 0://刷新页面
                    refreshActivity();
                    break;
                case 1://隐藏显示手柄
                    obHandleViewShow.set(!obHandleViewShow.get());
                    BaseDataManager.INSTANCE.setHandleViewShow(obHandleViewShow.get());
                    break;
                case 2://新建地图
                    if (!startNewMap) {
                        RosDeviceManager.INSTANCE.settingOsBuildMap();
                        startNewMap = true;
                    }
                    break;
                case 3://重定位

                    opMapType = 3;
                    break;
                case 4://地图导航
                    opMapType = 4;
                    break;
                case 5://保存地图
                    if (startNewMap) {
                        ThreadPoolManager.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (RosDeviceManager.INSTANCE.saveMap("测试地图")) {
                                    ToastUtil.showLong("保存成功");
                                    startNewMap = false;
                                    RosDeviceManager.INSTANCE.startNavigate();
                                }
                            }
                        });

                    }
                    break;
                case 6://回充
                    RosDeviceManager.INSTANCE.goHome(null);
                    break;
                default:
                    break;
            }
        }
    };

    public int getOpMapType() {
        return opMapType;
    }

    public boolean isStartNewMap() {
        return startNewMap;
    }

    //将imu情况存入共享参数
    private void setImuInfo(boolean value) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean("imu", value);

    }

    //将imu情况存入共享参数
    private boolean getImuInfo() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean("imu", true);
    }

    //将充电方式存入共享参数
    private void setRechargeInfo(boolean value) {

        SharedPreferencesUtils.getInstance(Utils.getContext()).putBoolean("recharge", value);
    }

    //将充电方式存入共享参数
    private boolean getRechargeInfo() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getBoolean("recharge", true);
    }


    //将服务类型存入共享参数
    private void setServiceType(String type) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putString("serviceType", type);
    }

    //将服务类型存入共享参数
    private String getServiceType() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getString("serviceType", "navigation");
    }

    //将ip存入共享参数
    private void setRosServiceIp(String ip) {
        SharedPreferencesUtils.getInstance(Utils.getContext()).putString("rosServiceIp", ip);
    }

    //将ip存入共享参数
    private String getRosServiceIp() {
        return SharedPreferencesUtils.getInstance(Utils.getContext()).getString("rosServiceIp", BaseConstant.CONNECT_IP);
    }


    //将除了正在点击的多选框外，其他所有多选框设为不选中
    private void setBoxFalseWithout(int index, boolean b) {
        if (b) {
            switch (index) {
                case 0:
                    serviceType = "gmapping";
                    break;
                case 1:
                    serviceType = "navigation";
                    break;
                case 2:
                    serviceType = "google";
                    break;
                case 3:
                    serviceType = "check";
                    break;
                default:
                    break;
            }
            setServiceType(serviceType);
            setCheck(index);
        }
    }

    /**
     * 显示地图列表
     */
    private void showMapList(List<NuxRosMapBean> mapList) {
        if (mapList != null && !mapList.isEmpty()) {
            mapNames.clear();
            mapFiles.clear();
            mapFiles.addAll(mapList);
            currentMapIndex = 0;
            for (int i = 0; i < mapList.size(); i++) {
                mapNames.add(mapList.get(i).getMapName());
            }
            obBtConnectEnable.set(true);
            // 获取上次加载的地图名称
//            String baseLoadMap = BaseDataManager.INSTANCE.getBaseLoadMapName(BaseDataManager.INSTANCE.getLoadMapName());
            String baseLoadMap;
            if (firstLoadMap) {
                // 获取底盘当前加载的地图
                baseLoadMap = RosDeviceManager.INSTANCE.getCurrentMap();
                LogUtils.d("TAG", "当前地图：" + baseLoadMap);
                firstLoadMap = false;
            } else {
                baseLoadMap = BaseDataManager.INSTANCE.getLoadMapName();
            }

            if (!TextUtils.isEmpty(baseLoadMap) && !mapNames.isEmpty()) {
                //循环判断地图列表中是否存在上次加载的地图
                for (int i = 0; i < mapNames.size(); i++) {
                    if (mapNames.get(i).equals(baseLoadMap)) {
                        currentMapIndex = i;
                        break;
                    }
                }
            }

            if (mapNames.size() != 0) {
                selectMapIndex = currentMapIndex;
                mapSpannerSelectionVo.postValue(currentMapIndex);

            }

        }
    }


    /**
     * 断开连接
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRosConnect(RosConnectStatusEvent event) {
        if (event.isConnect()) {
            connectSuccess = true;
            needConnectRos = false;
        } else {
            showShortToast(event.getReason());
            obRosConnectStatus.set(R.drawable.emoticon_sleep);
            obBatteryValue.set("");
            needConnectRos = true;
            connectSuccess = false;
            BaseConstant.rosConnected = false;
            obBtConnectEnable.set(false);
            isConnecting = false;
            ActivityManager.getInstance().finishAllActivities(ConnectActivity.class);
        }
    }


    /**
     * 底盘状态
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void robotInfo(RobotInfoEvent event) {
        if (null != event && null != connectHandler) {
            connectHandler.obtainMessage(HandlerMsgCode.GETVOLTAGE, RosDeviceManager.INSTANCE.getBatteryPercentage()).sendToTarget();
            connectHandler.obtainMessage(HandlerMsgCode.ISCHARGING, RosDeviceManager.INSTANCE.getPowerSupplyStatus()).sendToTarget();
        }
    }


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
        LogUtils.d("TAG", "direction" + direction);
        MoveDirection moveDirection;
        switch (direction) {
            case 0:
                moveDirection = MoveDirection.BACKWARD;
                break;
            case 1:
                moveDirection = MoveDirection.TURN_LEFT;
                break;
            case 2:
                moveDirection = MoveDirection.FORWARD;
                break;
            case 3:
                moveDirection = MoveDirection.TURN_RIGHT;
                break;


            default:
                moveDirection = null;
                break;
        }
        if (moveDirection != null) {
            RosDeviceManager.INSTANCE.moveBy(moveDirection);
        }

    }

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
            //RosBridgeClientManager.INSTANCE.actionCancel();
            ThreadPoolManager.getInstance().cancel(mMoveRunnable);
        }
    };


    @Override
    protected void onCleared() {
        if (null != connectHandler) {
            connectHandler.removeCallbacksAndMessages(null);
            connectHandler = null;
        }

        Utils.getContext().stopService(new Intent(Utils.getContext(), RosService.class));
        if (connectSuccess) {
            RosDeviceManager.INSTANCE.closeConnect();
        }
        super.onCleared();
    }

    @Override
    protected void onNavigateError() {

    }

    @Override
    protected void onNavigateSuccess() {

    }

    public static class ConnectHandler extends Handler {
        ConnectViewModel connectViewModel;

        ConnectHandler(Looper looper, WeakReference<ConnectViewModel> weakReference) {
            super(looper);
            connectViewModel = weakReference.get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (connectViewModel == null) {
                return;
            }
            switch (msg.what) {
                case HandlerMsgCode.GETMAPS://地图列表
                    connectViewModel.obWifiStatus.set(R.drawable.wifi_signal_full);
                    connectViewModel.showMapList((List<NuxRosMapBean>) msg.obj);
                    break;
                case HandlerMsgCode.CHECKSTART:
                    if (connectViewModel.canGo) {
                        connectViewModel.goToCheckActivity();
                    }
                    break;
                case HandlerMsgCode.GMAPPINGSTOP:
                    connectViewModel.obRosConnectStatus.set(R.drawable.emoticon_sleep);
                    break;
                case HandlerMsgCode.GETVOLTAGE://获取电量
                    connectViewModel.obBatteryValue.set(msg.obj + "%");
                    connectViewModel.batteryValueVo.postValue((int) msg.obj);
                    break;
                case HandlerMsgCode.ISCHARGING:
                    connectViewModel.chargeStatusVo.postValue(((int) msg.obj) == 1);
                    break;
                case HandlerMsgCode.DOWNLOAD_YAML_SUCCESS:
                    break;
                case HandlerMsgCode.DOWNLOAD_YAML_FAILED:
                    break;
                case HandlerMsgCode.DOWNLOAD_POSE_SUCCESS:
                    break;
                case HandlerMsgCode.DOWNLOAD_POSE_FAILED:
                    break;
                case HandlerMsgCode.UPLOAD_YAML_SUCCESS:
                    break;
                case HandlerMsgCode.UPLOAD_YAML_FAILED:
                    break;
                case HandlerMsgCode.UPLOAD_POSE_SUCCESS:
                    break;
                case HandlerMsgCode.UPLOAD_POSE_FAILED:
                    break;
                case HandlerMsgCode.SETMAPIMU:
                    //HttpServiceTool.navigationImuStart(ip, handler, HandlerMsgCode.SERVICESTART);
                    break;
                case HandlerMsgCode.SETMAPODOM:
                    ///HttpServiceTool.navigationStart(ip, handler, HandlerMsgCode.SERVICESTART);
                    break;
                default:
                    break;
            }
        }
    }


}
