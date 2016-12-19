package com.shahzeb.a1.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.FrameLayout;

import com.shahzeb.a1.A1Constants;
import com.shahzeb.a1.BaseActivity;
import com.shahzeb.a1.R;
import com.shahzeb.a1.fragment.SummaryFragment;
import com.shahzeb.a1.fragment.UniversalFragment;
import com.shahzeb.a1.model.Summary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements UniversalFragment.OnFragmentInteractionListener{

    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.fragment_container)
    public FrameLayout mParentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addFragment(getSupportFragmentManager(),
                UniversalFragment.newInstance(A1Constants.FragmentType.MANUFACTURER, null, null),
                R.id.fragment_container,
                A1Constants.TAG_FRAGMENT_MANUFACTURER,
                false, true);
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

    @Override
    public void onFragmentInteraction(A1Constants.FragmentType fragmentType, String manufacturer, String mainType, String builtDate) {
        if (fragmentType == A1Constants.FragmentType.MAIN_TYPE) {
            replaceFragment(getSupportFragmentManager(),
                    UniversalFragment.newInstance(A1Constants.FragmentType.MAIN_TYPE, manufacturer, mainType),
                    R.id.fragment_container, A1Constants.TAG_FRAGMENT_MAIN_TYPE, true, true);
        } else if (fragmentType == A1Constants.FragmentType.BUILT_DATES) {
            replaceFragment(getSupportFragmentManager(),
                    UniversalFragment.newInstance(A1Constants.FragmentType.BUILT_DATES, manufacturer, mainType),
                    R.id.fragment_container, A1Constants.TAG_FRAGMENT_BUILT_DATES, true, true);
        } else if (fragmentType == A1Constants.FragmentType.SUMMARY) {
            replaceFragment(getSupportFragmentManager(),
                    SummaryFragment.newInstance(new Summary(manufacturer, mainType, builtDate)),
                    R.id.fragment_container, A1Constants.TAG_FRAGMENT_SUMMARY, true, true);
        }
    }
}
