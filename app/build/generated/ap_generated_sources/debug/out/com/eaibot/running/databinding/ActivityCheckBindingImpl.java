package com.eaibot.running.databinding;
import com.eaibot.running.R;
import com.eaibot.running.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCheckBindingImpl extends ActivityCheckBinding implements com.eaibot.running.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.view_center, 16);
        sViewsWithIds.put(R.id.distance_sonar1, 17);
        sViewsWithIds.put(R.id.distance_sonar2, 18);
        sViewsWithIds.put(R.id.distance_sonar3, 19);
        sViewsWithIds.put(R.id.distance_sonar4, 20);
        sViewsWithIds.put(R.id.distance_sonar5, 21);
        sViewsWithIds.put(R.id.ll_check_sonar, 22);
        sViewsWithIds.put(R.id.sonar1, 23);
        sViewsWithIds.put(R.id.sonar2, 24);
        sViewsWithIds.put(R.id.sonar3, 25);
        sViewsWithIds.put(R.id.sonar4, 26);
        sViewsWithIds.put(R.id.sonar5, 27);
        sViewsWithIds.put(R.id.hint_finger, 28);
        sViewsWithIds.put(R.id.stop_status, 29);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    @Nullable
    private final android.view.View.OnClickListener mCallback10;
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCheckBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }
    private ActivityCheckBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 14
            , (android.widget.Button) bindings[4]
            , (android.widget.ImageButton) bindings[1]
            , (android.widget.ImageButton) bindings[3]
            , (android.widget.Button) bindings[10]
            , (android.widget.ImageButton) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (com.eaibot.running.view.WebHandleView) bindings[11]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[21]
            , (android.widget.ImageView) bindings[28]
            , (android.widget.TextView) bindings[12]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.ImageView) bindings[26]
            , (android.widget.ImageView) bindings[27]
            , (android.widget.TextView) bindings[9]
            , (android.widget.ImageView) bindings[29]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[15]
            , (android.view.View) bindings[16]
            );
        this.btnChangeLaserscan.setTag(null);
        this.btnGo1m.setTag(null);
        this.btnRecharge.setTag(null);
        this.btnShowHint.setTag(null);
        this.btnTurn360.setTag(null);
        this.clCheckParent.setTag(null);
        this.dashgoHandleControl.setTag(null);
        this.imuText.setTag(null);
        this.linearLayoutDistance.setTag(null);
        this.linearLayoutHintSonar.setTag(null);
        this.linearLayoutHintVoltage.setTag(null);
        this.robotPose.setTag(null);
        this.robotState.setTag(null);
        this.sonarTitle.setTag(null);
        this.textView.setTag(null);
        this.textVoltage.setTag(null);
        setRootTag(root);
        // listeners
        mCallback9 = new com.eaibot.running.generated.callback.OnClickListener(this, 3);
        mCallback8 = new com.eaibot.running.generated.callback.OnClickListener(this, 2);
        mCallback10 = new com.eaibot.running.generated.callback.OnClickListener(this, 4);
        mCallback7 = new com.eaibot.running.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8000L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.checkViewModel == variableId) {
            setCheckViewModel((com.eaibot.running.viewmodel.CheckViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCheckViewModel(@Nullable com.eaibot.running.viewmodel.CheckViewModel CheckViewModel) {
        this.mCheckViewModel = CheckViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4000L;
        }
        notifyPropertyChanged(BR.checkViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeCheckViewModelObLlHintSonarShow((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 1 :
                return onChangeCheckViewModelObImuText((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeCheckViewModelObChargeShow((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 3 :
                return onChangeCheckViewModelObHandleViewShow((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 4 :
                return onChangeCheckViewModelObVoltageText((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeCheckViewModelObVoltageTextColor((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 6 :
                return onChangeCheckViewModelObSonarTitleColor((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 7 :
                return onChangeCheckViewModelObRobotState((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 8 :
                return onChangeCheckViewModelObBtnChargeBg((androidx.databinding.ObservableField<android.graphics.drawable.Drawable>) object, fieldId);
            case 9 :
                return onChangeCheckViewModelObLlDistanceShow((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 10 :
                return onChangeCheckViewModelObBtnRotateBg((androidx.databinding.ObservableField<android.graphics.drawable.Drawable>) object, fieldId);
            case 11 :
                return onChangeCheckViewModelObBtnChangeLaserScanBg((androidx.databinding.ObservableField<android.graphics.drawable.Drawable>) object, fieldId);
            case 12 :
                return onChangeCheckViewModelObRobotPose((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 13 :
                return onChangeCheckViewModelObBtnDirectMoveBg((androidx.databinding.ObservableField<android.graphics.drawable.Drawable>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCheckViewModelObLlHintSonarShow(androidx.databinding.ObservableField<java.lang.Integer> CheckViewModelObLlHintSonarShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObImuText(androidx.databinding.ObservableField<java.lang.String> CheckViewModelObImuText, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObChargeShow(androidx.databinding.ObservableField<java.lang.Integer> CheckViewModelObChargeShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObHandleViewShow(androidx.databinding.ObservableField<java.lang.Boolean> CheckViewModelObHandleViewShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObVoltageText(androidx.databinding.ObservableField<java.lang.String> CheckViewModelObVoltageText, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObVoltageTextColor(androidx.databinding.ObservableField<java.lang.Integer> CheckViewModelObVoltageTextColor, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObSonarTitleColor(androidx.databinding.ObservableField<java.lang.Integer> CheckViewModelObSonarTitleColor, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObRobotState(androidx.databinding.ObservableField<java.lang.String> CheckViewModelObRobotState, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObBtnChargeBg(androidx.databinding.ObservableField<android.graphics.drawable.Drawable> CheckViewModelObBtnChargeBg, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObLlDistanceShow(androidx.databinding.ObservableField<java.lang.Integer> CheckViewModelObLlDistanceShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObBtnRotateBg(androidx.databinding.ObservableField<android.graphics.drawable.Drawable> CheckViewModelObBtnRotateBg, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObBtnChangeLaserScanBg(androidx.databinding.ObservableField<android.graphics.drawable.Drawable> CheckViewModelObBtnChangeLaserScanBg, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObRobotPose(androidx.databinding.ObservableField<java.lang.String> CheckViewModelObRobotPose, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1000L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCheckViewModelObBtnDirectMoveBg(androidx.databinding.ObservableField<android.graphics.drawable.Drawable> CheckViewModelObBtnDirectMoveBg, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2000L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.databinding.ObservableField<java.lang.Integer> checkViewModelObLlHintSonarShow = null;
        java.lang.Integer checkViewModelObChargeShowGet = null;
        java.lang.Integer checkViewModelObVoltageTextColorGet = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObSonarTitleColorGet = 0;
        java.lang.String checkViewModelObVoltageTextGet = null;
        androidx.databinding.ObservableField<java.lang.String> checkViewModelObImuText = null;
        androidx.databinding.ObservableField<java.lang.Integer> checkViewModelObChargeShow = null;
        android.view.View.OnTouchListener checkViewModelShowHintonTouchListener = null;
        int checkViewModelObHandleViewShowViewVISIBLEViewGONE = 0;
        int androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObVoltageTextColorGet = 0;
        java.lang.Integer checkViewModelObLlDistanceShowGet = null;
        android.view.View.OnTouchListener checkViewModelSonarTitleTouchListener = null;
        java.lang.Integer checkViewModelObLlHintSonarShowGet = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlHintSonarShowGet = 0;
        androidx.databinding.ObservableField<java.lang.Boolean> checkViewModelObHandleViewShow = null;
        android.view.View.OnTouchListener checkViewModelOnChargeMarkTouchListener = null;
        androidx.databinding.ObservableField<java.lang.String> checkViewModelObVoltageText = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObChargeShowGet = 0;
        androidx.databinding.ObservableField<java.lang.Integer> checkViewModelObVoltageTextColor = null;
        androidx.databinding.ObservableField<java.lang.Integer> checkViewModelObSonarTitleColor = null;
        com.eaibot.running.viewmodel.CheckViewModel checkViewModel = mCheckViewModel;
        java.lang.String checkViewModelObRobotStateGet = null;
        android.graphics.drawable.Drawable checkViewModelObBtnRotateBgGet = null;
        android.graphics.drawable.Drawable checkViewModelObBtnChangeLaserScanBgGet = null;
        androidx.databinding.ObservableField<java.lang.String> checkViewModelObRobotState = null;
        androidx.databinding.ObservableField<android.graphics.drawable.Drawable> checkViewModelObBtnChargeBg = null;
        android.graphics.drawable.Drawable checkViewModelObBtnDirectMoveBgGet = null;
        androidx.databinding.ObservableField<java.lang.Integer> checkViewModelObLlDistanceShow = null;
        int androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlDistanceShowGet = 0;
        androidx.databinding.ObservableField<android.graphics.drawable.Drawable> checkViewModelObBtnRotateBg = null;
        java.lang.Integer checkViewModelObSonarTitleColorGet = null;
        androidx.databinding.ObservableField<android.graphics.drawable.Drawable> checkViewModelObBtnChangeLaserScanBg = null;
        java.lang.Boolean checkViewModelObHandleViewShowGet = null;
        androidx.databinding.ObservableField<java.lang.String> checkViewModelObRobotPose = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObHandleViewShowGet = false;
        androidx.databinding.ObservableField<android.graphics.drawable.Drawable> checkViewModelObBtnDirectMoveBg = null;
        java.lang.String checkViewModelObRobotPoseGet = null;
        java.lang.String checkViewModelObImuTextGet = null;
        android.graphics.drawable.Drawable checkViewModelObBtnChargeBgGet = null;

        if ((dirtyFlags & 0xffffL) != 0) {


            if ((dirtyFlags & 0xc001L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obLlHintSonarShow
                        checkViewModelObLlHintSonarShow = checkViewModel.obLlHintSonarShow;
                    }
                    updateRegistration(0, checkViewModelObLlHintSonarShow);


                    if (checkViewModelObLlHintSonarShow != null) {
                        // read checkViewModel.obLlHintSonarShow.get()
                        checkViewModelObLlHintSonarShowGet = checkViewModelObLlHintSonarShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obLlHintSonarShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlHintSonarShowGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObLlHintSonarShowGet);
            }
            if ((dirtyFlags & 0xc002L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obImuText
                        checkViewModelObImuText = checkViewModel.obImuText;
                    }
                    updateRegistration(1, checkViewModelObImuText);


                    if (checkViewModelObImuText != null) {
                        // read checkViewModel.obImuText.get()
                        checkViewModelObImuTextGet = checkViewModelObImuText.get();
                    }
            }
            if ((dirtyFlags & 0xc004L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obChargeShow
                        checkViewModelObChargeShow = checkViewModel.obChargeShow;
                    }
                    updateRegistration(2, checkViewModelObChargeShow);


                    if (checkViewModelObChargeShow != null) {
                        // read checkViewModel.obChargeShow.get()
                        checkViewModelObChargeShowGet = checkViewModelObChargeShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obChargeShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObChargeShowGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObChargeShowGet);
            }
            if ((dirtyFlags & 0xc000L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.showHintonTouchListener
                        checkViewModelShowHintonTouchListener = checkViewModel.showHintonTouchListener;
                        // read checkViewModel.sonarTitleTouchListener
                        checkViewModelSonarTitleTouchListener = checkViewModel.sonarTitleTouchListener;
                        // read checkViewModel.onChargeMarkTouchListener
                        checkViewModelOnChargeMarkTouchListener = checkViewModel.onChargeMarkTouchListener;
                    }
            }
            if ((dirtyFlags & 0xc008L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obHandleViewShow
                        checkViewModelObHandleViewShow = checkViewModel.obHandleViewShow;
                    }
                    updateRegistration(3, checkViewModelObHandleViewShow);


                    if (checkViewModelObHandleViewShow != null) {
                        // read checkViewModel.obHandleViewShow.get()
                        checkViewModelObHandleViewShowGet = checkViewModelObHandleViewShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obHandleViewShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObHandleViewShowGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObHandleViewShowGet);
                if((dirtyFlags & 0xc008L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObHandleViewShowGet) {
                            dirtyFlags |= 0x20000L;
                    }
                    else {
                            dirtyFlags |= 0x10000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
                    checkViewModelObHandleViewShowViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObHandleViewShowGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0xc010L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obVoltageText
                        checkViewModelObVoltageText = checkViewModel.obVoltageText;
                    }
                    updateRegistration(4, checkViewModelObVoltageText);


                    if (checkViewModelObVoltageText != null) {
                        // read checkViewModel.obVoltageText.get()
                        checkViewModelObVoltageTextGet = checkViewModelObVoltageText.get();
                    }
            }
            if ((dirtyFlags & 0xc020L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obVoltageTextColor
                        checkViewModelObVoltageTextColor = checkViewModel.obVoltageTextColor;
                    }
                    updateRegistration(5, checkViewModelObVoltageTextColor);


                    if (checkViewModelObVoltageTextColor != null) {
                        // read checkViewModel.obVoltageTextColor.get()
                        checkViewModelObVoltageTextColorGet = checkViewModelObVoltageTextColor.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obVoltageTextColor.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObVoltageTextColorGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObVoltageTextColorGet);
            }
            if ((dirtyFlags & 0xc040L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obSonarTitleColor
                        checkViewModelObSonarTitleColor = checkViewModel.obSonarTitleColor;
                    }
                    updateRegistration(6, checkViewModelObSonarTitleColor);


                    if (checkViewModelObSonarTitleColor != null) {
                        // read checkViewModel.obSonarTitleColor.get()
                        checkViewModelObSonarTitleColorGet = checkViewModelObSonarTitleColor.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obSonarTitleColor.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObSonarTitleColorGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObSonarTitleColorGet);
            }
            if ((dirtyFlags & 0xc080L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obRobotState
                        checkViewModelObRobotState = checkViewModel.obRobotState;
                    }
                    updateRegistration(7, checkViewModelObRobotState);


                    if (checkViewModelObRobotState != null) {
                        // read checkViewModel.obRobotState.get()
                        checkViewModelObRobotStateGet = checkViewModelObRobotState.get();
                    }
            }
            if ((dirtyFlags & 0xc100L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obBtnChargeBg
                        checkViewModelObBtnChargeBg = checkViewModel.obBtnChargeBg;
                    }
                    updateRegistration(8, checkViewModelObBtnChargeBg);


                    if (checkViewModelObBtnChargeBg != null) {
                        // read checkViewModel.obBtnChargeBg.get()
                        checkViewModelObBtnChargeBgGet = checkViewModelObBtnChargeBg.get();
                    }
            }
            if ((dirtyFlags & 0xc200L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obLlDistanceShow
                        checkViewModelObLlDistanceShow = checkViewModel.obLlDistanceShow;
                    }
                    updateRegistration(9, checkViewModelObLlDistanceShow);


                    if (checkViewModelObLlDistanceShow != null) {
                        // read checkViewModel.obLlDistanceShow.get()
                        checkViewModelObLlDistanceShowGet = checkViewModelObLlDistanceShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obLlDistanceShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlDistanceShowGet = androidx.databinding.ViewDataBinding.safeUnbox(checkViewModelObLlDistanceShowGet);
            }
            if ((dirtyFlags & 0xc400L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obBtnRotateBg
                        checkViewModelObBtnRotateBg = checkViewModel.obBtnRotateBg;
                    }
                    updateRegistration(10, checkViewModelObBtnRotateBg);


                    if (checkViewModelObBtnRotateBg != null) {
                        // read checkViewModel.obBtnRotateBg.get()
                        checkViewModelObBtnRotateBgGet = checkViewModelObBtnRotateBg.get();
                    }
            }
            if ((dirtyFlags & 0xc800L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obBtnChangeLaserScanBg
                        checkViewModelObBtnChangeLaserScanBg = checkViewModel.obBtnChangeLaserScanBg;
                    }
                    updateRegistration(11, checkViewModelObBtnChangeLaserScanBg);


                    if (checkViewModelObBtnChangeLaserScanBg != null) {
                        // read checkViewModel.obBtnChangeLaserScanBg.get()
                        checkViewModelObBtnChangeLaserScanBgGet = checkViewModelObBtnChangeLaserScanBg.get();
                    }
            }
            if ((dirtyFlags & 0xd000L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obRobotPose
                        checkViewModelObRobotPose = checkViewModel.obRobotPose;
                    }
                    updateRegistration(12, checkViewModelObRobotPose);


                    if (checkViewModelObRobotPose != null) {
                        // read checkViewModel.obRobotPose.get()
                        checkViewModelObRobotPoseGet = checkViewModelObRobotPose.get();
                    }
            }
            if ((dirtyFlags & 0xe000L) != 0) {

                    if (checkViewModel != null) {
                        // read checkViewModel.obBtnDirectMoveBg
                        checkViewModelObBtnDirectMoveBg = checkViewModel.obBtnDirectMoveBg;
                    }
                    updateRegistration(13, checkViewModelObBtnDirectMoveBg);


                    if (checkViewModelObBtnDirectMoveBg != null) {
                        // read checkViewModel.obBtnDirectMoveBg.get()
                        checkViewModelObBtnDirectMoveBgGet = checkViewModelObBtnDirectMoveBg.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0xc800L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.btnChangeLaserscan, checkViewModelObBtnChangeLaserScanBgGet);
        }
        if ((dirtyFlags & 0x8000L) != 0) {
            // api target 1

            this.btnChangeLaserscan.setOnClickListener(mCallback10);
            this.btnGo1m.setOnClickListener(mCallback7);
            this.btnRecharge.setOnClickListener(mCallback9);
            this.btnTurn360.setOnClickListener(mCallback8);
        }
        if ((dirtyFlags & 0xe000L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.btnGo1m, checkViewModelObBtnDirectMoveBgGet);
        }
        if ((dirtyFlags & 0xc100L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.btnRecharge, checkViewModelObBtnChargeBgGet);
        }
        if ((dirtyFlags & 0xc000L) != 0) {
            // api target 1

            this.btnShowHint.setOnTouchListener(checkViewModelShowHintonTouchListener);
            this.sonarTitle.setOnTouchListener(checkViewModelSonarTitleTouchListener);
            this.textView.setOnTouchListener(checkViewModelOnChargeMarkTouchListener);
            this.textVoltage.setOnTouchListener(checkViewModelOnChargeMarkTouchListener);
        }
        if ((dirtyFlags & 0xc400L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.btnTurn360, checkViewModelObBtnRotateBgGet);
        }
        if ((dirtyFlags & 0xc008L) != 0) {
            // api target 1

            this.dashgoHandleControl.setVisibility(checkViewModelObHandleViewShowViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0xc002L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.imuText, checkViewModelObImuTextGet);
        }
        if ((dirtyFlags & 0xc200L) != 0) {
            // api target 1

            this.linearLayoutDistance.setVisibility(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlDistanceShowGet);
        }
        if ((dirtyFlags & 0xc001L) != 0) {
            // api target 1

            this.linearLayoutHintSonar.setVisibility(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObLlHintSonarShowGet);
        }
        if ((dirtyFlags & 0xc004L) != 0) {
            // api target 1

            this.linearLayoutHintVoltage.setVisibility(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObChargeShowGet);
        }
        if ((dirtyFlags & 0xd000L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.robotPose, checkViewModelObRobotPoseGet);
        }
        if ((dirtyFlags & 0xc080L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.robotState, checkViewModelObRobotStateGet);
        }
        if ((dirtyFlags & 0xc040L) != 0) {
            // api target 1

            this.sonarTitle.setTextColor(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObSonarTitleColorGet);
        }
        if ((dirtyFlags & 0xc010L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textVoltage, checkViewModelObVoltageTextGet);
        }
        if ((dirtyFlags & 0xc020L) != 0) {
            // api target 1

            this.textVoltage.setTextColor(androidxDatabindingViewDataBindingSafeUnboxCheckViewModelObVoltageTextColorGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // checkViewModel != null
                boolean checkViewModelJavaLangObjectNull = false;
                // checkViewModel
                com.eaibot.running.viewmodel.CheckViewModel checkViewModel = mCheckViewModel;



                checkViewModelJavaLangObjectNull = (checkViewModel) != (null);
                if (checkViewModelJavaLangObjectNull) {


                    checkViewModel.onClickCharge();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // checkViewModel != null
                boolean checkViewModelJavaLangObjectNull = false;
                // checkViewModel
                com.eaibot.running.viewmodel.CheckViewModel checkViewModel = mCheckViewModel;



                checkViewModelJavaLangObjectNull = (checkViewModel) != (null);
                if (checkViewModelJavaLangObjectNull) {


                    checkViewModel.onClickRotate();
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // checkViewModel != null
                boolean checkViewModelJavaLangObjectNull = false;
                // checkViewModel
                com.eaibot.running.viewmodel.CheckViewModel checkViewModel = mCheckViewModel;



                checkViewModelJavaLangObjectNull = (checkViewModel) != (null);
                if (checkViewModelJavaLangObjectNull) {


                    checkViewModel.onClickChangeLaserScan();
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // checkViewModel != null
                boolean checkViewModelJavaLangObjectNull = false;
                // checkViewModel
                com.eaibot.running.viewmodel.CheckViewModel checkViewModel = mCheckViewModel;



                checkViewModelJavaLangObjectNull = (checkViewModel) != (null);
                if (checkViewModelJavaLangObjectNull) {


                    checkViewModel.onClickDirectMove();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): checkViewModel.obLlHintSonarShow
        flag 1 (0x2L): checkViewModel.obImuText
        flag 2 (0x3L): checkViewModel.obChargeShow
        flag 3 (0x4L): checkViewModel.obHandleViewShow
        flag 4 (0x5L): checkViewModel.obVoltageText
        flag 5 (0x6L): checkViewModel.obVoltageTextColor
        flag 6 (0x7L): checkViewModel.obSonarTitleColor
        flag 7 (0x8L): checkViewModel.obRobotState
        flag 8 (0x9L): checkViewModel.obBtnChargeBg
        flag 9 (0xaL): checkViewModel.obLlDistanceShow
        flag 10 (0xbL): checkViewModel.obBtnRotateBg
        flag 11 (0xcL): checkViewModel.obBtnChangeLaserScanBg
        flag 12 (0xdL): checkViewModel.obRobotPose
        flag 13 (0xeL): checkViewModel.obBtnDirectMoveBg
        flag 14 (0xfL): checkViewModel
        flag 15 (0x10L): null
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(checkViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}