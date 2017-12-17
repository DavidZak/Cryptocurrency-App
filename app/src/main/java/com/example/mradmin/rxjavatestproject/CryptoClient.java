package com.example.mradmin.rxjavatestproject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by mrAdmin on 16.12.2017.
 */

public interface CryptoClient {

    @GET("/v1/ticker/")
    Observable<List<CryptoEntity>> getCryptoInfo();

}
