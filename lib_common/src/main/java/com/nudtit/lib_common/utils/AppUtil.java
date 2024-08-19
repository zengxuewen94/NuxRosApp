package com.nudtit.lib_common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;

/**
 * @author :  zengxuewen
 * @date :  2021/6/10
 * @desc :  app基础工具类
 */
public class AppUtil {
    public static final String ROUTER_BUNDLE = "router_bundle";
    /**
     * service 传递数据常量
     */
    public static final String SERVICE_DATA="serviceData";
    /**
     * 启动服务
     *
     * @param context
     * @param packageName
     * @param serviceName
     */
    public static void openService(Context context, String packageName, String serviceName) {
        Intent intent = new Intent();
        intent.setClassName(packageName, serviceName);
        context.startService(intent);
    }
    /**
     * 启动服务
     *
     * @param context
     * @param packageName
     * @param serviceName
     * @param bundle
     */
    public static void openService(Context context, String packageName, String serviceName, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(packageName, serviceName);
        intent.putExtra(SERVICE_DATA,bundle);
        context.startService(intent);
    }
    /**
     * 关闭服务
     *
     * @param context
     * @param packageName
     * @param serviceName
     */
    public static void closeService(Context context, String packageName, String serviceName) {
        Intent intent = new Intent();
        intent.setClassName(packageName, serviceName);
        context.stopService(intent);
    }

    /**
     * 页面跳转-不带参数
     * @param packageContext
     * @param cls
     */
    public static void startActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
    }

    /**
     * 路由跳转
     * @param path
     * @param bundle 参数
     */
    public static void startActivity(String path,Bundle bundle) {
        ARouter.getInstance()
                .build(path)
                .withBundle(ROUTER_BUNDLE,bundle)
                .navigation();
    }
    /**
     * 根据包名查询对应应用信息
     * @param context
     * @param packageName
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 判断app是否存在
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppExist(Context context, String packageName) {
        try {
            PackageInfo info = getPackageInfo(context,packageName);
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 根据包名获取应用图标
     * @param context
     * @param packageName
     * @return
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        Drawable drawable = null;
        try {
            drawable = context.getPackageManager().getApplicationIcon(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }
    /**
     * 安装apk
     *
     * @param context
     * @param apkFilePath
     */
    public static void installApk(Context context, String apkFilePath) {
        File file = new File(apkFilePath);
        if (file.exists()) {
            // 通过Intent安装APK文件
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    /**
     * 卸载apk
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        PackageInfo info = getPackageInfo(context, packageName);
        if (info != null) {
            Uri uri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            context.startActivity(intent);
        }
    }
    /**
     *  获取版本号
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0)
                        .versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "4.0.0";
    }
}
