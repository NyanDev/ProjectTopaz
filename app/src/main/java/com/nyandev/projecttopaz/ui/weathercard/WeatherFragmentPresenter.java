package com.nyandev.projecttopaz.ui.weathercard;

import android.content.Context;

import com.nyandev.projecttopaz.App;
import com.nyandev.projecttopaz.data.adapters.WeatherCardRecyclerAdapter;
import com.nyandev.projecttopaz.data.models.WeatherDay;
import com.nyandev.projecttopaz.data.models.WeatherForecast;
import com.nyandev.projecttopaz.data.models.WeatherInfo;
import com.nyandev.projecttopaz.data.models.database.model.TableForecast;
import com.nyandev.projecttopaz.data.models.database.model.TableWeather;
import com.nyandev.projecttopaz.data.models.database.model.TableWeather_Table;
import com.nyandev.projecttopaz.data.models.interfaces.WeatherService;
import com.nyandev.projecttopaz.utils.NetworkRequest;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import retrofit2.Retrofit;

import static com.nyandev.projecttopaz.data.models.interfaces.WeatherService.API_KEY;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherFragmentPresenter {

    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Inject
    Retrofit retrofit;
    WeatherService weatherService;
    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;
    Context context;

    public WeatherFragmentPresenter(Context context){
        ((App)context.getApplicationContext()).getNetComponent().inject(this);
        this.context = context;
        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(context);
        weatherService = retrofit.create(WeatherService.class);
    }

    @DebugLog
    public void fetchWeatherCity(final String city){
        NetworkRequest.perform(weatherService.fetchWeatherForCity(city, API_KEY), weatherInfo -> {
            weatherCardRecyclerAdapter.addWeather(weatherInfo);
            populateWeatherInDatabase(city, weatherInfo);
            fetchWeatherForecastsForCityName(weatherCardRecyclerAdapter.getItemCount()-1, city, 5, API_KEY);
        });
    }

    public boolean isWeatherInDatabase(String city){
        boolean isInBase;

        isInBase = SQLite.select()
                .from(TableWeather.class)
                .where(TableWeather_Table.cityName.eq(city))
                .count() > 0;

        return isInBase;
    }

    public boolean isWeatherUpToDate(String city, long day){
        long wDay;
        wDay = SQLite.select()
                .from(TableWeather.class)
                .where(TableWeather_Table.cityName.eq(city))
                .querySingle().getDay();

        return (Math.abs(day) - Math.abs(wDay) <= 2 * 60 * 60 * 1000); // update frequency of openweathermap
    }

    @DebugLog
    public void populateWeatherInDatabase(String city, WeatherInfo weatherInfo){
        if (isWeatherInDatabase(city)){
            long day = weatherInfo.getDt();
            TableWeather tableWeather = new TableWeather();
            if (!isWeatherUpToDate(city, day)) {
                tableWeather = SQLite.select()
                        .from(TableWeather.class)
                        .where(TableWeather_Table.cityName.eq(city))
                        .querySingle();
            }

            int icon = weatherInfo.getWeatherList().get(0).getId();
            String description = weatherInfo.getWeatherList().get(0).getDescription();
            double wSpeed = weatherInfo.getWind().getSpeed();
            double wDegree = weatherInfo.getWind().getDeg();
            double pressure = weatherInfo.getMain().getPressure();
            double humidity = weatherInfo.getMain().getHumidity();

            tableWeather.setDay(day);
            tableWeather.setCityName(city);
            tableWeather.setIcon(icon);
            tableWeather.setDescription(description);
            tableWeather.setWindSpeed(wSpeed);
            tableWeather.setWindDegree(wDegree);
            tableWeather.setPressure(pressure);
            tableWeather.setHumidity(humidity);
            tableWeather.save();
        }
    }

    @DebugLog
    public void populateForecastsInDatabase(String city, WeatherForecast weatherForecast){
        int weatherId = SQLite.select()
                .from(TableWeather.class)
                .where(TableWeather_Table.cityName.eq(city))
                .querySingle().getId();

        for (int i = 1; i < weatherForecast.getDays().size(); i++) {
            WeatherDay weatherDay = weatherForecast.getDays().get(i);
            TableForecast tableForecast = new TableForecast();
            tableForecast.setDay(weatherDay.getDt());
            tableForecast.setWeatherId(weatherId);
            tableForecast.setTemperature(weatherDay.getTemp().getDay());
            tableForecast.save();
        }
    }

    @DebugLog
    public void fetchWeatherForecastsForCityName(final int position, String city, int count, String apiKey){
        NetworkRequest.perform(weatherService.fetchWeatherForecastsForCityName(city, count, apiKey), weatherForecast -> {
            ArrayList<WeatherDay> weatherDays = new ArrayList<>();
            for (int i = 1; i < weatherForecast.getDays().size(); i++) {
                weatherDays.add(weatherForecast.getDays().get(i));
            }
            populateForecastsInDatabase(city, weatherForecast);
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
            updateWeatherCity(i, city, API_KEY);
        }
        weatherCardRecyclerAdapter.notifyDataSetChanged();
    }
}
