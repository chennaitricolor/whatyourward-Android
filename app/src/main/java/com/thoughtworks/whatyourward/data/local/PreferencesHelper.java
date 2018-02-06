package com.thoughtworks.whatyourward.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {


    private final SharedPreferences preferences;

    @Inject
    PreferencesHelper(SharedPreferences sharedPreferences) {
        preferences = sharedPreferences;
    }

    public void putString(String key,String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putInt( String key,  int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
