package com.eaibot.running.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.eaibot.running.R;
import com.eaibot.running.adapter.RecyclerViewAdapter;
import com.eaibot.running.bean.ShowAddPointDialogVo;
import com.eaibot.running.constants.RosMode;
import com.eaibot.running.databinding.ActivityMainBinding;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.dialog.LocationListDialog;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.view.BatteryView;
import com.eaibot.running.view.StyledDialog;
import com.eaibot.running.view.WebHandleView;
import com.eaibot.running.viewmodel.MainViewModel;
import com.eaibot.running.widget.RoundMenuView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.lib_common.utils.PermissionUtil;
import com.nudtit.lib_common.utils.Utils;
import com.nudtit.lib_common.widget.ConfirmDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.nudtit.lib_common.utils.BitmapUtil.drawableToBitmap;


/**
 * @author Administrator
 * <p>
 * 建图和导航页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    //基本变量
    private int singlePoseIndex = -1;                               //当前单点导航的点的下标
    private boolean isMuiltNavMarkPoseListChanged = true;           //多点导航点集合是否发生改变
    private int addGoalQueueIndex = 0;                              //循环添加导航队列时的进度

    private int voltageChangeTimes = 3000;                          //电量变化次数
    private boolean isCharging = false;                             //是否正在充电
    private boolean lastIsCharging = false;                         //上一次的充电状态

    private int lastVoltagePercentage = 0;                          //上一次的电量
    private int currentPoseListHeight = 0;                          //当前目标点列表的高度
    private boolean poseListIsShow = false;                         //目标点列表是否已经显示
    private int poseType = 0;                                       //0:设置初始点  1:设置目标点  2:设置充电桩
    private int lastPoseType = 0;                                   //上一次点的类型
    // private List<MarkPose> chargePoseList = new ArrayList<>();      //充电点集合
    public static List<SlamLocationBean> markPoseList = new ArrayList<>();  //导航点集合
    private boolean isMapAddPose = true;                            //点击地图是否为设置点
    private boolean isMapClick = false;                              //点击地图是否为移动/缩放地图

    private int mode = 0;

    //特殊变量
    //private RobotPose lastRobotPose;
    private DecimalFormat df = new DecimalFormat("######0.00");       //保留4位小数
    //控件相关

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView poseRecyclerView;
    private LinearLayout llPoseOptions;

    private Button poseList;
    private LinearLayout llChargeOptions;
    private Button chargeOptions;

    private Button saveMap;
    private LinearLayout llMapOptions;
    private Button mapOptions;
    // 手柄
    private WebHandleView handleView;
    private BatteryView batteryView;
    private Button paint, edit, undo, commit;

    //地图列表
    private LocationListDialog.Builder mLocationListDialog;
    private MainViewModel mainViewModel;
    private int mType;
    private List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainBinding.setMainViewModel(mainViewModel);
        getLifecycle().addObserver(mainViewModel);
        initData();
        initPublicView();
        initViewData();
        observeViewModel();
    }


    private void initViewData() {
        switch (mode) {
            case RosMode.GMAPPINGIMUSTART:
            case RosMode.GMAPPINGSTART:
            case RosMode.CARTOGRAPHERSTART://建图
                initGmappingView();
                break;
            case RosMode.NAVIGATIONIMUSTART:
            case RosMode.NAVIGATIONSTART:
            case RosMode.NAVIGATIONIMUNORECHARGESTART:
            case RosMode.NAVIGATIONNORECHARGESTART://导航
                initNavigationView();
                break;
            default:
                break;
        }
    }

    private void observeViewModel() {
        // 电量
        mainViewModel.getBatteryValueVo().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                batteryView.setPower(integer / 100f);
            }
        });
        // 充电状态
        mainViewModel.getChargeStatusVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                batteryView.setIsCharging(aBoolean);
            }
        });

        // 显示位置点列表
        mainViewModel.getShowAddPointDialogVo().observe(this, new Observer<ShowAddPointDialogVo>() {
            @Override
            public void onChanged(ShowAddPointDialogVo vo) {
                List<SlamLocationBean> list = vo.getList();
                int type = vo.getType();
                if (type == 2 && (list == null || list.isEmpty())) {
                    showShortToast("暂无位置点");
                }
                showPointListDialog(type, list, mainViewModel);
            }
        });
        mainViewModel.getRequestPer().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer type) {
                MainActivity.this.mType = type;
                //getRWPermission();
                onRequestPermission();
            }
        });
    }

    private void onRequestPermission() {
        permissionList = PermissionUtil.getPermissionList(this);
        if (permissionList.isEmpty()) {
            mainViewModel.queryLocationList(mType);
        } else {
            XXPermissions.with(MainActivity.this)
                    .permission(permissionList)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                mainViewModel.queryLocationList(mType);
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            showConfirmDialog(false, never, permissions);
                        }
                    });
        }
    }

    /**
     * @param request 是否需要提示申请权限
     * @param never   权限是否永远被拒绝
     * @param denied
     */
    private void showConfirmDialog(boolean request, boolean never, List<String> denied) {
        String content;
        if (request) {
            content = "应用将需要申请\"" + PermissionUtil.getPermissionContent(this) + "\"权限";
        } else {
            if (never) {
                content = "请在\"设置-应用-" + Utils.getString(R.string.app_name) + "-权限管理\"中开启\"" + PermissionUtil.getPermissionContent(this) + "\"权限";
            } else {
                content = "请授予\"" + PermissionUtil.getPermissionContent(this) + "\"权限，否则应用无法使用";
            }
        }

        new ConfirmDialog.Builder(this)
                .setTitle("权限申请")
                .setContent(content)
                .setListener(new ConfirmDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseNewDialog dialog) {
                        if (request) {
                            onRequestPermission();
                        } else {
                            if (never) {
                                XXPermissions.startPermissionActivity(MainActivity.this, denied);
                            } else {
                                onRequestPermission();
                            }
                        }
                    }

                    @Override
                    public void onCancel(BaseNewDialog dialog) {
                        finish();
                    }
                }).show();

    }


    /**
     * 显示位置点列表弹窗
     *
     * @param slamLocationBeans
     */
    private void showPointListDialog(int type, List<SlamLocationBean> slamLocationBeans, MainViewModel mainViewModel) {
        if (null == mLocationListDialog) {
            mLocationListDialog = new LocationListDialog.Builder(this);
        }

        mLocationListDialog.setOnPointListener(mainViewModel.onPointListener);
        mLocationListDialog.addOnDismissListener(new BaseNewDialog.OnDismissListener() {
            @Override
            public void onDismiss(BaseNewDialog dialog) {
                mainViewModel.obHandleViewShow.set(true);
            }
        });
        mLocationListDialog.setData(type, slamLocationBeans).show();
        mainViewModel.obHandleViewShow.set(false);
    }


    //加载公有控件
    private void initPublicView() {
        StyledDialog.init(this);
        batteryView = (BatteryView) findViewById(R.id.battery);
        batteryView.setPower(1f);
        batteryView.setmHeadWidth((int) (windowY * 0.84 * 0.035 * 0.7 * 0.3));
        batteryView.setmHeight((int) (windowY * 0.84 * 0.035));
        batteryView.setmWidth((int) (windowY * 0.84 * 0.035 * 0.7));
        saveMap = (Button) findViewById(R.id.saveMap);
        llMapOptions = (LinearLayout) findViewById(R.id.ll_mapOptions);
        mapOptions = (Button) findViewById(R.id.mapOptions);
        llChargeOptions = (LinearLayout) findViewById(R.id.ll_chargeOptions);
        chargeOptions = (Button) findViewById(R.id.chargeOptions);

        poseList = (Button) findViewById(R.id.poseList);
        llPoseOptions = (LinearLayout) findViewById(R.id.llPoseOptions);
        poseRecyclerView = (RecyclerView) findViewById(R.id.poseRecyclerView);

        paint = (Button) findViewById(R.id.paint);
        edit = (Button) findViewById(R.id.edit);
        undo = (Button) findViewById(R.id.undo);
        commit = (Button) findViewById(R.id.commit);

        llPoseOptions.setVisibility(View.GONE);
        saveMap.setVisibility(View.GONE);
        mapOptions.setVisibility(View.GONE);
        chargeOptions.setVisibility(View.VISIBLE);
        llMapOptions.setVisibility(View.GONE);
        llChargeOptions.setVisibility(View.GONE);

        initHandleView();

    }




    //加载建图模式下控件
    private void initGmappingView() {
        mapOptions.setVisibility(View.GONE);
        llMapOptions.setVisibility(View.GONE);
        llChargeOptions.setVisibility(View.GONE);

        saveMap.setVisibility(View.VISIBLE);
        saveMap.setOnClickListener(this);
    }


    //加载导航模式下控件
    private void initNavigationView() {

        poseList.setOnClickListener(this);

        saveMap.setVisibility(View.GONE);
        mapOptions.setVisibility(View.VISIBLE);
        mapOptions.setOnClickListener(this);
        paint.setOnClickListener(this);
        undo.setOnClickListener(this);
        commit.setOnClickListener(this);
        edit.setOnClickListener(this);

        if (mode == RosMode.NAVIGATIONIMUSTART || mode == RosMode.NAVIGATIONSTART) {
            //chargeOptions.setVisibility(View.VISIBLE);
            chargeOptions.setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapOptions://控制其他控件显示隐藏
                if (llMapOptions.getVisibility() == View.VISIBLE) {
                    PropertyValuesHolder pvhAlphaShow = PropertyValuesHolder.ofFloat("alpha", 1f, 0.15f, 0.1f, 0.05f, 0f, 0f, 0f, 0f);
                    PropertyValuesHolder pvhTranslationXShow = PropertyValuesHolder.ofFloat("translationX", 0f, (int) (MainActivity.this.getResources().getDisplayMetrics().density * 100 + 0.5f));
                    PropertyValuesHolder pvhTranslationYShow = PropertyValuesHolder.ofFloat("translationY", 0f, -(int) (MainActivity.this.getResources().getDisplayMetrics().density * 142 + 0.5f));
                    ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(llMapOptions, pvhTranslationXShow, pvhAlphaShow);
                    animator1.setDuration(300);
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {        //动画完成时隐藏按钮
                            llMapOptions.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator1.start();
                    ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(llPoseOptions, pvhTranslationYShow, pvhAlphaShow);
                    animator2.setDuration(300);
                    animator2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {        //动画完成时隐藏按钮
                            llPoseOptions.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator2.start();
                } else {
                    llMapOptions.setVisibility(View.VISIBLE);
                    llPoseOptions.setVisibility(View.VISIBLE);
                    PropertyValuesHolder pvhAlphaShow = PropertyValuesHolder.ofFloat("alpha", 0f, 0f, 0f, 0f, 0.05f, 0.1f, 0.15f, 1f);
                    PropertyValuesHolder pvhTranslationXShow = PropertyValuesHolder.ofFloat("translationX", (int) (MainActivity.this.getResources().getDisplayMetrics().density * 100 + 0.5f), 0f);
                    PropertyValuesHolder pvhTranslationYShow = PropertyValuesHolder.ofFloat("translationY", -(int) (MainActivity.this.getResources().getDisplayMetrics().density * 142 + 0.5f), 0f);
                    ObjectAnimator.ofPropertyValuesHolder(llMapOptions, pvhTranslationXShow, pvhAlphaShow).setDuration(300).start();
                    ObjectAnimator.ofPropertyValuesHolder(llPoseOptions, pvhTranslationYShow, pvhAlphaShow).setDuration(300).start();
                }
                break;
            case R.id.chargeOptions://控制充电相关控件显示隐藏
                if (llChargeOptions.getVisibility() == View.VISIBLE) {
                    PropertyValuesHolder pvhAlphaShow = PropertyValuesHolder.ofFloat("alpha", 1f, 0.15f, 0.1f, 0.05f, 0f, 0f, 0f, 0f);
                    PropertyValuesHolder pvhTranslationXShow = PropertyValuesHolder.ofFloat("translationX", 0f, (int) (MainActivity.this.getResources().getDisplayMetrics().density * 175 + 0.5f));
                    ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(llChargeOptions, pvhTranslationXShow, pvhAlphaShow);
                    animator1.setDuration(300);
                    animator1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {        //动画完成时隐藏按钮
                            llChargeOptions.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator1.start();
                } else {
                    llChargeOptions.setVisibility(View.VISIBLE);
                    PropertyValuesHolder pvhAlphaShow = PropertyValuesHolder.ofFloat("alpha", 0f, 0f, 0f, 0f, 0.05f, 0.1f, 0.15f, 1f);
                    PropertyValuesHolder pvhTranslationXShow = PropertyValuesHolder.ofFloat("translationX", (int) (MainActivity.this.getResources().getDisplayMetrics().density * 175 + 0.5f), 0f);
                    ObjectAnimator.ofPropertyValuesHolder(llChargeOptions, pvhTranslationXShow, pvhAlphaShow).setDuration(300).start();
                }
                break;
            case R.id.poseList:
                if (!poseListIsShow) {
                    if (markPoseList.size() > 0) {
                        if (isMapClick) {
                            //goalRechargeLayer.setIsSetGoal(false);
                        }
                        ValueAnimator va = ValueAnimator.ofInt(0, (int) (0.35 * windowY));
                        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                poseRecyclerView.getLayoutParams().height = (int) (Integer) valueAnimator.getAnimatedValue();
                                currentPoseListHeight = poseRecyclerView.getLayoutParams().height;
                                poseRecyclerView.requestLayout();
                            }
                        });
                        va.setDuration(600);
                        va.start();
                        poseListIsShow = true;
                    } else {
                        //toastLast(getStringByResource(R.string.noSavedPose));
                    }
                } else {
                    //goalRechargeLayer.setIsSetGoal(true);
                    ValueAnimator va = ValueAnimator.ofInt(currentPoseListHeight, 0);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            poseRecyclerView.getLayoutParams().height = (int) (Integer) valueAnimator.getAnimatedValue();
                            currentPoseListHeight = poseRecyclerView.getLayoutParams().height;
                            poseRecyclerView.requestLayout();
                        }
                    });
                    va.setDuration(600);
                    va.start();
                    poseListIsShow = false;
                }
                break;
            default:
                break;
        }
    }


    private void initData() {
        Intent intent = this.getIntent();
        mode = intent.getIntExtra("mode", 0);

    }

    //根据id获取strings.xml中的文字
    public String getStringByResource(int resourceId) {
        return this.getResources().getString(resourceId);
    }


    //初始化虚拟摇杆
    private void initHandleView() {
        //虚拟摇杆
        handleView = (WebHandleView) findViewById(R.id.handleView_Main);

        FrameLayout.LayoutParams handleViewLayoutParam = (FrameLayout.LayoutParams) handleView.getLayoutParams();
        handleViewLayoutParam.width = (int) (windowX / 4.5);
        handleViewLayoutParam.height = (int) (windowX / 4.5);
        handleView.setLayoutParams(handleViewLayoutParam);

        RelativeLayout.LayoutParams thumbDivetLayoutParam = (RelativeLayout.LayoutParams) handleView.thumbDivet.getLayoutParams();
        thumbDivetLayoutParam.width = (int) ((windowX / 4.5) / 4);
        thumbDivetLayoutParam.height = (int) ((windowX / 4.5) / 4);
        handleView.thumbDivet.setLayoutParams(thumbDivetLayoutParam);

        RelativeLayout.LayoutParams center_divetLayoutParam = (RelativeLayout.LayoutParams) handleView.center_divet.getLayoutParams();
        center_divetLayoutParam.width = (int) ((windowX / 4.5) / 4);
        center_divetLayoutParam.height = (int) ((windowX / 4.5) / 4);
        handleView.center_divet.setLayoutParams(center_divetLayoutParam);

        handleView.setOnMoveListener(new WebHandleView.OnMoveListener() {
            @Override
            public void onMove(double linear, double anguler) {

                if (linear == 0 && anguler == 0) {//都为0时停止移动
                    RosDeviceManager.INSTANCE.cancelAllAction();
                } else {
                    MoveDirection moveDirection = null;
                    if (0.75 < linear && -0.75 <= anguler && anguler <= 0.75) {//往前
                        moveDirection = MoveDirection.FORWARD;
                    } else if (-0.75 < linear && linear <= 0.75 && anguler < -0.75) {//往左
                        moveDirection = MoveDirection.TURN_LEFT;
                    } else if (linear <= -0.75 && -0.75 < anguler && anguler < 0.75) {//往后
                        moveDirection = MoveDirection.BACKWARD;
                    } else if (-0.75 < linear && linear < 0.75 && 0.75 <= anguler) {//往右
                        moveDirection = MoveDirection.TURN_RIGHT;
                    }
                    if (null != moveDirection) {
                        RosDeviceManager.INSTANCE.moveBy(moveDirection);
                    }
                }
            }

        });

        RoundMenuView roundMenuView = findViewById(R.id.roundMenuView);
        for (int i = 0; i < 4; i++) {
            RoundMenuView.RoundMenu roundMenu = new RoundMenuView.RoundMenu();
            roundMenu.selectSolidColor = Utils.getColor(R.color.orange);
            roundMenu.strokeColor = Utils.getColor(R.color.orange);
            roundMenu.icon = drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.chevron_right));
            roundMenu.icon2 = drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.chevron_right_pressed));
            roundMenu.onClickListener =mainViewModel.onMenuClickListener;
            roundMenuView.addRoundMenu(roundMenu);
        }

        roundMenuView.setCoreMenu(Utils.getColor(R.color.orange),
                Utils.getColor(R.color.orange), Utils.getColor(R.color.orange)
                , 1, 0.43, drawableToBitmap(Utils.getContext().getDrawable(R.mipmap.ic_launcher_round)), null);

    }


    //activity销毁
    @Override
    protected void onDestroy() {
        markPoseList.clear();
        if (null != mLocationListDialog) {
            mLocationListDialog.dismiss();
            mLocationListDialog = null;
        }

        super.onDestroy();
    }


    //Intent意图或者权限等系统服务的处理结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (!XXPermissions.isGranted(this, permissionList)) {
            onRequestPermission();
        } else {
            mainViewModel.queryLocationList(mType);
        }
    }


    //防止横竖屏转换重新初始化
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.e("onConfigurationChanged", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.e("onConfigurationChanged", "onConfigurationChanged_ORIENTATION_PORTRAIT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
