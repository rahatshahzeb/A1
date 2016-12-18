package com.shahzeb.a1.util;

import android.app.Activity;
import android.view.WindowManager;

public class A1Util {

    public static void hideSoftKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
