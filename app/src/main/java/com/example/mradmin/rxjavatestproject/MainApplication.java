package com.example.mradmin.rxjavatestproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mradmin.rxjavatestproject.model.api.CryptoClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mrAdmin on 16.12.2017.
 */

public class MainApplication extends Application {

    private OkHttpClient.Builder okHttp;
    private Retrofit.Builder builder;
    private Retrofit retrofit;

    private static CryptoClient cryptoClient;

    private static SharedPreferences dataSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        okHttp =  new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttp.sslSocketFactory(getSSLSocketFactory());
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttp.addInterceptor(logging);

        builder = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp.build());

        retrofit = builder.build();

        cryptoClient = retrofit.create(CryptoClient.class);

        dataSharedPreferences = getSharedPreferences("crypto_data", Context.MODE_PRIVATE);
    }

    //for prefs============================
    public static SharedPreferences getDataSharedPreferences() {
        return dataSharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences preferences, Map<String, String> keyPairs) {
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry<String, String> entry : keyPairs.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }
    //======================

    public static CryptoClient getCryptoAPI() {
        return cryptoClient;
    }

    //for SSL =======================
    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }

    }
}
