package com.nudtit.lib_common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author houdeming
 * @date 2018/4/16
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * @author caojian
     * @date 2020/05/29
     * @remorks bug ID： 1000644，无网络的情况下，已选择宣传文件进行播放，点击【网络更新】后，不会播放宣传文件。
     * bug 产生原因：设备接了网口NetworkUtils.isConnected方法依然会返回true，导致判断失误，引起bug，
     * 现在添加判断无线网络连接判断，只判断wifi和数据网络连接情况。
     */
    public static boolean isWirelessNetConnected(Context context) {
        if (context != null) {
            if (isWifiConnected(context)) {
                return true;
            } else if (isMobleConnected(context)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数据网络是否连接
     *
     * @param context
     * @return
     */
    private static boolean isMobleConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (networkInfo != null) {
                    NetworkInfo.State mWiFiNetworkInfo = networkInfo.getState();
                    if (mWiFiNetworkInfo == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * WIFI是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo != null) {
                    NetworkInfo.State mWiFiNetworkInfo = networkInfo.getState();
                    if (mWiFiNetworkInfo == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 打开WIFI
    @SuppressLint("WrongConstant")
    public static void openWifi(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);
        LogUtils.d(TAG, "open wifi");
    }

    // 关闭WIFI
    public static boolean closeWifi(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager.isWifiEnabled()) {
            return mWifiManager.setWifiEnabled(false);
        }
        return false;
    }

    /**
     * 获取连接的WiFi的名称
     *
     * @param context
     * @return
     */
    public static String getConnectWifiName(Context context) {
        if (context != null) {
            try {
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (manager != null) {
                    WifiInfo info = manager.getConnectionInfo();
                    if (info != null) {
                        //"@Hyatt_WiFi" 获取的ssid是带双引号的
                        String sSId = info.getSSID();
                        Log.i(TAG, "sSId=" + sSId);
                        if (!TextUtils.isEmpty(sSId)) {
                            return sSId.substring(1, sSId.length() - 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses(); enumeration.hasMoreElements(); ) {
                    InetAddress inetAddress = enumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toLowerCase();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isWifiEnable(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return manager.isWifiEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 连接WiFi
     *
     * @param context
     * @param wifiName
     * @param wifiPwd
     */
    public static boolean connectWifi(Context context, String wifiName, String wifiPwd) {
        Log.i(TAG, "wifiName=" + wifiName + ",wifiPwd=" + wifiPwd);
        if (!TextUtils.isEmpty(wifiName)) {
            try {
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiConfiguration configuration = createWifiInfo(wifiName, wifiPwd, WifiCipherType.WIFICIPHER_NOPASS);
                if (configuration != null) {
                    int netID = manager.addNetwork(configuration);
                    Log.i(TAG, "netID=" + netID);
                    boolean isEnable = manager.enableNetwork(netID, true);
                    Log.i(TAG, "isEnable=" + isEnable);
                    if (isEnable) {
                        boolean reconnect = manager.reconnect();
                        Log.i(TAG, "reconnect=" + reconnect);
                        return reconnect;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "error=" + e.getMessage());
            }
        }
        return false;
    }

    /**
     * 定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
     */
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    private static WifiConfiguration createWifiInfo(String ssid, String password, WifiCipherType type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";
        if (type == WifiCipherType.WIFICIPHER_NOPASS) {
            // config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            // config.wepTxKeyIndex = 0;
        } else if (type == WifiCipherType.WIFICIPHER_WEP) {
            if (!TextUtils.isEmpty(password)) {
                if (isHexWepKey(password)) {
                    config.wepKeys[0] = password;
                } else {
                    config.wepKeys[0] = "\"" + password + "\"";
                }
            }
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WifiCipherType.WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private static boolean isHexWepKey(String wepKey) {
        final int len = wepKey.length();
        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }
        return isHex(wepKey);
    }

    private static boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')) {
                return false;
            }
        }
        return true;
    }
}
