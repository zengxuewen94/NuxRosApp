package com.nudtit.module_main.dialog;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;

import com.nudtit.androidrosbridgeclient.action.MoveDirection;
import com.nudtit.lib_common.base.BaseNewDialog;
import com.nudtit.lib_common.manager.ThreadPoolManager;
import com.nudtit.makejar.RobotControlConstant;
import com.nudtit.module_main.R;
import com.nudtit.module_main.listener.ControlMoveByListener;
import com.nudtit.module_main.widget.RockerView;


/**
 * @author :  zengxuewen
 * @date :  2022/5/16
 * @desc : 控制dialog
 */
public final class RockerActionDialog {
    public static final class Builder extends BaseNewDialog.Builder<RockerActionDialog.Builder> {

        private String TAG = RockerActionDialog.class.getSimpleName();
        private static final int TIME_CONTROL_MOVE_INTERVAL = 80;
        private static final int TIME_CONTROL_ROTATE_INTERVAL = 100;
        private boolean isStart;
        private RockerView.Direction mDirection;
        private int mClickCount;
        private MoveRunnable mMoveRunnable;
        private ControlMoveByListener mControlListener;

        public Builder(Activity activity) {
            super(activity);
            setContentView(R.layout.rocker_action_dailog);
            setAnimStyle(BaseNewDialog.ANIM_BOTTOM_INTO);
            setGravity(Gravity.BOTTOM);
            setBackgroundDimEnabled(false);
            RockerView rockerView = findViewById(R.id.rocker_control_dialog_view);
            rockerView.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
            rockerView.setOnShakeListener(RockerView.DirectionMode.DIRECTION_4_ROTATE_45, onShakeListener);
        }

        RockerView.OnShakeListener onShakeListener = new RockerView.OnShakeListener() {
            @Override
            public void onShakeStart() {

            }

            @Override
            public void direction(RockerView.Direction direction) {
                Log.i(TAG, "direction=" + direction);
                mDirection = direction;
                mClickCount = 0;
                isStart = true;
                mMoveRunnable = new MoveRunnable();
                ThreadPoolManager.getInstance().execute(mMoveRunnable);
            }

            @Override
            public void onFinish() {
                isStart = false;


            }
        };

        public Builder setControlListener(ControlMoveByListener controlListener) {
            mControlListener = controlListener;
            return this;
        }

        private class MoveRunnable implements Runnable {
            @Override
            public void run() {
                while (isStart) {
                    long delayTime;
                    // 左右控制的时候，间隔发送
                    if (mDirection == RockerView.Direction.DIRECTION_LEFT || mDirection == RockerView.Direction.DIRECTION_RIGHT) {
                        delayTime = TIME_CONTROL_ROTATE_INTERVAL;
                        if (mClickCount >= 1) {
                            mClickCount = 0;
                        } else {
                            mClickCount++;
                            controlMove(mDirection);
                        }
                    } else {
                        delayTime = TIME_CONTROL_MOVE_INTERVAL;
                        controlMove(mDirection);
                    }

                    if (isStart) {
                        try {
                            Thread.sleep(delayTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }

                Log.i(TAG, "cancel");
            }
        }

        private void controlMove(RockerView.Direction direction) {
            MoveDirection action = null;
            switch (direction) {
                case DIRECTION_LEFT:
                    action = MoveDirection.TURN_LEFT;
                    break;
                case DIRECTION_RIGHT:
                    action = MoveDirection.TURN_RIGHT;
                    break;
                case DIRECTION_UP:
                    action = MoveDirection.FORWARD;
                    break;
                case DIRECTION_DOWN:
                    action = MoveDirection.BACKWARD;
                    break;
                default:
                    break;
            }
            if (mControlListener != null) {
                mControlListener.controlMoveBy(action);
            }
        }
    }


}
