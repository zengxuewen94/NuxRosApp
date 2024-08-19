package com.nudtit.lib_common.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        if (!XXPermissions.isGranted(activity, Permission.CAMERA)) {
            permissionList.add(Permission.CAMERA);
            stringBuffer.append("相机").append("/");
        }
        if (!XXPermissions.isGranted(activity, Permission.MANAGE_EXTERNAL_STORAGE)) {
            permissionList.add(Permission.MANAGE_EXTERNAL_STORAGE);
            stringBuffer.append("存储").append("/");
        }
        if (!XXPermissions.isGranted(activity, Permission.RECORD_AUDIO)) {
            permissionList.add(Permission.RECORD_AUDIO);
            stringBuffer.append("录音").append("/");
        }
        if (!XXPermissions.isGranted(activity, Permission.ACCESS_FINE_LOCATION)) {
            permissionList.add(Permission.ACCESS_FINE_LOCATION);
            stringBuffer.append("位置").append("/");
        }
        if (!XXPermissions.isGranted(activity, Permission.SEND_SMS)) {
            permissionList.add(Permission.SEND_SMS);
            stringBuffer.append("发送短信").append("/");
        }
        if (!XXPermissions.isGranted(activity, Permission.SYSTEM_ALERT_WINDOW)) {
            permissionList.add(Permission.SYSTEM_ALERT_WINDOW);
            stringBuffer.append("显示在其他应用的上层").append("/");
        }

        if (!XXPermissions.isGranted(activity, Permission.REQUEST_INSTALL_PACKAGES)) {
            permissionList.add(Permission.REQUEST_INSTALL_PACKAGES);
            stringBuffer.append("安装应用").append("/");
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

    /**
     * 是否需要申请勿扰模式权限
     *
     * @return
     */
    public static boolean requestNotificationPolicyPermission(Activity activity) {
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        return !notificationManager.isNotificationPolicyAccessGranted();
    }


}
