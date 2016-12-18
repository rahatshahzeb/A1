package com.shahzeb.a1.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "com.shahzeb.a1.preference.PREF";
    private static final String KEY_USER_PHONE = "com.shahzeb.a1.preference.USER_PHONE";

    private static PreferenceManager sInstance;
    private final SharedPreferences mPref;

    private PreferenceManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
    }

    public static synchronized PreferenceManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferenceManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setUserPhone(String value) {
        mPref.edit()
                .putString(KEY_USER_PHONE, value)
                .commit();
    }

    public String getUserPhone() {
        return mPref.getString(KEY_USER_PHONE, null);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
