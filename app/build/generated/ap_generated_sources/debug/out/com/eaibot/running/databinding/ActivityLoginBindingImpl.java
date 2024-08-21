package com.eaibot.running.databinding;
import com.eaibot.running.R;
import com.eaibot.running.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLoginBindingImpl extends ActivityLoginBinding implements com.eaibot.running.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rememberPwd, 5);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback16;
    @Nullable
    private final android.view.View.OnClickListener mCallback15;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityLoginBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ActivityLoginBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (android.widget.TextView) bindings[4]
            , (android.widget.Button) bindings[3]
            , (android.widget.FrameLayout) bindings[0]
            , (android.widget.CheckBox) bindings[5]
            , (android.widget.EditText) bindings[1]
            , (android.widget.EditText) bindings[2]
            );
        this.changeLanguage.setTag(null);
        this.login.setTag(null);
        this.loginView.setTag(null);
        this.userName.setTag(null);
        this.userPwd.setTag(null);
        setRootTag(root);
        // listeners
        mCallback16 = new com.eaibot.running.generated.callback.OnClickListener(this, 2);
        mCallback15 = new com.eaibot.running.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
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
        if (BR.loginViewModel == variableId) {
            setLoginViewModel((com.eaibot.running.viewmodel.LoginViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLoginViewModel(@Nullable com.eaibot.running.viewmodel.LoginViewModel LoginViewModel) {
        this.mLoginViewModel = LoginViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.loginViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLoginViewModelObPassword((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeLoginViewModelObUserName((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeLoginViewModelObLanguageText((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLoginViewModelObPassword(androidx.databinding.ObservableField<java.lang.String> LoginViewModelObPassword, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLoginViewModelObUserName(androidx.databinding.ObservableField<java.lang.String> LoginViewModelObUserName, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLoginViewModelObLanguageText(androidx.databinding.ObservableField<java.lang.String> LoginViewModelObLanguageText, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
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
        androidx.databinding.ObservableField<java.lang.String> loginViewModelObPassword = null;
        androidx.databinding.ObservableField<java.lang.String> loginViewModelObUserName = null;
        java.lang.String loginViewModelObUserNameGet = null;
        java.lang.String loginViewModelObLanguageTextGet = null;
        java.lang.String loginViewModelObPasswordGet = null;
        androidx.databinding.ObservableField<java.lang.String> loginViewModelObLanguageText = null;
        com.eaibot.running.viewmodel.LoginViewModel loginViewModel = mLoginViewModel;

        if ((dirtyFlags & 0x1fL) != 0) {


            if ((dirtyFlags & 0x19L) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.obPassword
                        loginViewModelObPassword = loginViewModel.obPassword;
                    }
                    updateRegistration(0, loginViewModelObPassword);


                    if (loginViewModelObPassword != null) {
                        // read loginViewModel.obPassword.get()
                        loginViewModelObPasswordGet = loginViewModelObPassword.get();
                    }
            }
            if ((dirtyFlags & 0x1aL) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.obUserName
                        loginViewModelObUserName = loginViewModel.obUserName;
                    }
                    updateRegistration(1, loginViewModelObUserName);


                    if (loginViewModelObUserName != null) {
                        // read loginViewModel.obUserName.get()
                        loginViewModelObUserNameGet = loginViewModelObUserName.get();
                    }
            }
            if ((dirtyFlags & 0x1cL) != 0) {

                    if (loginViewModel != null) {
                        // read loginViewModel.obLanguageText
                        loginViewModelObLanguageText = loginViewModel.obLanguageText;
                    }
                    updateRegistration(2, loginViewModelObLanguageText);


                    if (loginViewModelObLanguageText != null) {
                        // read loginViewModel.obLanguageText.get()
                        loginViewModelObLanguageTextGet = loginViewModelObLanguageText.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x1cL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.changeLanguage, loginViewModelObLanguageTextGet);
        }
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.changeLanguage.setOnClickListener(mCallback16);
            this.login.setOnClickListener(mCallback15);
        }
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, loginViewModelObUserNameGet);
        }
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userPwd, loginViewModelObPasswordGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 2: {
                // localize variables for thread safety
                // loginViewModel != null
                boolean loginViewModelJavaLangObjectNull = false;
                // loginViewModel
                com.eaibot.running.viewmodel.LoginViewModel loginViewModel = mLoginViewModel;



                loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
                if (loginViewModelJavaLangObjectNull) {


                    loginViewModel.changeLanguage();
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // loginViewModel != null
                boolean loginViewModelJavaLangObjectNull = false;
                // loginViewModel
                com.eaibot.running.viewmodel.LoginViewModel loginViewModel = mLoginViewModel;



                loginViewModelJavaLangObjectNull = (loginViewModel) != (null);
                if (loginViewModelJavaLangObjectNull) {


                    loginViewModel.onLogin();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): loginViewModel.obPassword
        flag 1 (0x2L): loginViewModel.obUserName
        flag 2 (0x3L): loginViewModel.obLanguageText
        flag 3 (0x4L): loginViewModel
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}