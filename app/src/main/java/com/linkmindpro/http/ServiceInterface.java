package com.linkmindpro.http;


import com.linkmindpro.models.register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST("admin/web_service/register_api.php?")
    Call<RegisterResponse> register();
}
