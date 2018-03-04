package com.nyandev.projecttopaz.ui.weathercard;

import android.content.Context;
import android.widget.Toast;

import com.nyandev.projecttopaz.App;
import com.nyandev.projecttopaz.data.adapters.WeatherCardAdapterDB;
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

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import retrofit2.Retrofit;

import static com.nyandev.projecttopaz.data.models.interfaces.WeatherService.API_KEY;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherFragmentPresenter {

    @Inject
    Retrofit retrofit;
    WeatherService weatherService;
    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;
    WeatherCardAdapterDB weatherCardAdapterDB;
    Context context;

    public WeatherFragmentPresenter(Context context){
        ((App)context.getApplicationContext()).getNetComponent().inject(this);
        this.context = context;
        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(context);
        weatherCardAdapterDB = new WeatherCardAdapterDB(context);
        weatherService = retrofit.create(WeatherService.class);
    }

    @DebugLog
    public void fetchWeatherCity(final String city){
        boolean exist = isWeatherInDatabase(city);
        if (!exist) {
            NetworkRequest.perform(weatherService.fetchWeatherForCity(city, API_KEY), weatherInfo -> {
                populateWeatherInDatabase(city, weatherInfo, exist);
                fetchWeatherForecastsForCityName(city, 5, API_KEY);
                weatherCardAdapterDB.updateWeather();
            }, error -> {
                Toast.makeText(context, "City not found", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(context, "City is already in the list", Toast.LENGTH_SHORT).show();
        }
    }

    @DebugLog
    public void fetchWeatherForecastsForCityName(String city, int count, String apiKey){
        NetworkRequest.perform(weatherService.fetchWeatherForecastsForCityName(city, count, apiKey), weatherForecast -> {
            populateForecastsInDatabase(city, weatherForecast);
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
    public void populateWeatherInDatabase(String city, WeatherInfo weatherInfo, boolean exist){
        TableWeather tableWeather = new TableWeather();
        long day;
        if (exist) {
            day = weatherInfo.getDt();
            if (!isWeatherUpToDate(city, day)) {
                tableWeather = SQLite.select()
                        .from(TableWeather.class)
                        .where(TableWeather_Table.cityName.eq(city))
                        .querySingle();
            }
        }

        day = weatherInfo.getDt();
        int icon = weatherInfo.getWeatherList().get(0).getId();
        String description = weatherInfo.getWeatherList().get(0).getDescription();
        double wSpeed = weatherInfo.getWind().getSpeed();
        double wDegree = weatherInfo.getWind().getDeg();
        double pressure = weatherInfo.getMain().getPressure();
        double humidity = weatherInfo.getMain().getHumidity();
        double temperature = weatherInfo.getMain().getTemp();

        tableWeather.setDay(day);
        tableWeather.setCityName(city);
        tableWeather.setIcon(icon);
        tableWeather.setDescription(description);
        tableWeather.setWindSpeed(wSpeed);
        tableWeather.setWindDegree(wDegree);
        tableWeather.setPressure(pressure);
        tableWeather.setHumidity(humidity);
        tableWeather.setTemperature(temperature);
        tableWeather.save();
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
            tableForecast.setIcon(weatherDay.getWeather().get(0).getId());
            tableForecast.save();
        }
    }


}
