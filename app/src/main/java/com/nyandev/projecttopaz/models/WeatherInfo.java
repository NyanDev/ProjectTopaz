package com.nyandev.projecttopaz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Niiaou on 12/05/2017.
 */

public class WeatherInfo {
    @SerializedName("coord") private Coordinates coordinates;
    @SerializedName("weather") private List<Weather> weatherList;
    @SerializedName("base") private String base;
    @SerializedName("main") private Main main;
    @SerializedName("visibility") private int visibility;
    @SerializedName("wind") private Wind wind;
    @SerializedName("clouds") private Clouds clouds;
    @SerializedName("dt") private long dt;
    @SerializedName("sys") private Sys sys;
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("cod") private int cod;


    public class Coordinates {
        @SerializedName("lat") private double latitude;
        @SerializedName("lon") private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public class Weather {
        @SerializedName("id") private int id;
        @SerializedName("main") private String main;
        @SerializedName("description") private String description;
        @SerializedName("icon") private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Main {
        @SerializedName("temp") private double temp;
        @SerializedName("pressure") private double pressure;
        @SerializedName("humidity") private double humidity;
        @SerializedName("temp_min") private double temp_min;
        @SerializedName("temp_max") private double temp_max;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
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

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
    }

    public class Wind {
        @SerializedName("speed") private double speed;
        @SerializedName("deg") private double deg;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public class Clouds {
        @SerializedName("all") private double all;

        public double getAll() {
            return all;
        }

        public void setAll(double all) {
            this.all = all;
        }
    }

    public class Sys {
        @SerializedName("type") private int type;
        @SerializedName("id") private int id;
        @SerializedName("message") private double message;
        @SerializedName("country") private String country;
        @SerializedName("sunrise") private long sunrise;
        @SerializedName("sunset") private long sunset;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
