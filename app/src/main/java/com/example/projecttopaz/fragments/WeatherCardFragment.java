package com.example.projecttopaz.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecttopaz.SharedApp;
import com.example.projecttopaz.R;
import com.example.projecttopaz.adapters.WeatherCardRecyclerAdapter;
import com.example.projecttopaz.adapters.WeatherExpandableForecastAdapter;
import com.example.projecttopaz.events.AllPurposeEvent;
import com.example.projecttopaz.events.WeatherForecastEvent;
import com.example.projecttopaz.interfaces.WeatherService;
import com.example.projecttopaz.models.WeatherDay;
import com.example.projecttopaz.models.WeatherForecast;
import com.example.projecttopaz.utils.NetworkRequest;
import com.example.projecttopaz.utils.SwipeHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by xuan- on 26/08/2017.
 */

public class WeatherCardFragment extends Fragment {

    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    final WeatherService weatherService = SharedApp.getInstance().getRetrofit().create(WeatherService.class);

    public LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    public WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;
    public RecyclerView weatherRecyclerView;
    public String apiKey = "56f39e475af4af7888645b0a20f3ae03";

    public ListViewCompat weatherExpandedForecastListView;
    public WeatherExpandableForecastAdapter weatherExpandableForecastAdapter;
    public WeatherForecast weatherForecast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView(view);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when an event is posted
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onMessageEvent(AllPurposeEvent event){
        if(event.getMessage().equals("mustUpdateData")){
            // update data
            weatherCardRecyclerAdapter.notifyDataSetChanged();
        } else if (event.getMessage().equals("fetchNewData")) {
            updateWeatherCard();
        } else {
            fetchWeatherCity(event.getMessage(), apiKey);
            weatherCardRecyclerAdapter.notifyDataSetChanged();
        }
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onWeatherForecastEvent(WeatherForecastEvent event){
        fetchWeatherForecastsForCityName(event.getPosition(), event.getLocation(), 5, apiKey);
    }

    public void initView(View view){
        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(view.getContext());
        weatherRecyclerView = (RecyclerView)view.findViewById(R.id.rv_weatherCard);
        weatherRecyclerView.setLayoutManager(linearLayoutManager);
        weatherRecyclerView.setAdapter(weatherCardRecyclerAdapter);

        ItemTouchHelper.Callback callback = new SwipeHelper(weatherCardRecyclerAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(weatherRecyclerView);
    }

    public void fetchWeatherCity(final String city, final String apiKey){
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
