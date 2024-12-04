package com.wildysheverando.jaykomp.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;
import java.util.Map;

public interface ApiService {
    // endpoint api untuk login
    @POST("/login")
    Call<Map<String, String>> login(@Body Map<String, String> credentials);

    // endpoint api untuk daftar email
    @POST("/register")
    Call<Map<String, String>> register(@Body Map<String, String> credentials);

    // Endpoint untuk forget
    @POST("/forget")
    Call<Map<String, String>> forget(@Body Map<String, String> email);
}