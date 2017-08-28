package com.example.projecttopaz.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projecttopaz.R;
import com.example.projecttopaz.adapters.WeatherCardRecyclerAdapter;
import com.example.projecttopaz.adapters.WeatherExpandableForecastAdapter;
import com.example.projecttopaz.events.AllPurposeEvent;
import com.example.projecttopaz.events.WeatherForecastEvent;
import com.example.projecttopaz.interfaces.WeatherService;
import com.example.projecttopaz.models.WeatherDay;
import com.example.projecttopaz.models.WeatherForecast;
import com.example.projecttopaz.models.WeatherInfo;
import com.example.projecttopaz.utils.SwipeHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xuan- on 26/08/2017.
 */

public class WeatherCardFragment extends Fragment {

    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(httpClient.build())
            .build();
    final WeatherService weatherService = retrofit.create(WeatherService.class);

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
        fetchWeatherCity(event.getMessage(), apiKey);
        weatherCardRecyclerAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = false)
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

    public void fetchWeatherCity(String city, String apiKey){
        try{
            final Observable<WeatherInfo> weatherInformationObservable = weatherService.fetchWeatherForCity(city, apiKey);
            Observer weatherInfoObserver = new Observer() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                @Override
                public void onCompleted() {
                    Log.i("Completed", "weatherInformation");
                    weatherInformationObservable.unsubscribeOn(Schedulers.newThread());
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Error", e.getMessage());
                    Log.e("fetchWeatherCity", "city not found");
                    Toast.makeText(getActivity(), "Oops, an error occured", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(Object o) {
                    WeatherInfo weatherInfo = (WeatherInfo) o;
                    weatherCardRecyclerAdapter.addWeather(weatherInfo);
                }
            };
            weatherInformationObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherInfoObserver);
        } catch (Exception e){
            Log.e("fetchWeatherCity", e.getMessage());
        }
    }

    public void fetchWeatherForecastsForCityName(final int position, String city, int count, String apiKey){
        try{
            final Observable<WeatherForecast> weatherForecastObservable = weatherService.fetchWeatherForecastsForCityName(city, count, apiKey);
            Observer weatherInformationObserver = new Observer() {
                @Override
                public void onCompleted() {
                    Log.i("Completed", "fetchWeatherForecastForCity completed");
                    weatherForecastObservable.unsubscribeOn(Schedulers.newThread());
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Error", e.getMessage());
                }

                @Override
                public void onNext(Object o) {
                    weatherForecast = (WeatherForecast) o;
                    ArrayList<WeatherDay> weatherDays = new ArrayList<>();
                    for (int i = 1; i < weatherForecast.getDays().size(); i++) {
                        weatherDays.add(weatherForecast.getDays().get(i));
                    }
                    weatherCardRecyclerAdapter.addWeatherForecast(position, weatherDays);
                    weatherCardRecyclerAdapter.notifyDataSetChanged();
                }
            };
            weatherForecastObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weatherInformationObserver);
        } catch (Exception e){
            Log.e("fetchWeatherForecast", e.getMessage());
        }
    }

}
