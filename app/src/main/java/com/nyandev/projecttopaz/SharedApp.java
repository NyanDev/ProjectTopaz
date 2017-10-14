package com.nyandev.projecttopaz;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuan- on 16/09/2017.
 */

public class SharedApp extends Application {
    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    static SharedApp app;
    HttpLoggingInterceptor logging;
    OkHttpClient.Builder httpClient;

    Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }
    public static SharedApp getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        logging  = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder().addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
