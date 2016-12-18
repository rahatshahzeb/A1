package com.shahzeb.a1.util;

import android.util.Log;

import com.shahzeb.a1.A1Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.shahzeb.a1.model.A1Response;

public class NetworkManager{

    private final static String TAG = NetworkManager.class.getSimpleName();

    private Retrofit mRetrofit;
    private NetworkManagerInterface mNetworkManagerInterface;

    public static NetworkManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final NetworkManager INSTANCE = new NetworkManager();
    }

    public NetworkManagerInterface getInterface() {
        return mNetworkManagerInterface;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    //region Constructor
    private NetworkManager(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(A1Constants.URL_TARGET)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mNetworkManagerInterface = mRetrofit.create(NetworkManagerInterface.class);
    }
    //endregion

    //region ApiInterface
    public interface NetworkManagerInterface {
        @GET(A1Constants.URL_MANUFACTURER)
        Call<A1Response> getManufacturers(@Query(A1Constants.PARAM_WA_KEY) String waKey, @Query(A1Constants.PARAM_PAGE) int page, @Query(A1Constants.PARAM_PAGE_SIZE) int pageSize);

        @GET(A1Constants.URL_MAIN_TYPES)
        Call<A1Response> getMainTypes(@Query(A1Constants.PARAM_WA_KEY) String waKey, @Query(A1Constants.PARAM_MANUFACTURER) String manufacturer, @Query(A1Constants.PARAM_PAGE) int page, @Query(A1Constants.PARAM_PAGE_SIZE) int pageSize);

        @GET(A1Constants.URL_BUILT_DATES)
        Call<A1Response> getBuiltDates(@Query(A1Constants.PARAM_WA_KEY) String waKey, @Query(A1Constants.PARAM_MANUFACTURER) String manufacturer, @Query(A1Constants.PARAM_MAIN_TYPE) String mainType);
    }
    //endregion

    public void fetchManufacturers(int page, Callback callback) {
        Call<A1Response> call = NetworkManager.getInstance().getInterface().getManufacturers(A1Constants.KEY_WA, page, A1Constants.PAGE_SIZE);
        call.enqueue(callback);
    }

    public void fetchMainTypes(String manufacturer, int page, Callback callback) {
        Call<A1Response> call = NetworkManager.getInstance().getInterface().getMainTypes(A1Constants.KEY_WA, manufacturer, page, A1Constants.PAGE_SIZE);
        call.enqueue(callback);
    }

    public void fetchBuiltDates(String manufacturer, String mainType, Callback callback) {
        Call<A1Response> call = NetworkManager.getInstance().getInterface().getBuiltDates(A1Constants.KEY_WA, manufacturer, mainType);
        call.enqueue(callback);
    }

}
