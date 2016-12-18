package com.shahzeb.a1;

import android.support.v4.app.Fragment;

import com.shahzeb.a1.adapter.RecyclerViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements Callback, RecyclerViewAdapter.RecyclerViewAdapterCallback{

    @Override
    public void onResponse(Call call, Response response){}

    @Override
    public void onFailure(Call call, Throwable t){}

    @Override
    public void onItemClick(int position){}
}
