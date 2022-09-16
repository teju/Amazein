package com.amazein.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    private SharedPreferences app_preferences;
    public static String SHOW_TAG_DETAILS = "show_tag_details";
    public Preferences(Context context) {
        init(context);
    }

    public void init(Context context) {
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void putData(String key ,boolean value) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public boolean getData(String key) {
        return  app_preferences.getBoolean(key, false);

    }
}
