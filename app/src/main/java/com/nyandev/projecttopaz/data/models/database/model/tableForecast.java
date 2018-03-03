package com.nyandev.projecttopaz.data.models.database.model;

import com.nyandev.projecttopaz.data.models.database.WeatherDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by xuan- on 03/03/2018.
 */

@Table(database = WeatherDatabase.class)
public class tableForecast {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
            @ForeignKey(tableClass = tableWeather.class,
            references = @ForeignKeyReference(columnName = "weatherId", foreignKeyColumnName = "id"))
    int weatherId;

    @Column
    String day;

    @Column
    double temperature;

    public void setId(int id) {
        this.id = id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
