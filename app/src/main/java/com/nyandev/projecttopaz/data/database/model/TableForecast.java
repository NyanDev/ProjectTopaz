package com.nyandev.projecttopaz.data.database.model;

import com.nyandev.projecttopaz.data.database.WeatherDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by xuan- on 03/03/2018.
 */

@Table(database = WeatherDatabase.class)
public class TableForecast extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
            @ForeignKey(tableClass = TableWeather.class,
            references = @ForeignKeyReference(columnName = "weatherId", foreignKeyColumnName = "id"))
    int weatherId;

    @Column
    long day;

    @Column
    double temperature;

    @Column
    int icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
