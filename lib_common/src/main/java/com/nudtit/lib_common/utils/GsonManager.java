package com.nudtit.lib_common.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public enum GsonManager {
    INSTANCE;
    public Gson gson = new Gson();

    public Object getObject(String json, Class<?> cls) {
        Object object = gson.fromJson(json, cls);
        return object;
    }


    public Object getObject(String json, Type type) {
        Object object = gson.fromJson(json, type);
        return object;
    }

    public String Object2String(Class<?> cls) {
        String json = gson.toJson(cls);
        return json;
    }
}
