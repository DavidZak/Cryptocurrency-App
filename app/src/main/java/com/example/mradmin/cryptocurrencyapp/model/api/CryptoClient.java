package com.example.mradmin.cryptocurrencyapp.model.api;

import com.example.mradmin.cryptocurrencyapp.model.CryptoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mrAdmin on 16.12.2017.
 */

public interface CryptoClient {

    @GET("/v1/ticker/")
    Observable<List<CryptoEntity>> getCryptoInfo(@Query("convert") String convertValue);

    @GET("/v1/ticker/{id}")
    Observable<List<CryptoEntity>> getCryptoDetail(@Path("id") String id);

}
