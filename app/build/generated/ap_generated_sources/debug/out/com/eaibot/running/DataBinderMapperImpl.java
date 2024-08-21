package com.eaibot.running;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.eaibot.running.databinding.ActivityCheckBindingImpl;
import com.eaibot.running.databinding.ActivityConnectBindingImpl;
import com.eaibot.running.databinding.ActivityLoginBindingImpl;
import com.eaibot.running.databinding.ActivityMainBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHECK = 1;

  private static final int LAYOUT_ACTIVITYCONNECT = 2;

  private static final int LAYOUT_ACTIVITYLOGIN = 3;

  private static final int LAYOUT_ACTIVITYMAIN = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.eaibot.running.R.layout.activity_check, LAYOUT_ACTIVITYCHECK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.eaibot.running.R.layout.activity_connect, LAYOUT_ACTIVITYCONNECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.eaibot.running.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.eaibot.running.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHECK: {
          if ("layout/activity_check_0".equals(tag)) {
            return new ActivityCheckBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_check is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCONNECT: {
          if ("layout/activity_connect_0".equals(tag)) {
            return new ActivityConnectBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_connect is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(4);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.nudtit.lib_common.DataBinderMapperImpl());
    result.add(new me.tatarka.bindingcollectionadapter2.DataBinderMapperImpl());
    result.add(new me.tatarka.bindingcollectionadapter2.recyclerview.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(5);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "checkViewModel");
      sKeys.put(2, "connectViewModel");
      sKeys.put(3, "loginViewModel");
      sKeys.put(4, "mainViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_check_0", com.eaibot.running.R.layout.activity_check);
      sKeys.put("layout/activity_connect_0", com.eaibot.running.R.layout.activity_connect);
      sKeys.put("layout/activity_login_0", com.eaibot.running.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.eaibot.running.R.layout.activity_main);
    }
  }
}
