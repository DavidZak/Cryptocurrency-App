package com.example.mradmin.rxjavatestproject.model.api;

import com.example.mradmin.rxjavatestproject.model.CryptoEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mrAdmin on 16.12.2017.
 */

public interface CryptoClient {

    @GET("/v1/ticker/")
    Observable<List<CryptoEntity>> getCryptoInfo();

    @GET("/v1/ticker/{id}")
    Observable<List<CryptoEntity>> getCryptoDetail(@Path("id") String id);

}
