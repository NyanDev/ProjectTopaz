package com.nyandev.projecttopaz.interfaces;

import com.nyandev.projecttopaz.models.WeatherForecast;
import com.nyandev.projecttopaz.models.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Niiaou on 12/05/2017.
 */

public interface WeatherService {

    String API_KEY = "56f39e475af4af7888645b0a20f3ae03";

    @GET("/data/2.5/weather")
    Observable<WeatherInfo> fetchWeatherForCity(@Query("q") String city,
                                                @Query("apikey") String apiKey);

    @GET("/data/2.5/forecast/daily")
    Observable<WeatherForecast> fetchWeatherForecastsForCity(@Query("id") int cityId,
                                                             @Query("cnt") int count,
                                                             @Query("apikey") String apiKey);

    @GET("/data/2.5/forecast/daily")
    Observable<WeatherForecast> fetchWeatherForecastsForCityName(@Query("q") String city,
                                                             @Query("cnt") int count,
                                                             @Query("apikey") String apiKey);

    @GET("/data/2.5/weather")
    Observable<WeatherInfo> fetchWeatherWithGeoCoord(@Query("lat") double latitude,
                                                     @Query("lon") double longitude,
                                                     @Query("apikey") String apiKey);

}
