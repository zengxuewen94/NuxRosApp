package com.nudtit.lib_common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author zengxuewen
 * @date 2020/11/16
 * @desc 权限申请的工具类，定制式机器
 * 出厂就需要把所有用到的权限申请好
 */
public class PermissionUtil {
    private static final String PERMISSION_LIST = "PERMISSION_LIST";
    private static final String PERMISSION_CONTENT = "PERMISSION_CONTENT";

    /**
     * 是否需要申请权限
     *
     * @param activity
     * @return
     */
    public static boolean isRequestPermission(Activity activity) {
        List<String> list = getPermissionList(activity);
        return list != null && !list.isEmpty();
    }

    /**
     * @return 需要申请的权限map
     */
    public static HashMap getMapPermission(Activity activity) {
        HashMap<String, Object> map = new HashMap<>(2);
        List<String> permissionList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (!XXPermissions.isGranted(activity, Permission.MANAGE_EXTERNAL_STORAGE)) {
                permissionList.add(Permission.MANAGE_EXTERNAL_STORAGE);
                stringBuffer.append("存储").append("/");
            }
        }else {
            if (!XXPermissions.isGranted(activity, Permission.READ_EXTERNAL_STORAGE)) {
                Collections.addAll(permissionList,Permission.READ_EXTERNAL_STORAGE);
                stringBuffer.append("存储").append("/");
            }

        }

        map.put(PERMISSION_LIST, permissionList);
        map.put(PERMISSION_CONTENT, TextUtils.isEmpty(stringBuffer.toString()) ? "" : stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));
        return map;

    }


    public static List<String> getPermissionList(Activity activity) {
        HashMap hashMap = getMapPermission(activity);
        return (List<String>) hashMap.get(PERMISSION_LIST);
    }





    public static String getPermissionContent(Activity activity) {
        HashMap hashMap = getMapPermission(activity);
        return (String) hashMap.get(PERMISSION_CONTENT);
    }

    //是否已经有权限了,或者是否已经给用户提示过了
    public static boolean mHasPermissionOrHasHinted = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean hasPermission(Context context) {
        if (BrandUtil.isBrandXiaoMi()) {
            if (Build.VERSION.SDK_INT >= 30 && !Settings.canDrawOverlays(context)) {
                return false;
            }
            return isXiaomiBgStartPermissionAllowed(context);
        } else if (BrandUtil.isBrandVivo()) {
            return isVivoBgStartPermissionAllowed(context);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return Settings.canDrawOverlays(context);
            } else {
                return hasPermissionBelowMarshmallow(context);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean hasPermissionOnActivityResult(Context context) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            return hasPermissionForO(context);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return hasPermissionBelowMarshmallow(context);
        }
    }

    /**
     * 6.0以下判断是否有权限
     * 理论上6.0以上才需处理权限，但有的国内rom在6.0以下就添加了权限
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static boolean hasPermissionBelowMarshmallow(Context context) {
        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Method dispatchMethod = AppOpsManager.class.getMethod("checkOp", int.class, int.class, String.class);
            return AppOpsManager.MODE_ALLOWED == (Integer) dispatchMethod.invoke(
                    manager, 24, Binder.getCallingUid(), context.getApplicationContext().getPackageName());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 用于判断8.0时是否有权限，仅用于OnActivityResult
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean hasPermissionForO(Context context) {
        try {
            WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (mgr == null) return false;
            View viewToAdd = new View(context);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
            viewToAdd.setLayoutParams(params);
            mgr.addView(viewToAdd, params);
            mgr.removeView(viewToAdd);
            return true;
        } catch (Exception e) {
            Log.e("TAG", "hasPermissionForO e:" + e.toString());
        }
        return false;
    }

    //小米后台拉起应用权限是否开启
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isXiaomiBgStartPermissionAllowed(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int op = 10021;
        try {
            Method method = appOpsManager.getClass().getMethod("checkOpNoThrow",
                    new Class[]{int.class, int.class, String.class});
            method.setAccessible(true);
            int result = (int) method.invoke(appOpsManager, op, android.os.Process.myUid(), context.getPackageName());
            return AppOpsManager.MODE_ALLOWED == result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Vivo后台拉起应用权限是否开启
    private static boolean isVivoBgStartPermissionAllowed(Context context) {
        try {
            Uri uri = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
            Cursor cursor = context.getContentResolver().query(uri, null, "pkgname = ?",
                    new String[]{context.getPackageName()},
                    null);
            if (cursor.moveToFirst()) {
                int state = cursor.getInt(cursor.getColumnIndex("currentstate"));
                return 0 == state;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
