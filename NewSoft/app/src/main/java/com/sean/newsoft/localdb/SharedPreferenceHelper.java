package com.sean.newsoft.localdb;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sean.newsoft.enums.LocalData;



public class SharedPreferenceHelper {
    private static SharedPreferenceHelper instance;
    private SharedPreferences prefs;
    private SharedPreferenceHelper(Context context) {
        prefs = context.getSharedPreferences("com.sean.newsoft.prefs",Context.MODE_PRIVATE);
    }
    public static SharedPreferenceHelper getInstance(){
        if(instance  != null){
            return instance;
        }else {
            throw new RuntimeException("Please call init(context) function first");
        }
    }
    public static void init(Context context){
        instance = new SharedPreferenceHelper(context);
    }

    public <T> T getValue(LocalData data){
        String cache = prefs.getString(data.name(),null);
        if(cache != null){
            return (T) new Gson().fromJson(cache,data.getType());
        }
        return null;
    }
    public <T> void putValue(LocalData data,T value){
        String cache = new Gson().toJson(value);
        prefs.edit().putString(data.name(),cache).apply();

    }



}
