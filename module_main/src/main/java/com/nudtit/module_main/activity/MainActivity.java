package com.nudtit.module_main.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;

import com.nudtit.lib_common.utils.ToastUtil;
import com.nudtit.module_main.R;
import com.nudtit.module_main.base.BaseActivity;
import com.nudtit.module_main.databinding.ActivityMainBinding;
import com.nudtit.module_main.dialog.EraserDialog;
import com.nudtit.module_main.dialog.MapEditDialog;
import com.nudtit.module_main.dialog.MapOperateDialog;
import com.nudtit.module_main.dialog.MapSaveDialog;
import com.nudtit.module_main.dialog.RockerActionDialog;
import com.nudtit.module_main.dialog.VirtualTracksEditDialog;
import com.nudtit.module_main.dialog.VirtualWallEditDialog;
import com.nudtit.module_main.listener.MapEditListener;
import com.nudtit.module_main.listener.MapOperateListener;
import com.nudtit.module_main.listener.MapSaveListener;
import com.nudtit.module_main.listener.VirtualWallEditListener;
import com.nudtit.module_main.viewmodel.MainViewModel;


/**
 * @author zengxuewen
 * @date 2022/08/10
 * @desc 首页
 */
public class MainActivity extends BaseActivity {
    // 返回键首次点击时间
    private long firstOutTime = 0;
    // 返回键点击次数
    private int firstOutCount = 0;
    private LinearLayout llBottomBar;
    // 控制-前后左右移动弹窗
    private RockerActionDialog.Builder mRockerActionDialog;
    // 地图操作弹窗
    private MapOperateDialog.Builder mMapOperateDialog;
    // 地图编辑弹窗
    private MapEditDialog.Builder mMapEditDialog;
    // 虚拟墙编辑弹窗
    private VirtualWallEditDialog.Builder mVirtualWallEditDialog;
    // 虚拟轨道编辑弹窗
    private VirtualTracksEditDialog.Builder mVirtualTracksEditDialog;
    // 橡皮檫
    private EraserDialog.Builder mEraserDialog;
    // 保存地图
    private MapSaveDialog.Builder mMapSaveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding();
    }

    private void binding() {
        ActivityMainBinding mainBinding = bindView(R.layout.activity_main);
        llBottomBar = mainBinding.llMainBottomNavbar;
        MainViewModel mainViewModel = getViewModel(MainViewModel.class);
        mainBinding.setMainViewModel(mainViewModel);
        observeViewModel(mainViewModel);
        getLifecycle().addObserver(mainViewModel);
    }

    /**
     * 订阅LiveData数据
     *
     * @param mainViewModel
     */
    private void observeViewModel(MainViewModel mainViewModel) {
        // 地图
        mainViewModel.getMapDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (isMapOperateDialogShow()) {
                    closeMapOperateDialog();
                } else {
                    showMapOperateDialog(mainViewModel);
                }
            }
        });
        // 编辑
        mainViewModel.getEditDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (isMapEditDialogShow()) {
                    closeMapEditDialog();
                } else {
                    showMapEditDialog(mainViewModel);
                }
            }
        });
        //控制-前后左右动作
        mainViewModel.getActionDialogVo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (isRockerActionDialogShow()) {
                    closeRockerActionDialog();
                } else {
                    showRockerActionDialog(mainViewModel);
                }
            }
        });

        mainViewModel.getActivityFinishVo().observe(this, aBoolean -> {
            if (aBoolean) {
                onStartActivity(OfflineLoginActivity.class);
            }
            finish();
        });
        mainViewModel.getJumpToRobotInfo().observe(this, aBoolean ->
                onStartActivity(RobotInfoActivity.class));
        mainViewModel.getJumpToSetting().observe(this, aBoolean -> onStartActivity(SettingActivity.class));
    }


    /**
     * 显示控制弹框
     *
     * @param mainViewModel
     */
    private void showRockerActionDialog(MainViewModel mainViewModel) {
        if (null == mRockerActionDialog) {
            mRockerActionDialog = new RockerActionDialog.Builder(this).setControlListener(mainViewModel::moveBy);
        }
        mRockerActionDialog.setYoffset(getDialogYOffset()).show();
    }


    /**
     * 关闭控制弹框
     */
    private void closeRockerActionDialog() {
        if (null != mRockerActionDialog) {
            mRockerActionDialog.dismiss();
            mRockerActionDialog = null;
        }
    }

    /**
     * @return 控制弹窗是否显示
     */
    private boolean isRockerActionDialogShow() {
        return null != mRockerActionDialog && mRockerActionDialog.isShowing();
    }


    /**
     * 显示地图操作弹框
     *
     * @param mainViewModel
     */
    private void showMapOperateDialog(MainViewModel mainViewModel) {
        if (null == mMapOperateDialog) {
            mMapOperateDialog = new MapOperateDialog.Builder(this).setMapOperateListener(mapOperateListener);
        }
        mMapOperateDialog.setYoffset(getDialogYOffset()).show();
    }


    /**
     * 关闭地图操作弹框
     */
    private void closeMapOperateDialog() {
        if (null != mMapOperateDialog) {
            mMapOperateDialog.dismiss();
            mMapOperateDialog = null;
        }
    }

    /**
     * @return 地图操作弹窗是否显示
     */
    private boolean isMapOperateDialogShow() {
        return null != mMapOperateDialog && mMapOperateDialog.isShowing();
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
     * 显示地图编辑弹框
     *
     * @param mainViewModel
     */
    private void showMapEditDialog(MainViewModel mainViewModel) {
        if (null == mMapEditDialog) {
            mMapEditDialog = new MapEditDialog.Builder(this).setMapEditListener(mapEditListener);
        }
        mMapEditDialog.setYoffset(getDialogYOffset()).show();
    }


    /**
     * 关闭地图编辑弹框
     */
    private void closeMapEditDialog() {
        if (null != mMapEditDialog) {
            mMapEditDialog.dismiss();
            mMapEditDialog = null;
        }
    }

    /**
     * @return 地图编辑弹窗是否显示
     */
    private boolean isMapEditDialogShow() {
        return null != mMapEditDialog && mMapEditDialog.isShowing();
    }


    /**
     * 显示地图保存弹框
     */
    private void showMapSaveDialog() {
        if (null == mMapSaveDialog) {
            mMapSaveDialog = new MapSaveDialog.Builder(this).setMapSaveListener(new MapSaveListener() {
                @Override
                public void onSave(String mapName) {
                    //TODO

                }
            });
        }
        mMapSaveDialog.show();
    }


    /**
     * 关闭地图保存弹框
     */
    private void closeMapSaveDialog() {
        if (null != mMapSaveDialog) {
            mMapSaveDialog.dismiss();
            mMapSaveDialog = null;
        }
    }

    /**
     * @return 地图保存弹窗是否显示
     */
    private boolean isMapSaveDialogShow() {
        return null != mMapSaveDialog && mMapSaveDialog.isShowing();
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


    /**
     * 地图操作监听事件
     */
    public MapOperateListener mapOperateListener = new MapOperateListener() {
        /**
         * 清除地图
         */
        @Override
        public void onClearMap() {
            //todo
        }

        /**
         * 保存地图
         */
        @Override
        public void onSaveMap() {
            //todo
            showMapSaveDialog();
        }

        /**
         * 重定位
         */
        @Override
        public void onReLocation() {
            //todo
        }

        @Override
        public void onEdit() {
            onStartActivity(MapEditActivity.class);
        }
    };


    /**
     * 地图编辑监听
     */
    public MapEditListener mapEditListener = new MapEditListener() {
        /**
         * 虚拟墙
         */
        @Override
        public void onVirtualWall() {
            showVirtualWallDialog();
        }

        /**
         * 虚拟轨道
         */
        @Override
        public void onVirtualTracks() {
            showVirtualTracksDialog();
        }

        /**
         * 橡皮檫
         */
        @Override
        public void onRubber() {
            showEraserDialog();
        }

    };


    /**
     * @return dialog 弹窗位置
     */
    private int getDialogYOffset() {
        return llBottomBar.getMeasuredHeight() + 5;
    }


    private void closeAllDialog() {
        closeRockerActionDialog();
        closeEraserDialog();
        closeMapEditDialog();
        closeMapOperateDialog();
        closeVirtualTracksDialog();
        closeVirtualWallDialog();
        closeMapSaveDialog();
    }

    @Override
    protected void onPause() {
        closeAllDialog();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        // 如果有弹窗在显示，先关闭弹窗
        if (isEraserDialogShow() || isMapEditDialogShow() || isVirtualTracksShow()
                || isVirtualWallDialogShow() || isRockerActionDialogShow() || isMapOperateDialogShow()
                ||isMapSaveDialogShow()) {
            closeAllDialog();
            return;
        }
        long doubleOutTime = System.currentTimeMillis();
        if (doubleOutTime - firstOutTime < 1000) {
            if (firstOutCount >= 1) {
                firstOutCount = 0;
                firstOutTime = 0;
                finish();
            }
        } else {
            ToastUtil.showShort(getString(R.string.app_exit_tip));
            firstOutCount++;
        }
        firstOutTime = doubleOutTime;
    }
}