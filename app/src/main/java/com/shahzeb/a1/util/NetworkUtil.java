package com.shahzeb.a1.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    // Whether there is a Wi-Fi connection.
    public static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    public static boolean mobileConnected = false;

    // Checks the network connection and sets the wifiConnected and mobileConnected
    // variables accordingly.
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnectedOrConnecting()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            return true;
        } else {
            wifiConnected = false;
            mobileConnected = false;
            return false;
        }
    }

    public boolean isWifiConnected() {
        return wifiConnected;
    }

    public boolean isMobileConnected() {
        return mobileConnected;
    }
}