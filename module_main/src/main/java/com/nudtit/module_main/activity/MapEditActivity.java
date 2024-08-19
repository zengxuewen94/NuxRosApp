package com.nudtit.module_main.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.nudtit.lib_common.utils.LogUtils;
import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivityMapEditBinding;
import com.nudtit.module_main.dialog.EraserDialog;
import com.nudtit.module_main.dialog.TrimPoseDialog;
import com.nudtit.module_main.dialog.VirtualTracksEditDialog;
import com.nudtit.module_main.dialog.VirtualWallEditDialog;
import com.nudtit.module_main.listener.TrimPoseListener;
import com.nudtit.module_main.listener.VirtualWallEditListener;
import com.nudtit.module_main.ruler.ScaleView;
import com.nudtit.module_main.ruler.ScrollRulerLayout;
import com.nudtit.module_main.viewmodel.MapEditViewModel;

/**
 * @author :  zengxuewen
 * @date :  2022/8/18
 * @desc :  地图编辑界面
 */
public class MapEditActivity extends BaseActivity {
    private static final String TAG = MapEditActivity.class.getSimpleName();
    // 虚拟墙编辑弹窗
    private VirtualWallEditDialog.Builder mVirtualWallEditDialog;
    // 虚拟轨道编辑弹窗
    private VirtualTracksEditDialog.Builder mVirtualTracksEditDialog;
    // 橡皮檫
    private EraserDialog.Builder mEraserDialog;
    // 【充电桩/机器人】位姿调整
    private TrimPoseDialog.Builder mTrimPoseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    private void bindView() {
        ActivityMapEditBinding mapEditBinding = bindView(R.layout.activity_map_edit);
        MapEditViewModel mapEditViewModel = getViewModel(MapEditViewModel.class);
        mapEditBinding.setMapEditViewModel(mapEditViewModel);
        observeLiveData(mapEditViewModel);
        ScrollRulerLayout scrollRulerLayout=findViewById(R.id.ruler_view);
        scrollRulerLayout.setScope(-180,180,10);
        scrollRulerLayout.setCurrentItem("0");
    }
    private void observeLiveData(MapEditViewModel mapEditViewModel) {
        mapEditViewModel.getActivityFinishVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        // 虚拟墙
        mapEditViewModel.getVirtualWallDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showVirtualWallDialog();
            }
        });
        // 虚拟轨道
        mapEditViewModel.getVirtualTrackDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showVirtualTracksDialog();
            }
        });
        // 橡皮檫
        mapEditViewModel.getEraserDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showEraserDialog();
            }
        });
        // 机器人位姿
        mapEditViewModel.getRobotPoseDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                closeTrimPoseDialog();
                showTrimPoseDialog(1);
            }
        });
        // 充电桩位姿
        mapEditViewModel.getCharePoseDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                closeTrimPoseDialog();
                showTrimPoseDialog(2);
            }
        });
    }

    /**
     * 显示橡皮檫弹框
     */
    private void showEraserDialog() {
        if (null == mEraserDialog) {
            mEraserDialog = new EraserDialog.Builder(this);
        }
        mEraserDialog.show();
    }


    /**
     * 关闭橡皮檫弹框
     */
    private void closeEraserDialog() {
        if (null != mEraserDialog) {
            mEraserDialog.dismiss();
            mEraserDialog = null;
        }
    }

    /**
     * @return 橡皮檫弹窗是否显示
     */
    private boolean isEraserDialogShow() {
        return null != mEraserDialog && mEraserDialog.isShowing();
    }

    /**
     * 显示橡皮檫弹框
     */
    private void showTrimPoseDialog(int type) {
        if (null == mTrimPoseDialog) {
            mTrimPoseDialog = new TrimPoseDialog.Builder(this).setTrimPoseListener(new TrimPoseListener() {
                @Override
                public void onAngle(int poseType, double angle) {
                    LogUtils.d(TAG, "角度:" + angle);
                }

                @Override
                public void onPose(int poseType) {

                }

                @Override
                public void confirm(int type) {

                }
            });
        }
        mTrimPoseDialog.setType(type).show();
    }


    /**
     * 关闭橡皮檫弹框
     */
    private void closeTrimPoseDialog() {
        if (null != mTrimPoseDialog) {
            mTrimPoseDialog.dismiss();
            mTrimPoseDialog = null;
        }
    }

    /**
     * @return 橡皮檫弹窗是否显示
     */
    private boolean isTrimPoseDialogShow() {
        return null != mTrimPoseDialog && mTrimPoseDialog.isShowing();
    }

    /**
     * 显示虚拟墙编辑弹框
     */
    private void showVirtualWallDialog() {
        if (null == mVirtualWallEditDialog) {
            mVirtualWallEditDialog = new VirtualWallEditDialog.Builder(this).setVirtualWallEditListener(new VirtualWallEditListener() {
                @Override
                public void delete() {

                }

                @Override
                public void areaDelete() {

                }

                @Override
                public void addSteepWall() {

                }

                @Override
                public void addCurveWall() {

                }
            });
        }
        mVirtualWallEditDialog.show();
    }


    /**
     * 关闭虚拟墙编辑弹框
     */
    private void closeVirtualWallDialog() {
        if (null != mVirtualWallEditDialog) {
            mVirtualWallEditDialog.dismiss();
            mVirtualWallEditDialog = null;
        }
    }

    /**
     * @return 虚拟墙弹窗是否显示
     */
    private boolean isVirtualWallDialogShow() {
        return null != mVirtualWallEditDialog && mVirtualWallEditDialog.isShowing();
    }


    /**
     * 显示地图编辑弹框
     */
    private void showVirtualTracksDialog() {
        if (null == mVirtualTracksEditDialog) {
            mVirtualTracksEditDialog = new VirtualTracksEditDialog.Builder(this).setVirtualWallEditListener(new VirtualWallEditListener() {
                @Override
                public void delete() {

                }

                @Override
                public void areaDelete() {

                }

                @Override
                public void addSteepWall() {

                }

                @Override
                public void addCurveWall() {

                }
            });
        }
        mVirtualTracksEditDialog.show();
    }


    /**
     * 关闭地图编辑弹框
     */
    private void closeVirtualTracksDialog() {
        if (null != mVirtualTracksEditDialog) {
            mVirtualTracksEditDialog.dismiss();
            mVirtualTracksEditDialog = null;
        }
    }

    /**
     * @return 地图编辑弹窗是否显示
     */
    private boolean isVirtualTracksShow() {
        return null != mVirtualTracksEditDialog && mVirtualTracksEditDialog.isShowing();
    }

    @Override
    protected void onPause() {
        closeEraserDialog();
        closeTrimPoseDialog();
        closeVirtualTracksDialog();
        closeVirtualWallDialog();
        super.onPause();
    }
}
