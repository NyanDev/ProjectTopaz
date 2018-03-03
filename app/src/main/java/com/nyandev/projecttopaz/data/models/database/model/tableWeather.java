package com.nyandev.projecttopaz.data.models.database.model;

import com.nyandev.projecttopaz.data.models.database.WeatherDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by xuan- on 03/03/2018.
 */

@Table(database = WeatherDatabase.class)
public class tableWeather {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String cityName;

    @Column
    String description;

    @Column
    String icon;

    @Column
    double pressure;

    @Column
    double humidity;

    @Column
    double windSpeed;

    @Column
    double windDegree;

    public List<tableForecast> forecasts;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "forecasts")
    public List<tableForecast> getForecasts(){
        if (forecasts == null || forecasts.isEmpty()){
            forecasts = SQLite.select()
                    .from(tableForecast.class)
                    .where(tableForecast_Table.weatherId.eq(id))
                    .queryList();
        }
        return forecasts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public void setForecasts(List<tableForecast> forecasts) {
        this.forecasts = forecasts;
    }
}
