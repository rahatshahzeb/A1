package com.shahzeb.a1;

import com.orm.SugarApp;
import com.shahzeb.a1.preference.PreferenceManager;

import butterknife.ButterKnife;

public class A1Application extends SugarApp {

    public static final String TAG = A1Application.class
            .getSimpleName();

    private static A1Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        ButterKnife.setDebug(true);
        PreferenceManager.initializeInstance(this);

    }

    public static synchronized A1Application getInstance() {
        return mInstance;
    }

}
