package com.nudtit.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    public  boolean putBitmap(String name, Bitmap bitmap) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        paraCheck(sharedPreferences, name);
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            editor.putString(name, imageBase64);
            return editor.commit();
        }
    }

    public  Bitmap getBitmap(Context context, String name,
                                   Bitmap defaultValue) {
        if(sharedPreferences != null || TextUtils.isEmpty(name))
        paraCheck(sharedPreferences, name);
        String imageBase64 = sharedPreferences.getString(name, "");
        if (TextUtils.isEmpty(imageBase64)) {
            return defaultValue;
        }

        byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Bitmap ret = BitmapFactory.decodeStream(bais);
        if (ret != null) {
            return ret;
        } else {
            return defaultValue;
        }
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


    private static void paraCheck(SharedPreferences sp, String key) {
        if (sp == null) {
            throw new IllegalArgumentException();
        }
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
    }


    public boolean putByteString(String name, byte[] value) {
        if (editor == null || TextUtils.isEmpty(name)) {
            return false;
        }
        editor.putString(name, bytesToHexString(value));
        editor.commit();
        return true;
    }

    public byte[] getByteString(String name, String defaultValue) {
        if (sharedPreferences == null || TextUtils.isEmpty(name)) {
            return StringToBytes(defaultValue);
        }
        return StringToBytes(sharedPreferences.getString(name, defaultValue));
    }

    /**
     * desc:将数组转为16进制
     */
    public  String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:将16进制的数据转为数组
     */
    public  byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i);
            int int_ch3;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch3 = (hex_char1-48)*16;
          else if(hex_char1 >= 'A' && hex_char1 <='F')
            int_ch3 = (hex_char1-55)*16;
          else
            return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch4 = (hex_char2-48);
          else if(hex_char2 >= 'A' && hex_char2 <='F')
            int_ch4 = hex_char2-55;
          else
            return null;
            int_ch = int_ch3+int_ch4;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

}
