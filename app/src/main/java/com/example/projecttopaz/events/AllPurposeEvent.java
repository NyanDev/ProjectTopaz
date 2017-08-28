package com.example.projecttopaz.events;

import com.example.projecttopaz.models.WeatherForecast;

/**
 * Created by xuan- on 15/08/2017.
 */

public class AllPurposeEvent {
    private final String message;

    public AllPurposeEvent(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
