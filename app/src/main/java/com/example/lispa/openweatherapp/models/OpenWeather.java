package com.example.lispa.openweatherapp.models;

import java.util.ArrayList;

/**
 * Created by lispa on 18/11/2016.
 */

public class OpenWeather {
    private String city;
    private ArrayList<DailyWeather> dailyWeather;

    public OpenWeather() {
        dailyWeather = new ArrayList<DailyWeather>();
    }

    public OpenWeather(String city) {
        this.city = city;
        dailyWeather = new ArrayList<DailyWeather>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<DailyWeather> getDailyWeather() {
        return dailyWeather;
    }

    public void setDailyWeather(ArrayList<DailyWeather> dailyWeather) {
        this.dailyWeather = dailyWeather;
    }
}
