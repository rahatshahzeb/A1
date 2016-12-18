package com.shahzeb.a1;

import android.support.v4.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements Callback{

    @Override
    public abstract void onResponse(Call call, Response response);

    @Override
    public abstract void onFailure(Call call, Throwable t);
}
