package com.genry.phonegalleryandroid._Application;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class AppPreferences {
    private AppPreferences() {}

    private static final AppPreferences preferences = new AppPreferences();
    public static AppPreferences getInstance() {
        return preferences;
    }

    public void setPref(String name, Object value) {
        SharedPreferences.Editor editor = App.MainContext.getSharedPreferences(AppConstants.PREFERENCE_STORAGE_NAME, MODE_PRIVATE).edit();
        editor.putString(name, value.toString());
        editor.apply();
    }

    public String getPref(String name) {
        SharedPreferences pref = App.MainContext.getSharedPreferences(AppConstants.PREFERENCE_STORAGE_NAME, MODE_PRIVATE);
        return pref.getString(name, "-1");
    }

    public void removePref(String name) {
        SharedPreferences.Editor editor = App.MainContext.getSharedPreferences(AppConstants.PREFERENCE_STORAGE_NAME, MODE_PRIVATE).edit();
        editor.remove(name);
        editor.apply();
    }
}
