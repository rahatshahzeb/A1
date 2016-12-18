package com.shahzeb.a1.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.shahzeb.a1.A1Constants;
import com.shahzeb.a1.BaseActivity;
import com.shahzeb.a1.R;
import com.shahzeb.a1.fragment.ManufactureFragment;
import com.shahzeb.a1.util.NetworkManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements Callback{

    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.fragment_container)
    public FrameLayout mParentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        NetworkManager.getInstance().fetchManufacturers(0, 10, this);
        addFragment(getSupportFragmentManager(),
                ManufactureFragment.newInstance(),
                R.id.fragment_container,
                A1Constants.TAG_FRAGMENT_MANUFACTURER,
                false, true);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.code() == A1Constants.HTTP_SUCCESS ) {
            Log.v(TAG, "Success");
        } else {
            Log.v(TAG, "Error");
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.v(TAG, "Failed");
    }

    @Override
    public void onNetworkStatusChanged(boolean isConnected) {
        super.onNetworkStatusChanged(isConnected);

        if (mParentView != null) {
            if (isConnected) {
                Snackbar.make(mParentView, getString(R.string.network_success), Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(mParentView, getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
