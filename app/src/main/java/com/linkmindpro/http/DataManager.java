package com.linkmindpro.http;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.linkmindpro.models.changepassword.ChangeRequest;
import com.linkmindpro.models.changepassword.ChangeResponse;
import com.linkmindpro.models.editprofile.EditProfileRequest;
import com.linkmindpro.models.editprofile.EditProfileResponse;
import com.linkmindpro.models.forgot.ForgotRequest;
import com.linkmindpro.models.forgot.ForgotResponse;
import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.models.patient.PatientRequest;
import com.linkmindpro.models.patient.PatientResponse;
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
        Call<RegisterResponse> call = getService().register(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.body().getErrorResponse());
                } else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
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
                } else dataManagerListener.onError(response.errorBody());
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
                } else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<SubsribeResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void forgot(Activity activity, ForgotRequest forgotRequest, final DataManagerListener dataManagerListener) {
        Call<ForgotResponse> call = getService().forgot(forgotRequest);
        call.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void patientList(Activity activity, PatientRequest patientRequest, final DataManagerListener dataManagerListener) {
        Call<PatientResponse> call = getService().patientList(patientRequest);
        call.enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(@NonNull Call<PatientResponse> call, @NonNull Response<PatientResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PatientResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void changePassword(Activity activity, ChangeRequest changeRequest, final DataManagerListener dataManagerListener) {
        Call<ChangeResponse> call = getService().changePassword(changeRequest);
        call.enqueue(new Callback<ChangeResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChangeResponse> call, @NonNull Response<ChangeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChangeResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getProfile(Activity activity, EditProfileRequest editProfileRequest, final DataManagerListener dataManagerListener) {
        Call<EditProfileResponse> call = getService().getProfile(editProfileRequest);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditProfileResponse> call, @NonNull Response<EditProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EditProfileResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateProfile(Activity activity, EditProfileRequest editProfileRequest, final DataManagerListener dataManagerListener) {
        Call<EditProfileResponse> call = getService().updateProfile(editProfileRequest);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditProfileResponse> call, @NonNull Response<EditProfileResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(SUCCESS))
                        dataManagerListener.onSuccess(response.body());
                    else dataManagerListener.onError(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EditProfileResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }


}
