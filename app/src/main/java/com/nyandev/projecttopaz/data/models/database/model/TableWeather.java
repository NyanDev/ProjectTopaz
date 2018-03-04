package com.nyandev.projecttopaz.data.models.database.model;

import com.nyandev.projecttopaz.data.models.database.WeatherDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by xuan- on 03/03/2018.
 */

@Table(database = WeatherDatabase.class)
public class TableWeather extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String cityName;

    @Column
    long day;

    @Column
    String description;

    @Column
    int icon;

    @Column
    double pressure;

    @Column
    double humidity;

    @Column
    double windSpeed;

    @Column
    double windDegree;

    public List<TableForecast> forecasts;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "forecasts")
    public List<TableForecast> getForecasts(){
        if (forecasts == null || forecasts.isEmpty()){
            forecasts = SQLite.select()
                    .from(TableForecast.class)
                    .where(TableForecast_Table.weatherId.eq(id))
                    .queryList();
        }
        return forecasts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public void setForecasts(List<TableForecast> forecasts) {
        this.forecasts = forecasts;
    }
}
