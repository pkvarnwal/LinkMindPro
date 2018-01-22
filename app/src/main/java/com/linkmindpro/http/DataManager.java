package com.linkmindpro.http;

import android.app.Activity;

import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.models.subscribe.SubsribeRequest;
import com.linkmindpro.models.subscribe.SubsribeResponse;
import com.linkmindpro.utils.AppConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager implements AppConstant {

    private static Retrofit retrofit = null;

    private DataManager() {
    }

    private static class DataManagerSingleton {
        private static final DataManager INSTANCE = new DataManager();
    }

    public static DataManager getInstance() {
        return DataManagerSingleton.INSTANCE;
    }

    private ServiceInterface getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ServiceInterface.class);
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build();


    public interface DataManagerListener {
        void onSuccess(Object response);

        void onError(Object response);
    }

    public void register(Activity activity, RegisterRequest registerRequest, final DataManagerListener dataManagerListener) {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", name);
//        map.put("email", email);
//        map.put("password", password);

//        Call<RegisterResponse> call = getService().register(map);
        Call<RegisterResponse> call = getService().register(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.body().getErrorResponse());
                }
                dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void login(Activity activity, LoginRequest loginRequest, final DataManagerListener dataManagerListener) {
        Call<LoginResponse> call = getService().login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.body().getErrorResponse());
                }
                dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void subscribe(Activity activity, SubsribeRequest subsribeRequest, final DataManagerListener dataManagerListener) {
        Call<SubsribeResponse> call = getService().subscribe(subsribeRequest);
        call.enqueue(new Callback<SubsribeResponse>() {
            @Override
            public void onResponse(Call<SubsribeResponse> call, Response<SubsribeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.body().getErrorResponse());
                }
                dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<SubsribeResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
}
