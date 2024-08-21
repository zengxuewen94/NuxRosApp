package com.eaibot.running.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eaibot.running.R;
import com.eaibot.running.constants.VersionTexts;
import com.eaibot.running.databinding.ActivityConnectBinding;
import com.eaibot.running.db.SlamLocationDBManager;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.manger.BaseDataManager;
import com.eaibot.running.manger.MapHelper;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.service.MainService;
import com.eaibot.running.view.BatteryView;
import com.eaibot.running.view.StyledDialog;
import com.eaibot.running.view.WebHandleView;
import com.eaibot.running.view.dialog.bottomsheet.BottomSheetBean;
import com.eaibot.running.viewmodel.ConnectViewModel;
import com.eaibot.running.widget.RoundMenuView;
import com.nudtit.androidrosbridgeclient.RosBridgeClientManager;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.androidrosbridgeclient.ros.message.nav_msgs.MapMetaData;
import com.nudtit.androidrosbridgeclient.ros.message.nav_msgs.OccupancyGrid;
import com.nudtit.androidrosbridgeclient.ros.message.sensor_msgs.LaserScan;
import com.nudtit.lib_common.utils.SharedPreferencesUtils;
import com.nudtit.lib_common.utils.Utils;

import com.nudtit.slam.bean.LocalLaserScan;
import com.nudtit.slam.bean.LocalPose;
import com.nudtit.slam.bean.Location;
import com.nudtit.slam.bean.Path;
import com.nudtit.slam.bean.PointF;
import com.nudtit.slam.bean.Size;
import com.nudtit.slam.mapview.MapView;
import com.nudtit.slam.robot.LaserPoint;

import com.nudtit.slam.robot.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.nudtit.lib_common.utils.BitmapUtil.drawableToBitmap;

/**
 * @author Administrator
 */
public class ConnectActivity extends BaseActivity {

    //基本变量
    private long exitTime = 0;
    //语言类型
    private int language = 0;
    //特殊变量
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private RoundMenuView roundMenuView;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    //加载控件
    private BatteryView batteryView;
    private MapView mapView;
    private MapHelper mapHelper;
    private BroadcastReceiver rosServiceBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConnectBinding connectBinding = DataBindingUtil.setContentView(this, R.layout.activity_connect);
        Utils.getContext().startService(new Intent(Utils.getContext(), MainService.class));
        ConnectViewModel connectViewModel = new ViewModelProvider(this).get(ConnectViewModel.class);
        connectBinding.setConnectViewModel(connectViewModel);
        initView(connectViewModel);
        observeViewModel(connectViewModel);
        getLifecycle().addObserver(connectViewModel);
        initBroadcastReceiver(connectViewModel);
        registerReceiver();


    }


    //广播监听
    private void initBroadcastReceiver(ConnectViewModel connectViewModel) {
        rosServiceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifiinfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    connectViewModel.wifiConnect(wifiinfo.isConnected());
                }
            }
        };
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(rosServiceBroadcastReceiver, intentFilter);
    }

    MapMetaData mapMetaData = null;

    private void observeViewModel(ConnectViewModel connectViewModel) {
        //工具箱
        connectViewModel.getToolBoxVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                initToolbox(connectViewModel);
            }
        });
        connectViewModel.getGoToMainActivityVo().observe(this, new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle bundle) {
                goToMainActivity(bundle);
            }
        });
        connectViewModel.getGoToCheckActivityVo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                goToCheckActivity(s);
            }
        });
        connectViewModel.getMapSpannerSelectionVo().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                spinnerAdapter.notifyDataSetChanged();
                spinner.setSelection(integer, true);
            }
        });
        connectViewModel.getBatteryValueVo().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                batteryView.setPower(integer / 100f);
            }
        });
        connectViewModel.getChargeStatusVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                batteryView.setIsCharging(aBoolean);
            }
        });


        connectViewModel.getObOccupancyGrid().observe(this, new Observer<OccupancyGrid>() {
            @Override
            public void onChanged(OccupancyGrid occupancyGrid) {
                if (mapMetaData == null) {
                    mapMetaData = occupancyGrid.info;
                    setMapData(occupancyGrid);

                    List<SlamLocationBean> list = SlamLocationDBManager.getInstance(Utils.getContext())
                            .queryLocationListByMap("室内Map1117");

                    List<Location> mList = new ArrayList<>();
                    for (SlamLocationBean slamLocationBean : list) {
                        Location location = new Location();
                        location.setX(slamLocationBean.getX());
                        location.setY(slamLocationBean.getY());
                        location.setZ(slamLocationBean.getYaw());
                        location.setLocationName(slamLocationBean.getLocationNameChina());
                        mList.add(location);
                    }

                    mapView.setSlamLocationBeans(mList);

                } else {
                    if (connectViewModel.isStartNewMap()) {
                        mapMetaData = occupancyGrid.info;
                        setMapData(occupancyGrid);
                    }
                }

                LocalPose localPose = RosDeviceManager.INSTANCE.getPose();

                if (localPose != null) {
                    mapView.setRobotPose(localPose);
                    //雷达数据
                    LaserScan laserScan = RosDeviceManager.INSTANCE.getLaserScan();
                    if (laserScan != null) {
                        float[] ranges = laserScan.ranges;
                        LocalLaserScan localLaserScan = new LocalLaserScan();
                        localLaserScan.setPose(localPose);
                        Vector<LaserPoint> laserPoints = new Vector<>();
                        for (int i = 0; i < ranges.length; i++) {
                            LaserPoint laserPoint = new LaserPoint();
                            laserPoint.setAngle(ranges[i]);
                            laserPoint.setDistance(i * laserScan.angle_increment + localPose.getRotation().getYaw() + laserScan.angle_min);
                            laserPoints.add(laserPoint);
                        }
                        localLaserScan.setLaserPoints(laserPoints);
                        mapView.setLaserScan(localLaserScan);
                    }

                }
                LocalPose homePose = RosDeviceManager.INSTANCE.getHomePose();
                if (homePose != null) {
                    mapView.setHomePose(homePose);
                }
                Path path = RosDeviceManager.INSTANCE.getPath();
                if (path != null) {
                    mapView.setRemainingPath(path);
                }

            }
        });
    }


    public void setMapData(OccupancyGrid occupancyGrid) {
        Log.d("Ros:", "地图数据:" + mapMetaData.width);
        PointF pointO = new PointF();
        pointO.setX((float) mapMetaData.origin.position.x);
        pointO.setY((float) mapMetaData.origin.position.y);
        Size size = new Size();
        size.setWidth(mapMetaData.width);
        size.setHeight(mapMetaData.height);
        PointF pointR = new PointF();
        pointR.setY(mapMetaData.resolution);
        pointR.setX(mapMetaData.resolution);
        Map map = new Map(pointO, size, pointR, occupancyGrid.header.seq, occupancyGrid.data);
        mapView.setMap(map);
    }


    @Override
    protected void onDestroy() {
        if (rosServiceBroadcastReceiver != null) {
            unregisterReceiver(rosServiceBroadcastReceiver);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    //activity销毁
    @Override
    protected void onPause() {
        super.onPause();
    }


    private void initView(ConnectViewModel connectViewModel) {
        StyledDialog.init(this);
        initMapSpinnerView(connectViewModel);
        initTextFont();
        initHandleView(connectViewModel);
        initAppVersion();
        batteryView = (BatteryView) findViewById(R.id.battery);
        batteryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        batteryView.setPower(1f);
        batteryView.setmHeadWidth((int) (windowY * 0.84 * 0.035 * 0.7 * 0.3));
        batteryView.setmHeight((int) (windowY * 0.84 * 0.035));
        batteryView.setmWidth((int) (windowY * 0.84 * 0.035 * 0.7));

        mapView = findViewById(R.id.iv_map);
        mapView.setSingleTapListener(new MapView.ISingleTapListener() {
            @Override
            public void onSingleTapListener(MotionEvent event) {
                PointF target = mapView.widgetCoordinateToMapCoordinate(event.getX(), event.getY());
                if (target == null) {
                    return;
                }
                Location location = new Location(target.getX(), target.getY(), 0);

                if (connectViewModel.getOpMapType() == 3) {
                    RosDeviceManager.INSTANCE.resetPosition(target.getX(), target.getY(), 0);
                } else if (connectViewModel.getOpMapType() == 4) {
                    RosDeviceManager.INSTANCE.moveTo(location);
                }
            }
        });

    }


    //初始化APP版本信息
    private void initAppVersion() {
        TextView textView = (TextView) findViewById(R.id.titleName);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ConnectActivity.this);
                dialog.setContentView(R.layout.dialog_version);
                dialog.setTitle(getString(R.string.versionInfo));
                dialog.setCanceledOnTouchOutside(true);
                TextView versionText = dialog.findViewById(R.id.versionCaption);
                versionText.setText(VersionTexts.getVersionText(language));
                dialog.show();
            }
        });
    }

    //初始化虚拟摇杆
    private void initHandleView(ConnectViewModel connectViewModel) {
        //虚拟摇杆
        WebHandleView handleView = (WebHandleView) findViewById(R.id.handleView_Connect);

        RelativeLayout.LayoutParams handleViewLayoutParam = (RelativeLayout.LayoutParams) handleView.getLayoutParams();
        handleViewLayoutParam.width = (int) (windowX / 4.5);
        handleViewLayoutParam.height = (int) (windowX / 4.5);
        handleView.setLayoutParams(handleViewLayoutParam);

        RelativeLayout.LayoutParams thumbDivetLayoutParam = (RelativeLayout.LayoutParams) handleView.thumbDivet.getLayoutParams();
        thumbDivetLayoutParam.width = (int) ((windowX / 4.5) / 4);
        thumbDivetLayoutParam.height = (int) ((windowX / 4.5) / 4);
        handleView.thumbDivet.setLayoutParams(thumbDivetLayoutParam);

        RelativeLayout.LayoutParams centerDivetlayoutparam = (RelativeLayout.LayoutParams) handleView.center_divet.getLayoutParams();
        centerDivetlayoutparam.width = (int) ((windowX / 4.5) / 4);
        centerDivetlayoutparam.height = (int) ((windowX / 4.5) / 4);
        handleView.center_divet.setLayoutParams(centerDivetlayoutparam);

        handleView.setOnMoveListener(new WebHandleView.OnMoveListener() {
            @Override
            public void onMove(double linear, double anguler) {
                Log.d("Check", linear + "##" + anguler);

                if (linear == 0 && anguler == 0) {//都为0时停止移动
                    RosDeviceManager.INSTANCE.actionCancel();
                } else {
                    MoveDirection moveDirection = null;
                    if (0.75 < linear && -0.75 <= anguler && anguler <= 0.75) {//往前
                        moveDirection = MoveDirection.FORWARD;
                        Log.d("Check", "前");
                    } else if (-0.75 < linear && linear <= 0.75 && anguler < -0.75) {//往左
                        moveDirection = MoveDirection.TURN_LEFT;
                        Log.d("Check", "左");
                    } else if (linear <= -0.75 && -0.75 < anguler && anguler < 0.75) {//往后
                        moveDirection = MoveDirection.BACKWARD;
                        Log.d("Check", "后");
                    } else if (-0.75 < linear && linear < 0.75 && 0.75 <= anguler) {//往右
                        moveDirection = MoveDirection.TURN_RIGHT;
                        Log.d("Check", "右");
                    }
                    if (null != moveDirection) {
                        RosDeviceManager.INSTANCE.moveBy(moveDirection);
                    }
                }

            }
        });

        roundMenuView = findViewById(R.id.roundMenuView);
        for (int i = 0; i < 4; i++) {
            RoundMenuView.RoundMenu roundMenu = new RoundMenuView.RoundMenu();
            roundMenu.selectSolidColor = Utils.getColor(R.color.orange);
            roundMenu.strokeColor = Utils.getColor(R.color.orange);
            roundMenu.icon = drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.chevron_right));
            roundMenu.icon2 = drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.chevron_right_pressed));
            roundMenu.onClickListener = connectViewModel.onMenuClickListener;
            roundMenuView.addRoundMenu(roundMenu);
        }

        roundMenuView.setCoreMenu(Utils.getColor(R.color.orange),
                Utils.getColor(R.color.orange), Utils.getColor(R.color.orange)
                , 1, 0.43, drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.ic_launcher_round)), null);

    }


    //弹出工具箱
    private void initToolbox(ConnectViewModel connectViewModel) {
        List<BottomSheetBean> tools = new ArrayList<>();

        tools.add(new BottomSheetBean(R.drawable.btn_refresh, getString(R.string.refreshPage)));


        if (roundMenuView.getVisibility() == View.VISIBLE) {
            tools.add(new BottomSheetBean(R.drawable.btn_steeringwheel, getString(R.string.hiddenHandle)));
        } else {
            tools.add(new BottomSheetBean(R.drawable.btn_steeringwheel, getString(R.string.showHandle)));
        }
        tools.add(new BottomSheetBean(R.drawable.btn_refresh, "新建地图"));
        tools.add(new BottomSheetBean(R.drawable.btn_refresh, "重定位"));
        tools.add(new BottomSheetBean(R.drawable.btn_refresh, "地图导航"));
        tools.add(new BottomSheetBean(R.drawable.btn_refresh, "保存地图"));
        tools.add(new BottomSheetBean(R.drawable.btn_refresh, "充电"));
//        tools.add(new BottomSheetBean(R.drawable.btn_service_await, getString(R.string.sleepStandby)));
//        tools.add(new BottomSheetBean(R.drawable.btn_delete_map, getString(R.string.deleteMap)));
//        if (connectViewModel.obChargeChecked.get()) {
//            if (!connectViewModel.isCharging) {
//                tools.add(new BottomSheetBean(R.drawable.btn_recharge, getString(R.string.startCharging)));
//            } else {
//                tools.add(new BottomSheetBean(R.drawable.btn_recharge, getString(R.string.stopCharging)));
//            }
//        }
        StyledDialog.buildBottomSheetGv(getString(R.string.toolBox), tools, "this is cancle button", 4, connectViewModel.myItemDialogListener).show();
    }


    //初始化文字字体
    private void initTextFont() {
        TextView titleName = (TextView) findViewById(R.id.titleName);
        Typeface fzmwt = Typeface.createFromAsset(getAssets(), "fonts/hkwwt.ttf");
        titleName.setTypeface(fzmwt);
    }

    //初始化地图下拉菜单
    private void initMapSpinnerView(ConnectViewModel connectViewModel) {
        spinner = (Spinner) findViewById(R.id.spinnerMap);

        spinner.setOnItemSelectedListener(connectViewModel.onItemSelectedListener);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_text, connectViewModel.mapNames);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_selected);
        spinner.setAdapter(spinnerAdapter);
    }


    //前往建图导航界面
    private void goToMainActivity(Bundle bundle) {
        RosBridgeClientManager.INSTANCE.moveTo(0, 0, 0);
        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
        intent.putExtra("mode", bundle.getInt("mode"));
        intent.putExtra("ip", bundle.getString("ip"));
        intent.putExtra("mapName", bundle.getString("selectedMapName"));
        startActivityForResult(intent, 666);
    }

    //前往验收界面
    private void goToCheckActivity(String ip) {
        Intent intent = new Intent(ConnectActivity.this, CheckActivity.class);
        intent.putExtra("ip", ip);
        startActivityForResult(intent, 666);
    }


    //Intent意图或者权限等系统服务的处理结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StyledDialog.init(ConnectActivity.this);

    }


//    //处理按键事件
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    //连按两次来退出APP
//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            showShortToast(getString(R.string.clickOneMoreToClose));
//            exitTime = System.currentTimeMillis();
//        } else {
//            finish();
//        }
//    }

//    //防止横竖屏转换重新初始化
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        try {
//            super.onConfigurationChanged(newConfig);
//            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                Log.e("onConfigurationChanged", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
//            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                Log.e("onConfigurationChanged", "onConfigurationChanged_ORIENTATION_PORTRAIT");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


}
