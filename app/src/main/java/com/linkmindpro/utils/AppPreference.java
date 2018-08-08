package com.linkmindpro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppPreference {

    private static final String APP_SHARED_PREFERENCE = "Apps_Preferences";
    private static SharedPreferences sSharedPreferences;
    private static AppPreference sAppPreference;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    private AppPreference(Context mContext) {
        sSharedPreferences = mContext.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        mEditor = sSharedPreferences.edit();
    }

    public static AppPreference getAppPreference(Context mContext) {
        if (sAppPreference == null)
            sAppPreference = new AppPreference(mContext);

        return sAppPreference;
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public String getString(String key) {
        return sSharedPreferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public Boolean getBoolean(String key) {
        return sSharedPreferences.getBoolean(key, false);
    }

    void putInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    int getInt(String key) {
        return sSharedPreferences.getInt(key, 0);
    }

    public void putObject(String key, Object t) {
        Gson gson = new Gson();
        String valueAsString = gson.toJson(t);
        mEditor.putString(key, valueAsString);
        mEditor.apply();
    }

    public <T> T getObject(String key, Class<T> t) {
        Gson gson = new Gson();
        return gson.fromJson(getString(key), t);
    }

    public void remove(String key) {
        mEditor.remove(key);
        mEditor.apply();
    }
}
