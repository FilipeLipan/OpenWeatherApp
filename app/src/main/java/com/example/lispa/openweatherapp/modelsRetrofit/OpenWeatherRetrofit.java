package com.example.lispa.openweatherapp.modelsRetrofit;

import java.util.ArrayList;

/**
 * Created by lispa on 13/11/2016.
 */

public class OpenWeatherRetrofit {
    public CityRetrofit city;
    public ArrayList<DailyWeatherRetrofit> list;

    public CityRetrofit getCity() {
        return city;
    }


    public ArrayList<DailyWeatherRetrofit> getDaily() {
        return list;
    }
}
