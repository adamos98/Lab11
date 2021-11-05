package com.example.lab11;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("geo")
    Call<IPInfo> getIPInfo();
}
