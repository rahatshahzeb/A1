package com.shahzeb.a1.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shahzeb.a1.util.NetworkUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private OnNetworkChangeListener onNetworkChangeListener;

    public NetworkChangeReceiver(OnNetworkChangeListener onNetworkChangeListener) {
        this.onNetworkChangeListener = onNetworkChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = NetworkUtil.isNetworkConnected(context);
        onNetworkChangeListener.onNetworkStatusChanged(isConnected);
    }
}
