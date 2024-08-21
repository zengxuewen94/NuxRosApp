package com.eaibot.running.databinding;
import com.eaibot.running.R;
import com.eaibot.running.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityConnectBindingImpl extends ActivityConnectBinding implements com.eaibot.running.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.connectLinearLayout, 19);
        sViewsWithIds.put(R.id.titleName, 20);
        sViewsWithIds.put(R.id.versionName, 21);
        sViewsWithIds.put(R.id.spinnerMap, 22);
        sViewsWithIds.put(R.id.settingLayout, 23);
        sViewsWithIds.put(R.id.iv_map, 24);
        sViewsWithIds.put(R.id.battery, 25);
        sViewsWithIds.put(R.id.handleView_Connect, 26);
        sViewsWithIds.put(R.id.cutLine, 27);
        sViewsWithIds.put(R.id.rosServiceIp, 28);
        sViewsWithIds.put(R.id.fixedLinear, 29);
        sViewsWithIds.put(R.id.fixedAngular, 30);
        sViewsWithIds.put(R.id.fixedStop, 31);
    }
    // views
    @NonNull
    private final com.zhy.android.percent.support.PercentRelativeLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback11;
    @Nullable
    private final android.view.View.OnClickListener mCallback12;
    @Nullable
    private final android.view.View.OnClickListener mCallback13;
    @Nullable
    private final android.view.View.OnClickListener mCallback14;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener cbImuandroidCheckedAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of connectViewModel.obImuChecked.get()
            //         is connectViewModel.obImuChecked.set((java.lang.Boolean) callbackArg_0)
            boolean callbackArg_0 = cbImu.isChecked();
            // localize variables for thread safety
            // connectViewModel.obImuChecked.get()
            java.lang.Boolean connectViewModelObImuCheckedGet = null;
            // connectViewModel
            com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
            // connectViewModel.obImuChecked
            androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObImuChecked = null;
            // connectViewModel.obImuChecked != null
            boolean connectViewModelObImuCheckedJavaLangObjectNull = false;
            // connectViewModel != null
            boolean connectViewModelJavaLangObjectNull = false;



            connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
            if (connectViewModelJavaLangObjectNull) {


                connectViewModelObImuChecked = connectViewModel.obImuChecked;

                connectViewModelObImuCheckedJavaLangObjectNull = (connectViewModelObImuChecked) != (null);
                if (connectViewModelObImuCheckedJavaLangObjectNull) {




                    connectViewModelObImuChecked.set(((java.lang.Boolean) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener cbRechargeandroidCheckedAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of connectViewModel.obChargeChecked.get()
            //         is connectViewModel.obChargeChecked.set((java.lang.Boolean) callbackArg_0)
            boolean callbackArg_0 = cbRecharge.isChecked();
            // localize variables for thread safety
            // connectViewModel
            com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
            // connectViewModel.obChargeChecked.get()
            java.lang.Boolean connectViewModelObChargeCheckedGet = null;
            // connectViewModel.obChargeChecked != null
            boolean connectViewModelObChargeCheckedJavaLangObjectNull = false;
            // connectViewModel.obChargeChecked
            androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObChargeChecked = null;
            // connectViewModel != null
            boolean connectViewModelJavaLangObjectNull = false;



            connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
            if (connectViewModelJavaLangObjectNull) {


                connectViewModelObChargeChecked = connectViewModel.obChargeChecked;

                connectViewModelObChargeCheckedJavaLangObjectNull = (connectViewModelObChargeChecked) != (null);
                if (connectViewModelObChargeCheckedJavaLangObjectNull) {




                    connectViewModelObChargeChecked.set(((java.lang.Boolean) (callbackArg_0)));
                }
            }
        }
    };
    private androidx.databinding.InverseBindingListener editTextIpandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of connectViewModel.obIp.get()
            //         is connectViewModel.obIp.set((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(editTextIp);
            // localize variables for thread safety
            // connectViewModel.obIp != null
            boolean connectViewModelObIpJavaLangObjectNull = false;
            // connectViewModel
            com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
            // connectViewModel.obIp
            androidx.databinding.ObservableField<java.lang.String> connectViewModelObIp = null;
            // connectViewModel.obIp.get()
            java.lang.String connectViewModelObIpGet = null;
            // connectViewModel != null
            boolean connectViewModelJavaLangObjectNull = false;



            connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
            if (connectViewModelJavaLangObjectNull) {


                connectViewModelObIp = connectViewModel.obIp;

                connectViewModelObIpJavaLangObjectNull = (connectViewModelObIp) != (null);
                if (connectViewModelObIpJavaLangObjectNull) {




                    connectViewModelObIp.set(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityConnectBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds));
    }
    private ActivityConnectBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 16
            , (com.eaibot.running.view.BatteryView) bindings[25]
            , (android.widget.Button) bindings[6]
            , (android.widget.Button) bindings[10]
            , (android.widget.CheckBox) bindings[16]
            , (android.widget.CheckBox) bindings[13]
            , (android.widget.CheckBox) bindings[15]
            , (android.widget.CheckBox) bindings[1]
            , (android.widget.CheckBox) bindings[14]
            , (android.widget.CheckBox) bindings[2]
            , (com.zhy.android.percent.support.PercentLinearLayout) bindings[19]
            , (android.view.View) bindings[27]
            , (android.widget.TextView) bindings[4]
            , (android.widget.EditText) bindings[12]
            , (android.widget.Button) bindings[30]
            , (android.widget.Button) bindings[29]
            , (android.widget.Button) bindings[31]
            , (com.eaibot.running.view.WebHandleView) bindings[26]
            , (com.nudtit.slam.mapview.MapView) bindings[24]
            , (android.widget.TextView) bindings[3]
            , (android.widget.Button) bindings[18]
            , (android.widget.Button) bindings[17]
            , (android.widget.TextView) bindings[28]
            , (android.widget.ImageView) bindings[7]
            , (com.eaibot.running.widget.RoundMenuView) bindings[11]
            , (com.zhy.android.percent.support.PercentRelativeLayout) bindings[23]
            , (android.widget.Spinner) bindings[22]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[9]
            , (android.widget.ImageView) bindings[8]
            );
        this.btnConnect.setTag(null);
        this.btnToolbox.setTag(null);
        this.cbCheck.setTag(null);
        this.cbGmapping.setTag(null);
        this.cbGoogle.setTag(null);
        this.cbImu.setTag(null);
        this.cbNavigation.setTag(null);
        this.cbRecharge.setTag(null);
        this.downloadMap.setTag(null);
        this.editTextIp.setTag(null);
        this.loadMap.setTag(null);
        this.mboundView0 = (com.zhy.android.percent.support.PercentRelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.navContinue.setTag(null);
        this.navStop.setTag(null);
        this.rosStatusConnect.setTag(null);
        this.roundMenuView.setTag(null);
        this.uploadMap.setTag(null);
        this.voltageText.setTag(null);
        this.wifiState.setTag(null);
        setRootTag(root);
        // listeners
        mCallback11 = new com.eaibot.running.generated.callback.OnClickListener(this, 1);
        mCallback12 = new com.eaibot.running.generated.callback.OnClickListener(this, 2);
        mCallback13 = new com.eaibot.running.generated.callback.OnClickListener(this, 3);
        mCallback14 = new com.eaibot.running.generated.callback.OnClickListener(this, 4);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20000L;
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
        if (BR.connectViewModel == variableId) {
            setConnectViewModel((com.eaibot.running.viewmodel.ConnectViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setConnectViewModel(@Nullable com.eaibot.running.viewmodel.ConnectViewModel ConnectViewModel) {
        this.mConnectViewModel = ConnectViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10000L;
        }
        notifyPropertyChanged(BR.connectViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeConnectViewModelObGoogleChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 1 :
                return onChangeConnectViewModelObIp((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeConnectViewModelObNavigateChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 3 :
                return onChangeConnectViewModelObBtConnectText((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeConnectViewModelObDownloadEnable((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 5 :
                return onChangeConnectViewModelObChargeChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 6 :
                return onChangeConnectViewModelObHandleViewShow((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 7 :
                return onChangeConnectViewModelObBtConnectEnable((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 8 :
                return onChangeConnectViewModelObMappingChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 9 :
                return onChangeConnectViewModelObCheckChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 10 :
                return onChangeConnectViewModelObUploadEnable((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 11 :
                return onChangeConnectViewModelObLoadMap((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 12 :
                return onChangeConnectViewModelObBatteryValue((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 13 :
                return onChangeConnectViewModelObImuChecked((androidx.databinding.ObservableField<java.lang.Boolean>) object, fieldId);
            case 14 :
                return onChangeConnectViewModelObRosConnectStatus((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
            case 15 :
                return onChangeConnectViewModelObWifiStatus((androidx.databinding.ObservableField<java.lang.Integer>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeConnectViewModelObGoogleChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObGoogleChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObIp(androidx.databinding.ObservableField<java.lang.String> ConnectViewModelObIp, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObNavigateChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObNavigateChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObBtConnectText(androidx.databinding.ObservableField<java.lang.String> ConnectViewModelObBtConnectText, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObDownloadEnable(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObDownloadEnable, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObChargeChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObChargeChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObHandleViewShow(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObHandleViewShow, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObBtConnectEnable(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObBtConnectEnable, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObMappingChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObMappingChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x100L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObCheckChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObCheckChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x200L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObUploadEnable(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObUploadEnable, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x400L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObLoadMap(androidx.databinding.ObservableField<java.lang.String> ConnectViewModelObLoadMap, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x800L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObBatteryValue(androidx.databinding.ObservableField<java.lang.String> ConnectViewModelObBatteryValue, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1000L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObImuChecked(androidx.databinding.ObservableField<java.lang.Boolean> ConnectViewModelObImuChecked, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2000L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObRosConnectStatus(androidx.databinding.ObservableField<java.lang.Integer> ConnectViewModelObRosConnectStatus, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4000L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeConnectViewModelObWifiStatus(androidx.databinding.ObservableField<java.lang.Integer> ConnectViewModelObWifiStatus, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8000L;
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
        int androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObRosConnectStatusGet = 0;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObGoogleChecked = null;
        java.lang.Boolean connectViewModelObMappingCheckedGet = null;
        androidx.databinding.ObservableField<java.lang.String> connectViewModelObIp = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObChargeCheckedGet = false;
        java.lang.Integer connectViewModelObWifiStatusGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObHandleViewShowGet = false;
        java.lang.Boolean connectViewModelObBtConnectEnableGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObNavigateCheckedGet = false;
        java.lang.Boolean connectViewModelObDownloadEnableGet = null;
        java.lang.String connectViewModelObIpGet = null;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObNavigateChecked = null;
        androidx.databinding.ObservableField<java.lang.String> connectViewModelObBtConnectText = null;
        com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObDownloadEnable = null;
        android.widget.CompoundButton.OnCheckedChangeListener connectViewModelOnCheckedChangeListener = null;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObChargeChecked = null;
        java.lang.Boolean connectViewModelObImuCheckedGet = null;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObHandleViewShow = null;
        java.lang.String connectViewModelObLoadMapGet = null;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObBtConnectEnable = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObDownloadEnableGet = false;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObMappingChecked = null;
        java.lang.Boolean connectViewModelObCheckCheckedGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObMappingCheckedGet = false;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObCheckChecked = null;
        java.lang.String connectViewModelObBtConnectTextGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObImuCheckedGet = false;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObUploadEnable = null;
        androidx.databinding.ObservableField<java.lang.String> connectViewModelObLoadMap = null;
        androidx.databinding.ObservableField<java.lang.String> connectViewModelObBatteryValue = null;
        androidx.databinding.ObservableField<java.lang.Boolean> connectViewModelObImuChecked = null;
        int connectViewModelObHandleViewShowViewVISIBLEViewGONE = 0;
        java.lang.String connectViewModelObBatteryValueGet = null;
        java.lang.Boolean connectViewModelObHandleViewShowGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObCheckCheckedGet = false;
        java.lang.Integer connectViewModelObRosConnectStatusGet = null;
        java.lang.Boolean connectViewModelObNavigateCheckedGet = null;
        androidx.databinding.ObservableField<java.lang.Integer> connectViewModelObRosConnectStatus = null;
        java.lang.Boolean connectViewModelObGoogleCheckedGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObBtConnectEnableGet = false;
        java.lang.Boolean connectViewModelObChargeCheckedGet = null;
        androidx.databinding.ObservableField<java.lang.Integer> connectViewModelObWifiStatus = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObGoogleCheckedGet = false;
        java.lang.Boolean connectViewModelObUploadEnableGet = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObUploadEnableGet = false;
        int androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObWifiStatusGet = 0;

        if ((dirtyFlags & 0x3ffffL) != 0) {


            if ((dirtyFlags & 0x30001L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obGoogleChecked
                        connectViewModelObGoogleChecked = connectViewModel.obGoogleChecked;
                    }
                    updateRegistration(0, connectViewModelObGoogleChecked);


                    if (connectViewModelObGoogleChecked != null) {
                        // read connectViewModel.obGoogleChecked.get()
                        connectViewModelObGoogleCheckedGet = connectViewModelObGoogleChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obGoogleChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObGoogleCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObGoogleCheckedGet);
            }
            if ((dirtyFlags & 0x30002L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obIp
                        connectViewModelObIp = connectViewModel.obIp;
                    }
                    updateRegistration(1, connectViewModelObIp);


                    if (connectViewModelObIp != null) {
                        // read connectViewModel.obIp.get()
                        connectViewModelObIpGet = connectViewModelObIp.get();
                    }
            }
            if ((dirtyFlags & 0x30004L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obNavigateChecked
                        connectViewModelObNavigateChecked = connectViewModel.obNavigateChecked;
                    }
                    updateRegistration(2, connectViewModelObNavigateChecked);


                    if (connectViewModelObNavigateChecked != null) {
                        // read connectViewModel.obNavigateChecked.get()
                        connectViewModelObNavigateCheckedGet = connectViewModelObNavigateChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obNavigateChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObNavigateCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObNavigateCheckedGet);
            }
            if ((dirtyFlags & 0x30008L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obBtConnectText
                        connectViewModelObBtConnectText = connectViewModel.obBtConnectText;
                    }
                    updateRegistration(3, connectViewModelObBtConnectText);


                    if (connectViewModelObBtConnectText != null) {
                        // read connectViewModel.obBtConnectText.get()
                        connectViewModelObBtConnectTextGet = connectViewModelObBtConnectText.get();
                    }
            }
            if ((dirtyFlags & 0x30010L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obDownloadEnable
                        connectViewModelObDownloadEnable = connectViewModel.obDownloadEnable;
                    }
                    updateRegistration(4, connectViewModelObDownloadEnable);


                    if (connectViewModelObDownloadEnable != null) {
                        // read connectViewModel.obDownloadEnable.get()
                        connectViewModelObDownloadEnableGet = connectViewModelObDownloadEnable.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obDownloadEnable.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObDownloadEnableGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObDownloadEnableGet);
            }
            if ((dirtyFlags & 0x30000L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.onCheckedChangeListener
                        connectViewModelOnCheckedChangeListener = connectViewModel.onCheckedChangeListener;
                    }
            }
            if ((dirtyFlags & 0x30020L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obChargeChecked
                        connectViewModelObChargeChecked = connectViewModel.obChargeChecked;
                    }
                    updateRegistration(5, connectViewModelObChargeChecked);


                    if (connectViewModelObChargeChecked != null) {
                        // read connectViewModel.obChargeChecked.get()
                        connectViewModelObChargeCheckedGet = connectViewModelObChargeChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obChargeChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObChargeCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObChargeCheckedGet);
            }
            if ((dirtyFlags & 0x30040L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obHandleViewShow
                        connectViewModelObHandleViewShow = connectViewModel.obHandleViewShow;
                    }
                    updateRegistration(6, connectViewModelObHandleViewShow);


                    if (connectViewModelObHandleViewShow != null) {
                        // read connectViewModel.obHandleViewShow.get()
                        connectViewModelObHandleViewShowGet = connectViewModelObHandleViewShow.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obHandleViewShow.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObHandleViewShowGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObHandleViewShowGet);
                if((dirtyFlags & 0x30040L) != 0) {
                    if(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObHandleViewShowGet) {
                            dirtyFlags |= 0x80000L;
                    }
                    else {
                            dirtyFlags |= 0x40000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
                    connectViewModelObHandleViewShowViewVISIBLEViewGONE = ((androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObHandleViewShowGet) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x30080L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obBtConnectEnable
                        connectViewModelObBtConnectEnable = connectViewModel.obBtConnectEnable;
                    }
                    updateRegistration(7, connectViewModelObBtConnectEnable);


                    if (connectViewModelObBtConnectEnable != null) {
                        // read connectViewModel.obBtConnectEnable.get()
                        connectViewModelObBtConnectEnableGet = connectViewModelObBtConnectEnable.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obBtConnectEnable.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObBtConnectEnableGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObBtConnectEnableGet);
            }
            if ((dirtyFlags & 0x30100L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obMappingChecked
                        connectViewModelObMappingChecked = connectViewModel.obMappingChecked;
                    }
                    updateRegistration(8, connectViewModelObMappingChecked);


                    if (connectViewModelObMappingChecked != null) {
                        // read connectViewModel.obMappingChecked.get()
                        connectViewModelObMappingCheckedGet = connectViewModelObMappingChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obMappingChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObMappingCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObMappingCheckedGet);
            }
            if ((dirtyFlags & 0x30200L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obCheckChecked
                        connectViewModelObCheckChecked = connectViewModel.obCheckChecked;
                    }
                    updateRegistration(9, connectViewModelObCheckChecked);


                    if (connectViewModelObCheckChecked != null) {
                        // read connectViewModel.obCheckChecked.get()
                        connectViewModelObCheckCheckedGet = connectViewModelObCheckChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obCheckChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObCheckCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObCheckCheckedGet);
            }
            if ((dirtyFlags & 0x30400L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obUploadEnable
                        connectViewModelObUploadEnable = connectViewModel.obUploadEnable;
                    }
                    updateRegistration(10, connectViewModelObUploadEnable);


                    if (connectViewModelObUploadEnable != null) {
                        // read connectViewModel.obUploadEnable.get()
                        connectViewModelObUploadEnableGet = connectViewModelObUploadEnable.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obUploadEnable.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObUploadEnableGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObUploadEnableGet);
            }
            if ((dirtyFlags & 0x30800L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obLoadMap
                        connectViewModelObLoadMap = connectViewModel.obLoadMap;
                    }
                    updateRegistration(11, connectViewModelObLoadMap);


                    if (connectViewModelObLoadMap != null) {
                        // read connectViewModel.obLoadMap.get()
                        connectViewModelObLoadMapGet = connectViewModelObLoadMap.get();
                    }
            }
            if ((dirtyFlags & 0x31000L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obBatteryValue
                        connectViewModelObBatteryValue = connectViewModel.obBatteryValue;
                    }
                    updateRegistration(12, connectViewModelObBatteryValue);


                    if (connectViewModelObBatteryValue != null) {
                        // read connectViewModel.obBatteryValue.get()
                        connectViewModelObBatteryValueGet = connectViewModelObBatteryValue.get();
                    }
            }
            if ((dirtyFlags & 0x32000L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obImuChecked
                        connectViewModelObImuChecked = connectViewModel.obImuChecked;
                    }
                    updateRegistration(13, connectViewModelObImuChecked);


                    if (connectViewModelObImuChecked != null) {
                        // read connectViewModel.obImuChecked.get()
                        connectViewModelObImuCheckedGet = connectViewModelObImuChecked.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obImuChecked.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObImuCheckedGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObImuCheckedGet);
            }
            if ((dirtyFlags & 0x34000L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obRosConnectStatus
                        connectViewModelObRosConnectStatus = connectViewModel.obRosConnectStatus;
                    }
                    updateRegistration(14, connectViewModelObRosConnectStatus);


                    if (connectViewModelObRosConnectStatus != null) {
                        // read connectViewModel.obRosConnectStatus.get()
                        connectViewModelObRosConnectStatusGet = connectViewModelObRosConnectStatus.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obRosConnectStatus.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObRosConnectStatusGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObRosConnectStatusGet);
            }
            if ((dirtyFlags & 0x38000L) != 0) {

                    if (connectViewModel != null) {
                        // read connectViewModel.obWifiStatus
                        connectViewModelObWifiStatus = connectViewModel.obWifiStatus;
                    }
                    updateRegistration(15, connectViewModelObWifiStatus);


                    if (connectViewModelObWifiStatus != null) {
                        // read connectViewModel.obWifiStatus.get()
                        connectViewModelObWifiStatusGet = connectViewModelObWifiStatus.get();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obWifiStatus.get())
                    androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObWifiStatusGet = androidx.databinding.ViewDataBinding.safeUnbox(connectViewModelObWifiStatusGet);
            }
        }
        // batch finished
        if ((dirtyFlags & 0x20000L) != 0) {
            // api target 1

            this.btnConnect.setOnClickListener(mCallback11);
            this.btnToolbox.setOnClickListener(mCallback12);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setListeners(this.cbImu, (android.widget.CompoundButton.OnCheckedChangeListener)null, cbImuandroidCheckedAttrChanged);
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setListeners(this.cbRecharge, (android.widget.CompoundButton.OnCheckedChangeListener)null, cbRechargeandroidCheckedAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.editTextIp, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, editTextIpandroidTextAttrChanged);
            this.navContinue.setOnClickListener(mCallback14);
            this.navStop.setOnClickListener(mCallback13);
        }
        if ((dirtyFlags & 0x30008L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.btnConnect, connectViewModelObBtConnectTextGet);
        }
        if ((dirtyFlags & 0x30080L) != 0) {
            // api target 1

            this.btnConnect.setSelected(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObBtConnectEnableGet);
        }
        if ((dirtyFlags & 0x30200L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbCheck, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObCheckCheckedGet);
        }
        if ((dirtyFlags & 0x30000L) != 0) {
            // api target 1

            this.cbCheck.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
            this.cbGmapping.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
            this.cbGoogle.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
            this.cbImu.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
            this.cbNavigation.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
            this.cbRecharge.setOnCheckedChangeListener(connectViewModelOnCheckedChangeListener);
        }
        if ((dirtyFlags & 0x30100L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbGmapping, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObMappingCheckedGet);
        }
        if ((dirtyFlags & 0x30001L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbGoogle, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObGoogleCheckedGet);
        }
        if ((dirtyFlags & 0x32000L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbImu, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObImuCheckedGet);
        }
        if ((dirtyFlags & 0x30004L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbNavigation, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObNavigateCheckedGet);
        }
        if ((dirtyFlags & 0x30020L) != 0) {
            // api target 1

            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(this.cbRecharge, androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObChargeCheckedGet);
        }
        if ((dirtyFlags & 0x30010L) != 0) {
            // api target 1

            this.downloadMap.setEnabled(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObDownloadEnableGet);
        }
        if ((dirtyFlags & 0x30002L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.editTextIp, connectViewModelObIpGet);
        }
        if ((dirtyFlags & 0x30800L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.loadMap, connectViewModelObLoadMapGet);
        }
        if ((dirtyFlags & 0x34000L) != 0) {
            // api target 1

            this.rosStatusConnect.setImageResource(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObRosConnectStatusGet);
        }
        if ((dirtyFlags & 0x30040L) != 0) {
            // api target 1

            this.roundMenuView.setVisibility(connectViewModelObHandleViewShowViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x30400L) != 0) {
            // api target 1

            this.uploadMap.setEnabled(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObUploadEnableGet);
        }
        if ((dirtyFlags & 0x31000L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.voltageText, connectViewModelObBatteryValueGet);
        }
        if ((dirtyFlags & 0x38000L) != 0) {
            // api target 1

            this.wifiState.setImageResource(androidxDatabindingViewDataBindingSafeUnboxConnectViewModelObWifiStatusGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // connectViewModel
                com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
                // connectViewModel != null
                boolean connectViewModelJavaLangObjectNull = false;



                connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
                if (connectViewModelJavaLangObjectNull) {


                    connectViewModel.onClickConnect();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // connectViewModel
                com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
                // connectViewModel != null
                boolean connectViewModelJavaLangObjectNull = false;



                connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
                if (connectViewModelJavaLangObjectNull) {


                    connectViewModel.onClickToolBox();
                }
                break;
            }
            case 3: {
                // localize variables for thread safety
                // connectViewModel
                com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
                // connectViewModel != null
                boolean connectViewModelJavaLangObjectNull = false;



                connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
                if (connectViewModelJavaLangObjectNull) {


                    connectViewModel.onClickStopNavigate();
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // connectViewModel
                com.eaibot.running.viewmodel.ConnectViewModel connectViewModel = mConnectViewModel;
                // connectViewModel != null
                boolean connectViewModelJavaLangObjectNull = false;



                connectViewModelJavaLangObjectNull = (connectViewModel) != (null);
                if (connectViewModelJavaLangObjectNull) {


                    connectViewModel.onClickContinueNavigate();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): connectViewModel.obGoogleChecked
        flag 1 (0x2L): connectViewModel.obIp
        flag 2 (0x3L): connectViewModel.obNavigateChecked
        flag 3 (0x4L): connectViewModel.obBtConnectText
        flag 4 (0x5L): connectViewModel.obDownloadEnable
        flag 5 (0x6L): connectViewModel.obChargeChecked
        flag 6 (0x7L): connectViewModel.obHandleViewShow
        flag 7 (0x8L): connectViewModel.obBtConnectEnable
        flag 8 (0x9L): connectViewModel.obMappingChecked
        flag 9 (0xaL): connectViewModel.obCheckChecked
        flag 10 (0xbL): connectViewModel.obUploadEnable
        flag 11 (0xcL): connectViewModel.obLoadMap
        flag 12 (0xdL): connectViewModel.obBatteryValue
        flag 13 (0xeL): connectViewModel.obImuChecked
        flag 14 (0xfL): connectViewModel.obRosConnectStatus
        flag 15 (0x10L): connectViewModel.obWifiStatus
        flag 16 (0x11L): connectViewModel
        flag 17 (0x12L): null
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
        flag 19 (0x14L): androidx.databinding.ViewDataBinding.safeUnbox(connectViewModel.obHandleViewShow.get()) ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}