package com.linkmindpro.http;

import com.linkmindpro.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        if (retrofit == null)  {
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
}
