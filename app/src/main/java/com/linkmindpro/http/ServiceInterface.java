package com.linkmindpro.http;


import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.models.subscribe.SubsribeRequest;
import com.linkmindpro.models.subscribe.SubsribeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ServiceInterface {


    @GET("register_api.php?")
    Call<RegisterResponse> register(@QueryMap Map<String, String> options);

    @POST("login_api.php?")
//    Call<LoginResponse> login(@QueryMap Map<String, String> options);
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("add_patient_invitation_api.php?")
    Call<SubsribeResponse> subscribe(@QueryMap Map<String, String> options);
}
