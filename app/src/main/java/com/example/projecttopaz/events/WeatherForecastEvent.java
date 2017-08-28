package com.example.projecttopaz.events;

import com.example.projecttopaz.models.WeatherForecast;

/**
 * Created by xuan- on 27/08/2017.
 */

public class WeatherForecastEvent {
    private final int position;
    private final String location;

    public WeatherForecastEvent(int position, String location) {
        this.position = position;
        this.location = location;
    }

    public int getPosition(){
        return position;
    }

    public String getLocation(){ return location; }
}
