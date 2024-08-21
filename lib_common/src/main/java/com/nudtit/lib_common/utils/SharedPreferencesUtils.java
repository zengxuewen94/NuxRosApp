package com.nudtit.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author song
 * @date 2021/6/23
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils sInstance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences("sp_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public synchronized static SharedPreferencesUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (sInstance == null) {
                    sInstance = new SharedPreferencesUtils(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public boolean putBoolean(String name, boolean value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putBoolean(name, value);
        editor.commit();
        return true;
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(name, defaultValue);
    }

    public boolean putString(String name, String value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putString(name, value);
        editor.commit();
        return true;
    }

    public String getString(String name, String defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return sharedPreferences.getString(name, defaultValue);
    }

    public boolean putLong(String name, long value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putLong(name, value);
        editor.commit();
        return true;
    }

    public long getLong(String name, long defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return sharedPreferences.getLong(name, defaultValue);
    }

    public boolean putInt(String name, int value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putInt(name, value);
        editor.commit();
        return true;
    }

    public int getInt(String name, int defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return sharedPreferences.getInt(name, defaultValue);
    }

    public boolean putFloat(String name, float value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putFloat(name, value);
        editor.commit();
        return true;
    }

    public float getFloat(String name, float defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return sharedPreferences.getFloat(name, defaultValue);
    }

    public boolean removeValue(String key) {
        if (TextUtils.isEmpty(key) || sharedPreferences == null || editor == null) {
            return false;
        }
        if (!sharedPreferences.contains(key)) {
            return false;
        }
        editor.remove(key);
        editor.commit();
        return true;
    }


    public boolean putDouble(String name, double value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putLong(name, Double.doubleToRawLongBits(value));
        editor.commit();
        return true;
    }

    public Double getDouble(String name, Double defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return defaultValue;
        }
        return Double.longBitsToDouble(sharedPreferences.getLong(name, 0));
    }


    public boolean clearData() {
        if (editor == null) {
            return false;
        }
        editor.clear();
        editor.commit();
        return true;
    }

    public Object getObject(String key, Class<?> cls) {
        String json = getString(key, null);
        Gson gson = new Gson();
        Object object = gson.fromJson(json, cls);
        return object;
    }


    public Object getObject(String key, Type type) {
        String json = getString(key, null);
        Gson gson = new Gson();
        Object object = gson.fromJson(json, type);
        return object;
    }

    /**
     * 保存序列化的实体类
     *
     * @param key
     * @param object
     */
    public boolean putObject(String key, Object object) {
        if (object == null) {
            return false;
        }
        Gson gson = new Gson();
        String json = gson.toJson(object);
        putString(key, json);
        return true;
    }


}
