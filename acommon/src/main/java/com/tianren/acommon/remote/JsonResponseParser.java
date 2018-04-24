package com.tianren.acommon.remote;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author Mr.Qiu
 * @date 2018/4/23
 */
public class JsonResponseParser {
    private static final String TAG = "JsonResponseParser";
    private Gson gson;

    public JsonResponseParser() {
        gson = new Gson();
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        Log.w(TAG, "fromJson: " + json);
        return gson.fromJson(json, classOfT);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        Log.w(TAG, "fromJson: " + json);
        return gson.fromJson(json, typeOfT);
    }

    public String toJson(Object src) {
        String r = gson.toJson(src);
        Log.w(TAG, "toJson: " + r);
        return r;
    }
}
