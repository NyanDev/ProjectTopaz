package com.nyandev.projecttopaz.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Niiaou on 21/05/2017.
 */

public class WeatherForecast {
    @SerializedName("city") private City city;
    @SerializedName("code") private int cod;
    @SerializedName("message") private double message;
    @SerializedName("cnt") private int cnt;
    @SerializedName("list") private List<WeatherDay> days;

    public class City{
        @SerializedName("id") private int id;
        @SerializedName("name") private String name;
        @SerializedName("coord") private Coord coord;
        @SerializedName("country") private String country;
        @SerializedName("population") private int population;

        public class Coord {
            @SerializedName("lon") private double lon;
            @SerializedName("lat") private double lat;

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
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

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherDay> getDays() {
        return days;
    }

    public void setDays(List<WeatherDay> days) {
        this.days = days;
    }
}
