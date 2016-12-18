package com.shahzeb.a1;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.shahzeb.a1.reciever.NetworkChangeReceiver;
import com.shahzeb.a1.reciever.OnNetworkChangeListener;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnNetworkChangeListener{

    protected boolean isNetworkConnected;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (networkChangeReceiver == null) {
            networkChangeReceiver = new NetworkChangeReceiver(this);
        }
        registerReceiver(networkChangeReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void addFragment(FragmentManager fm, Fragment fragment, int containerId, String tag, boolean addToBackStack, boolean shouldAnimate) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment == null) {
            transaction.add(containerId, fragment, tag);
            if (shouldAnimate)
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);
            if (addToBackStack)
                transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    protected void removeFragment(FragmentManager fm, String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment existingFragment = fm.findFragmentByTag(tag);
        if (existingFragment != null) {
            transaction.remove(existingFragment);
            transaction.commit();
        }
    }

    public void replaceFragment(FragmentManager fm, Fragment fragment, int containerId, String tag, boolean addToBackStack, boolean shouldAnimate) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (shouldAnimate) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

    }

    protected Fragment getFragmentByTag(FragmentManager fm, String tag) {
        if (fm != null) {
            Fragment fragment = fm.findFragmentByTag(tag);
            return fragment;
        }
        return null;
    }

    @Override
    public void onNetworkStatusChanged(boolean isConnected) {
        isNetworkConnected = isConnected;
    }
}
