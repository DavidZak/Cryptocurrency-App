package com.example.mradmin.rxjavatestproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mrAdmin on 16.12.2017.
 */

public interface CryptoClient {

    @GET("/v1/ticker/")
    Call<List<CryptoEntity>> getCryptoInfo();

}
