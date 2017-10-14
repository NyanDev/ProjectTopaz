package com.nyandev.projecttopaz.utils;

/**
 * Created by xuan- on 02/07/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nyandev.projecttopaz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utility {

    String[] bft_label_array;

    public enum WindDirection {

    }

    public static float fahrenheitToKelvin(float fahrenheitTemp){
        return (((5 * fahrenheitTemp) / 9) - 32) + 273.15f;
    }

    public static float celsiusToKelvin(float celsiusTemp){
        return celsiusTemp + 273.15f;
    }

    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static float kelvinToFahrenheit(float kelvinTemp) {
        return (((9 * kelvinToCelsius(kelvinTemp)) / 5) + 32);
    }

    public static float convertTemperature(float temperature, SharedPreferences sharedPreferences){
        if (sharedPreferences.getString("temperatureUnit", "°C").equals("°F")){
            return kelvinToFahrenheit(temperature);
        } else if (sharedPreferences.getString("temperatureUnit", "°C").equals("°C")){
            return kelvinToCelsius(temperature);
        } return temperature;
    }

    public static String windDegreeToDirection(double degree){
        int numberOfDirections = 8;

        double tmp = (degree / 360 ) * numberOfDirections;
        tmp %= numberOfDirections;
        int index = (int) Math.floor(tmp);
        String[] directions = {"north", "north_east",
                                "east", "south_east",
                                "south", "south_west",
                                "west", "north_west"};

        return directions[index];
    }

    public static float convertPressure(float pressure, SharedPreferences sharedPreferences){
        if (sharedPreferences.getString("pressureUnit", "hPa").equals("mmHg")){
            return pressure * 0.7500616827042f;
        } else if (sharedPreferences.getString("pressureUnit", "hPa").equals("b")){
            return pressure / 100;
        } else {
            // pressure in hPa
            return pressure;
        }
    }

    public static float convertWindSpeed(float windSpeed, SharedPreferences sharedPreferences){
        if (sharedPreferences.getString("speedUnit", "m/s").equals("mph")){
            return windSpeed * 2.236936292054f;
        } else if (sharedPreferences.getString("speedUnit", "m/s").equals("kn")){
            return windSpeed * 1.943844492441f;
        } else if (sharedPreferences.getString("speedUnit", "m/s").equals("kph")){
            return windSpeed * 3.6f;
        } else if (sharedPreferences.getString("speedUnit", "m/s").equals("bft")){
            if (windSpeed < 0.3){
                return 0; // Calm
            } else if (windSpeed < 1.6){
                return 1; // Light Air
            } else if (windSpeed < 3.4){
                return 2; // Light Breeze
            } else if (windSpeed < 5.5){
                return 3; // Gentle Breeze
            } else if (windSpeed < 8.0){
                return 4; // Moderate Breeze
            } else if (windSpeed < 10.8){
                return 5; // Fresh Breeze
            } else if (windSpeed < 13.9){
                return 6; // Strong Breeze
            } else if (windSpeed < 17.2){
                return 7; // Near Gale
            } else if (windSpeed < 20.8){
                return 8; // Gale
            } else if (windSpeed < 24.5){
                return 9; // Severe Gale
            } else if (windSpeed < 28.5){
                return 10; // Storm
            } else if (windSpeed < 32.7){
                return 11; //  Violent Storm
            } else {
                return 12; // Hurricane
            }
        } else {
            return windSpeed;
        }
    }

    public static String getBeaufortName(int beaufortIndex, Context context){
        if(beaufortIndex == 0) {
            return context.getString(R.string.beaufort_label_0);
        }
        else if (beaufortIndex == 1) {
            return context.getString(R.string.beaufort_label_1);
        }
        else if (beaufortIndex == 2) {
            return context.getString(R.string.beaufort_label_2);
        }
        else if (beaufortIndex == 3) {
            return context.getString(R.string.beaufort_label_3);
        }
        else if (beaufortIndex == 4) {
            return context.getString(R.string.beaufort_label_4);
        }
        else if (beaufortIndex == 5) {
            return context.getString(R.string.beaufort_label_5);
        }
        else if (beaufortIndex == 6) {
            return context.getString(R.string.beaufort_label_6);
        }
        else if (beaufortIndex == 7) {
            return context.getString(R.string.beaufort_label_7);
        }
        else if (beaufortIndex == 8) {
            return context.getString(R.string.beaufort_label_8);
        }
        else if (beaufortIndex == 9) {
            return context.getString(R.string.beaufort_label_9);
        }
        else if (beaufortIndex == 10) {
            return context.getString(R.string.beaufort_label_10);
        }
        else if (beaufortIndex == 11) {
            return context.getString(R.string.beaufort_label_11);
        }
        else {
            return context.getString(R.string.beaufort_label_12);
        }
    }


}