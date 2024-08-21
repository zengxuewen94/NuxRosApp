package com.eaibot.running.databinding;
import com.eaibot.running.R;
import com.eaibot.running.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding implements com.eaibot.running.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.robotPoseText, 11);
        sViewsWithIds.put(R.id.handleView_Main, 12);
        sViewsWithIds.put(R.id.saveMap, 13);
        sViewsWithIds.put(R.id.mapOptions, 14);
        sViewsWithIds.put(R.id.llPoseOptions, 15);
        sViewsWithIds.put(R.id.chargeOptions, 16);
        sViewsWithIds.put(R.id.ll_chargeOptions, 17);
        sViewsWithIds.put(R.id.addChargePile, 18);
        sViewsWithIds.put(R.id.delChargePile, 19);
        sViewsWithIds.put(R.id.ll_mapOptions, 20);
        sViewsWithIds.put(R.id.initOrAdd, 21);
        sViewsWithIds.put(R.id.paint, 22);
        sViewsWithIds.put(R.id.edit, 23);
        sViewsWithIds.put(R.id.undo, 24);
        sViewsWithIds.put(R.id.commit, 25);
        sViewsWithIds.put(R.id.battery, 26);
        sViewsWithIds.put(R.id.goAlong, 27);
        sViewsWithIds.put(R.id.goBack, 28);
        sViewsWithIds.put(R.id.turnRight, 29);
        sViewsWithIds.put(R.id.turnLeft, 30);
        sViewsWithIds.put(R.id.poseRecyclerView, 31);
    }
    // views
    @NonNull
    private final com.zhy.android.percent.support.PercentFrameLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.widget.Button) bindings[18]
            , (android.widget.Button) bindings[6]
            , (com.eaibot.running.view.BatteryView) bindings[26]
            , (android.widget.Button) bindings[16]
            , (android.widget.Button) bindings[25]
            , (android.widget.Button) bindings[19]
            , (android.widget.Button) bindings[23]
            , (android.widget.Button) bindings[27]
            , (android.widget.Button) bindings[28]
            , (android.widget.Button) bindings[4]
            , (com.eaibot.running.view.WebHandleView) bindings[12]
            , (android.widget.Button) bindings[21]
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.LinearLayout) bindings[20]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.Button) bindings[14]
            , (android.widget.Button) bindings[9]
            , (android.widget.TextView) bindings[2]
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[31]
            , (android.widget.TextView) bindings[11]
            , (com.eaibot.running.widget.RoundMenuView) bindings[1]
            , (android.widget.Button) bindings[13]
            , (android.widget.Button) bindings[3]
            , (android.widget.Button) bindings[5]
            , (android.widget.Button) bindings[8]
            , (android.widget.Button) bindings[30]
            , (android.widget.Button) bindings[29]
            , (android.widget.Button) bindings[24]
            , (android.widget.TextView) bindings[10]
            );
        this.addCurrentPoseGoal.setTag(null);
        this.goCharge.setTag(null);
        this.mboundView0 = (com.zhy.android.percent.support.PercentFrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.moveOrClick.setTag(null);
        this.msgText.setTag(null);
        this.poseList.setTag(null);
        this.roundMenuView.setTag(null);
        this.startNav.setTag(null);
        this.stopCharge.setTag(null);
        this.stopNav.setTag(null);
        this.voltageText.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new com.eaibot.running.generated.callback.OnClickListener(this, 6);
        mCallback2 = new com.eaibot.running.generated.callback.OnClickListener(this, 2);
        mCallback5 = new com.eaibot.running.generated.callback.OnClickListener(this, 5);
        mCallback1 = new com.eaibot.running.generated.callback.OnClickListener(this, 1);
        mCallback4 = new com.eaibot.running.generated.callback.OnClickListener(this, 4);
        mCallback3 = new com.eaibot.running.generated.callback.OnClickListener(this, 3);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        if (BR.mainViewModel == variableId) {
            setMainViewModel((com.eaibot.running.viewmodel.MainViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMainViewModel(@Nullable com.eaibot.running.viewmodel.MainViewModel MainViewModel) {
        this.mMainViewModel = MainViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.mainViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMainViewModelObMoveOrClickResource((androidx.databinding.ObservableField<android.graphics.drawable.Drawable>) object, fieldId);
            case 1 :
                return onChangeMainViewModelObHandleViewShow((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 2 :
                return onChangeMainViewModelObTitle((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeMainViewModelObBatteryValue((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMainViewModelObMoveOrClickResource(androidx.databinding.ObservableField<android.graphics.drawable.Drawable> MainViewModelObMoveOrClickResource, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelObHandleViewShow(androidx.databinding.ObservableField<java.lang.Boolean> MainViewModelObHandleViewShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelObTitle(androidx.databinding.ObservableField<java.lang.String> MainViewModelObTitle, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelObBatteryValue(androidx.databinding.ObservableField<java.lang.String> MainViewModelObBatteryValue, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        androidx.databinding.ObservableField<android.graphics.drawable.Drawable> mainViewModelObMoveOrClickResource = null;
        com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> mainViewModelObHandleViewShow = null;
        int mainViewModelObHandleViewShowViewVISIBLEViewGONE = 0;
        androidx.databinding.ObservableField<java.lang.String> mainViewModelObTitle = null;
        java.lang.String mainViewModelObBatteryValueGet = null;
        android.view.View.OnLongClickListener mainViewModelOnLongClickListener = null;
        android.graphics.drawable.Drawable mainViewModelObMoveOrClickResourceGet = null;
        java.lang.Boolean mainViewModelObHandleViewShowGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxMainViewModelObHandleViewShowGet = false;
        androidx.databinding.ObservableField<java.lang.String> mainViewModelObBatteryValue = null;
        java.lang.String mainViewModelObTitleGet = null;

        if ((dirtyFlags & 0x3fL) != 0) {


            if ((dirtyFlags & 0x31L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.obMoveOrClickResource
                        mainViewModelObMoveOrClickResource = mainViewModel.obMoveOrClickResource;
                    }
                    updateRegistration(0, mainViewModelObMoveOrClickResource);


                    if (mainViewModelObMoveOrClickResource != null) {
                        // read mainViewModel.obMoveOrClickResource.get()
                        mainViewModelObMoveOrClickResourceGet = mainViewModelObMoveOrClickResource.get();
                    }
            }
            if ((dirtyFlags & 0x32L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.obHandleViewShow
                        mainViewModelObHandleViewShow = mainViewModel.obHandleViewShow;
                    }
                    updateRegistration(1, mainViewModelObHandleViewShow);


                    if (mainViewModelObHandleViewShow != null) {
                        // read mainViewModel.obHandleViewShow.get()
                        mainViewModelObHandleViewShowGet = mainViewModelObHandleViewShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(mainViewModel.obHandleViewShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxMainViewModelObHandleViewShowGet = androidx.databinding.ViewDataBinding.safeUnbox(mainViewModelObHandleViewShowGet);
                if((dirtyFlags & 0x32L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxMainViewModelObHandleViewShowGet) {
                            dirtyFlags |= 0x80L;
                    }
                    else {
                            dirtyFlags |= 0x40L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(mainViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
                    mainViewModelObHandleViewShowViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxMainViewModelObHandleViewShowGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x34L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.obTitle
                        mainViewModelObTitle = mainViewModel.obTitle;
                    }
                    updateRegistration(2, mainViewModelObTitle);


                    if (mainViewModelObTitle != null) {
                        // read mainViewModel.obTitle.get()
                        mainViewModelObTitleGet = mainViewModelObTitle.get();
                    }
            }
            if ((dirtyFlags & 0x30L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.onLongClickListener
                        mainViewModelOnLongClickListener = mainViewModel.onLongClickListener;
                    }
            }
            if ((dirtyFlags & 0x38L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.obBatteryValue
                        mainViewModelObBatteryValue = mainViewModel.obBatteryValue;
                    }
                    updateRegistration(3, mainViewModelObBatteryValue);


                    if (mainViewModelObBatteryValue != null) {
                        // read mainViewModel.obBatteryValue.get()
                        mainViewModelObBatteryValueGet = mainViewModelObBatteryValue.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x20L) != 0) {
            // api target 1

            this.addCurrentPoseGoal.setOnClickListener(mCallback4);
            this.goCharge.setOnClickListener(mCallback2);
            this.poseList.setOnClickListener(mCallback5);
            this.startNav.setOnClickListener(mCallback1);
            this.stopCharge.setOnClickListener(mCallback3);
            this.stopNav.setOnClickListener(mCallback6);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.moveOrClick, mainViewModelObMoveOrClickResourceGet);
        }
        if ((dirtyFlags & 0x34L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.msgText, mainViewModelObTitleGet);
        }
        if ((dirtyFlags & 0x32L) != 0) {
            // api target 1

            this.roundMenuView.setVisibility(mainViewModelObHandleViewShowViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x30L) != 0) {
            // api target 1

            this.startNav.setOnLongClickListener(mainViewModelOnLongClickListener);
        }
        if ((dirtyFlags & 0x38L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.voltageText, mainViewModelObBatteryValueGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 6: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {


                    mainViewModel.onClickStopNav();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {


                    mainViewModel.onClickGoHome();
                }
                break;
            }
            case 5: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {



                    mainViewModel.onClickPoseList(2);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {


                    mainViewModel.onClickStartNav();
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {



                    mainViewModel.onClickAddCurrentPoseGoal(1);
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // mainViewModel
                com.eaibot.running.viewmodel.MainViewModel mainViewModel = mMainViewModel;
                // mainViewModel != null
                boolean mainViewModelJavaLangObjectNull = false;



                mainViewModelJavaLangObjectNull = (mainViewModel) != (null);
                if (mainViewModelJavaLangObjectNull) {


                    mainViewModel.onClickCancelGoHome();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): mainViewModel.obMoveOrClickResource
        flag 1 (0x2L): mainViewModel.obHandleViewShow
        flag 2 (0x3L): mainViewModel.obTitle
        flag 3 (0x4L): mainViewModel.obBatteryValue
        flag 4 (0x5L): mainViewModel
        flag 5 (0x6L): null
        flag 6 (0x7L): androidx.databinding.ViewDataBinding.safeUnbox(mainViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
        flag 7 (0x8L): androidx.databinding.ViewDataBinding.safeUnbox(mainViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}