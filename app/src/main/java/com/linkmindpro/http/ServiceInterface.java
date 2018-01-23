package com.linkmindpro.http;


import com.linkmindpro.models.forgot.ForgotRequest;
import com.linkmindpro.models.forgot.ForgotResponse;
import com.linkmindpro.models.login.LoginRequest;
import com.linkmindpro.models.login.LoginResponse;
import com.linkmindpro.models.register.RegisterRequest;
import com.linkmindpro.models.register.RegisterResponse;
import com.linkmindpro.models.subscribe.SubsribeRequest;
import com.linkmindpro.models.subscribe.SubsribeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST("register_api.php?")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("login_api.php?")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("add_patient_invitation_api.php?")
    Call<SubsribeResponse> subscribe(@Body SubsribeRequest subsribeRequest);

    @POST("forgot_password_api.php?")
    Call<ForgotResponse> forgot(@Body ForgotRequest forgotRequest);
}
