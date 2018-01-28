package com.linkmindpro.http;


import com.linkmindpro.models.changepassword.ChangeRequest;
import com.linkmindpro.models.changepassword.ChangeResponse;
import com.linkmindpro.models.chat.GetChatRequest;
import com.linkmindpro.models.chat.GetChatResponse;
import com.linkmindpro.models.chat.SendChatRequest;
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

    @POST("patient_list_api.php?")
    Call<PatientResponse> patientList(@Body PatientRequest patientRequest);

    @POST("change_password_api.php?")
    Call<ChangeResponse> changePassword(@Body ChangeRequest changeRequest);

    @POST("edit_profile_api.php?")
    Call<EditProfileResponse> getProfile(@Body EditProfileRequest editProfileRequest);

    @POST("edit_profile_api.php?")
    Call<EditProfileResponse> updateProfile(@Body EditProfileRequest editProfileRequest);

    @POST("chat_view_api.php?")
    Call<GetChatResponse> getChat(@Body GetChatRequest getChatRequest);

    @POST("send_message_api.php?")
    Call<GetChatResponse> sendChat(@Body SendChatRequest sendChatRequest);
}
