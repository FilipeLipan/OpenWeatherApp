package com.example.lispa.openweatherapp.modelsRetrofit;

/**
 * Created by lispa on 13/11/2016.
 */
public class TemperatureRetrofit {
    public double day;
    public double min;
    public double max;

    public double getTemp() {
        return day;
    }

    public double getTempMin() {
        return min;
    }

    public double getTempMax() {
        return max;
    }
}
