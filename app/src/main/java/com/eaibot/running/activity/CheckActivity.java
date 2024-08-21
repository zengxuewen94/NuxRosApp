package com.eaibot.running.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.eaibot.running.R;
import com.eaibot.running.constants.NumberCode;
import com.eaibot.running.databinding.ActivityCheckBinding;
import com.eaibot.running.manger.RosDeviceManager;
import com.eaibot.running.view.WebHandleView;
import com.eaibot.running.viewmodel.CheckViewModel;
import com.nudtit.androidrosbridgeclient.action.MoveDirection;

/**
 * @author Yist
 * 2018-06-19
 * 检测页面
 */
public class CheckActivity extends BaseActivity {

    private ConstraintLayout clParent;
    private ImageView[] sonar;
    //private ScanLayer scanLayer;
    //private Scan2Layer scan2Layer;
    //private RosService rosService;
//    private DriverRunPublisher driverRunPublisher;
//    private LayerContainer layerContainer;
//    private RechargePublisher rechargePublisher;

    private WebHandleView mHandleView;
    private TextView[] distanceTextViews;
    private ImageView hintFinger;                  //急停开关的状态

    private LinearLayout linearLayoutDistance;
    private int stopStatusCode = 0, changeVoltage = 1500, laserScan = 0, walkType = 0, lastX = 0, lastY = 0, chargeType = 0;
    private boolean[] sonarIsOut = {true, true, true, true, true}, sonarIsRed = {false, false, false, false, false}, sonarIsGreen = {false, false, false, false, false};
    private boolean canMove = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCheckBinding checkBinding = DataBindingUtil.setContentView(this, R.layout.activity_check);
        CheckViewModel checkViewModel = new ViewModelProvider(this).get(CheckViewModel.class);
        checkBinding.setCheckViewModel(checkViewModel);
        getLifecycle().addObserver(checkViewModel);

        initCheckView();

    }

    //初始化测试界面
    @SuppressLint("ClickableViewAccessibility")
    public void initCheckView() {
        clParent = findViewById(R.id.cl_check_parent);
        ImageView sonar1, sonar2, sonar3, sonar4, sonar5;
        sonar1 = (ImageView) findViewById(R.id.sonar1);
        sonar2 = (ImageView) findViewById(R.id.sonar2);
        sonar3 = (ImageView) findViewById(R.id.sonar3);
        sonar4 = (ImageView) findViewById(R.id.sonar4);
        sonar5 = (ImageView) findViewById(R.id.sonar5);

        hintFinger = (ImageView) findViewById(R.id.hint_finger);

        //layerContainer = (LayerContainer) findViewById(R.id.map_view);
        sonar = new ImageView[]{sonar1, sonar2, sonar3, sonar4, sonar5};
        //layerContainer = layerContainer.getLayerContainer(layerContainer);
        final Button btnShowHint = (Button) findViewById(R.id.btn_show_hint);
        //虚拟摇杆
        mHandleView = (WebHandleView) findViewById(R.id.dashgo_handle_control);
        TextView distanceSonar1 = (TextView) findViewById(R.id.distance_sonar1);
        TextView distanceSonar2 = (TextView) findViewById(R.id.distance_sonar2);
        TextView distanceSonar3 = (TextView) findViewById(R.id.distance_sonar3);
        TextView distanceSonar4 = (TextView) findViewById(R.id.distance_sonar4);
        TextView distanceSonar5 = (TextView) findViewById(R.id.distance_sonar5);
        linearLayoutDistance = (LinearLayout) findViewById(R.id.linearLayout_distance);
        distanceTextViews = new TextView[]{distanceSonar1, distanceSonar2, distanceSonar3, distanceSonar4, distanceSonar5};

        ConstraintLayout.LayoutParams mHandleViewLayoutParam = (ConstraintLayout.LayoutParams) mHandleView.getLayoutParams();
        mHandleViewLayoutParam.width = (int) (windowX / 4.5);
        mHandleViewLayoutParam.height = (int) (windowX / 4.5);
        mHandleView.setLayoutParams(mHandleViewLayoutParam);

        RelativeLayout.LayoutParams thumbDivetLayoutParam = (RelativeLayout.LayoutParams) mHandleView.thumbDivet.getLayoutParams();
        thumbDivetLayoutParam.width = (int) ((windowX / 4.5) / 4);
        thumbDivetLayoutParam.height = (int) ((windowX / 4.5) / 4);
        mHandleView.thumbDivet.setLayoutParams(thumbDivetLayoutParam);

        RelativeLayout.LayoutParams center_divetLayoutParam = (RelativeLayout.LayoutParams) mHandleView.center_divet.getLayoutParams();
        center_divetLayoutParam.width = (int) ((windowX / 4.5) / 4);
        center_divetLayoutParam.height = (int) ((windowX / 4.5) / 4);
        mHandleView.center_divet.setLayoutParams(center_divetLayoutParam);

        mHandleView.setOnMoveListener(new WebHandleView.OnMoveListener() {
            @Override
            public void onMove(double linear, double anguler) {

                if (canMove) {
                    //dashgoPublisher.publishVelocity(linear, 0, 0.6 * anguler);
                    if (linear == 0 && anguler == 0) {//都为0时停止移动
                        RosDeviceManager.INSTANCE.actionCancel();
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
            }
        });

        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0);
        ObjectAnimator.ofPropertyValuesHolder(hintFinger, pvhAlpha).setDuration(5000).start();

        linearLayoutDistance.setOnTouchListener((view, ev) -> {
            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    linearLayoutDistance.setBackgroundResource(R.drawable.distance_redstyle);
                    lastX = (int) ev.getRawX();//设定移动的初始位置相对位置
                    lastY = (int) ev.getRawY();
                    view.bringToFront();
                    break;

                case MotionEvent.ACTION_MOVE://移动
                    //event.getRawX()事件点距离屏幕左上角的距离
                    int dx = (int) ev.getRawX() - lastX;
                    int dy = (int) ev.getRawY() - lastY;
                    int left = view.getLeft() + dx;
                    int top = view.getTop() + dy;
                    int right = view.getRight() + dx;
                    int bottom = view.getBottom() + dy;
                    if (left < 0) { //最左边
                        left = 0;
                        right = left + view.getWidth();
                    }

                    if (right > windowX) { //最右边
                        right = windowX;
                        left = right - view.getWidth();
                    }
                    if (top < 0) { //最上边
                        top = 0;
                        bottom = top + view.getHeight();
                    }

                    if (bottom > windowY) {//最下边
                        bottom = windowY;
                        top = bottom - view.getHeight();
                    }
                    view.layout(left, top, right, bottom);

                    lastX = (int) ev.getRawX();//再次将滑动其实位置定位
                    lastY = (int) ev.getRawY();

                    break;
                case MotionEvent.ACTION_UP:
                    linearLayoutDistance.setBackgroundResource(R.drawable.distance_bluestyle);
                    //父布局是约束布局，因此设置ConstraintSet
                    ConstraintSet set = new ConstraintSet();
                    //clParent 是你的当前使用的约束布局
                    set.clone(clParent);
                    //设置linearLayout_distance控件的顶边与父布局的顶边对齐，且之间margin值是：
                    set.connect(R.id.linearLayout_distance, ConstraintSet.TOP, clParent.getId(), ConstraintSet.TOP, view.getTop());
                    set.connect(R.id.linearLayout_distance, ConstraintSet.START, clParent.getId(), ConstraintSet.START, view.getLeft());
                    //set.connect(R.id.linearLayout_distance,ConstraintSet.BOTTOM ,clParent.getId(),ConstraintSet.BOTTOM,view.getBottom());
                    //最后一步就是设置新的ConstraintLayout约束 这个必须设置;
                    set.applyTo(clParent);
                    break;
                default:
                    break;
            }

            return true;
        });


    }

    //检查对应超声波是否插上  i：0-4，对应5个超声波
    public void checkSonar(float v, int i) {
        if (v < NumberCode.MAX_SONAR_DISTANCE) {
            distanceTextViews[i].setText(((i + 1) == 1 ? getString(R.string.rightRear) : (i + 1) == 2
                    ? getString(R.string.rightFront) : (i + 1) == 3
                    ? getString(R.string.deadAhead) : (i + 1) == 4
                    ? getString(R.string.leftFront) : getString(R.string.leftRear)) + v + getString(R.string.meter));
            if (!sonarIsGreen[i]) {
                sonar[i].setBackgroundResource(R.drawable.greenhook);
                sonarIsGreen[i] = true;
                sonarIsRed[i] = false;
                sonarIsOut[i] = false;
            }
        } else if (v >= NumberCode.MAX_SONAR_DISTANCE && v != NumberCode.NO_SONAR_CODE) {
            distanceTextViews[i].setText(((i + 1) == 1 ? getString(R.string.rightRear) : (i + 1) == 2
                    ? getString(R.string.rightFront) : (i + 1) == 3
                    ? getString(R.string.deadAhead) : (i + 1) == 4
                    ? getString(R.string.leftFront) : getString(R.string.leftRear)) + ">=0.8" + getString(R.string.meter));
            if (!sonarIsRed[i]) {
                sonar[i].setBackgroundResource(R.drawable.redhock);
                sonarIsRed[i] = true;
                sonarIsGreen[i] = false;
                sonarIsOut[i] = false;
            }
        } else if (v == NumberCode.NO_SONAR_CODE) {
            distanceTextViews[i].setText(((i + 1) == 1 ? getString(R.string.rightRear) : (i + 1) == 2
                    ? getString(R.string.rightFront) : (i + 1) == 3
                    ? getString(R.string.deadAhead) : (i + 1) == 4
                    ? getString(R.string.leftFront) : getString(R.string.leftRear)) + getString(R.string.notInstalled));
            if (!sonarIsOut[i]) {
                sonar[i].setBackgroundResource(R.drawable.grayhook);
                sonarIsOut[i] = true;
                sonarIsRed[i] = false;
                sonarIsGreen[i] = false;
            }
        }
    }


    //销毁时关闭ros服务
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //防止横竖屏转换重新初始化
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.v("ORIENTATION", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.v("ORIENTATION", "onConfigurationChanged_ORIENTATION_PORTRAIT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
