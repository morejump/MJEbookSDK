package com.mjebooksdk.android.sample.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {
    public static final String LAST_READ_LOCATOR_KEY = "LAST_READ_LOCATOR_KEY";
    private static SharedPreferences instance;

    private SharePreferenceManager() {
    }

    public static SharedPreferences init(Context context) {
        if (instance == null) {
            instance = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        return instance;
    }

    public static String getLastReadLocator(String key) {
        return instance.getString(LAST_READ_LOCATOR_KEY, "");
    }

    public static void setLastReadLocator(String value) {
        if (value == null || value.isEmpty()) return;
        SharedPreferences.Editor prefsEditor = instance.edit();
        prefsEditor.putString(LAST_READ_LOCATOR_KEY, value);
        prefsEditor.commit();
    }

}
