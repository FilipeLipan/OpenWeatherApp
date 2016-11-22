package com.example.lispa.openweatherapp.api;

import com.example.lispa.openweatherapp.modelsRetrofit.OpenWeatherRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lispa on 13/11/2016.
 */

public interface OpenWeatherService {

    public static final String OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/";

    //http://api.openweathermap.org/data/2.5/forecast/daily?q=Curitiba&mode=json&cnt=7&appid=ce64ccbb4b9a569e13850f57bdbfff9c
    @GET("/data/2.5/forecast/daily")
    Call<OpenWeatherRetrofit> getWeatherByCity (@Query("q") String city,
                                                @Query("mode")  String mode,
                                                @Query("cnt") String cnt,
                                                @Query("appid") String Api);
}
