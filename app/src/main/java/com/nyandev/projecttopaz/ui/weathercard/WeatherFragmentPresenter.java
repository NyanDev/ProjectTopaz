package com.nyandev.projecttopaz.ui.weathercard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;

import com.nyandev.projecttopaz.SharedApp;
import com.nyandev.projecttopaz.adapters.WeatherCardRecyclerAdapter;
import com.nyandev.projecttopaz.adapters.WeatherExpandableForecastAdapter;
import com.nyandev.projecttopaz.models.WeatherDay;
import com.nyandev.projecttopaz.models.WeatherForecast;
import com.nyandev.projecttopaz.models.interfaces.WeatherService;
import com.nyandev.projecttopaz.utils.NetworkRequest;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherFragmentPresenter {

    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    final WeatherService weatherService = SharedApp.getInstance().getRetrofit().create(WeatherService.class);

    public String apiKey = "56f39e475af4af7888645b0a20f3ae03";

    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;
    Context context;

    public WeatherFragmentPresenter(Context context){
        this.context = context;
        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(context);
    }

    @DebugLog
    public void fetchWeatherCity(final String city){
        NetworkRequest.perform(weatherService.fetchWeatherForCity(city, apiKey), weatherInfo -> {
            weatherCardRecyclerAdapter.addWeather(weatherInfo);
            fetchWeatherForecastsForCityName(weatherCardRecyclerAdapter.getItemCount()-1, city, 5, apiKey);
        });
    }

    public void fetchWeatherForecastsForCityName(final int position, String city, int count, String apiKey){
        NetworkRequest.perform(weatherService.fetchWeatherForecastsForCityName(city, count, apiKey), weatherForecast -> {
            ArrayList<WeatherDay> weatherDays = new ArrayList<>();
            for (int i = 1; i < weatherForecast.getDays().size(); i++) {
                weatherDays.add(weatherForecast.getDays().get(i));
            }
            weatherCardRecyclerAdapter.addWeatherForecast(position, weatherDays);
        });
    }

    public void fetchWeatherCityWithGeoCoord(final double lat, final double lon, final String apiKey){
        NetworkRequest.perform(weatherService.fetchWeatherWithGeoCoord(lat, lon, apiKey), weatherInfo -> {
            weatherCardRecyclerAdapter.addWeather(weatherInfo);
            fetchWeatherForecastsForCityName(weatherCardRecyclerAdapter.getItemCount()-1, weatherInfo.getName(), 5, apiKey);
        });
    }

    public void updateWeatherCity(final int position, final String city, final String apiKey){
        NetworkRequest.perform(weatherService.fetchWeatherForCity(city, apiKey), weatherInfo -> {
            weatherCardRecyclerAdapter.setWeatherInfo(position, weatherInfo);
            fetchWeatherForecastsForCityName(weatherCardRecyclerAdapter.getItemCount()-1, city, 5, apiKey);
        });
    }

    public void updateWeatherCard(){
        String city;
        for (int i = 0; i < weatherCardRecyclerAdapter.getItemCount(); i++){
            city = weatherCardRecyclerAdapter.getWeatherInfo(i).getName();
            if (city.isEmpty()){
                return;
            }
            updateWeatherCity(i, city, apiKey);
        }
        weatherCardRecyclerAdapter.notifyDataSetChanged();
    }
}
