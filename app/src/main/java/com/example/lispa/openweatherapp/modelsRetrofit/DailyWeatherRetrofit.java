package com.example.lispa.openweatherapp.modelsRetrofit;

import java.util.ArrayList;

/**
 * Created by lispa on 13/11/2016.
 */

public class DailyWeatherRetrofit {
        public long dt;
        public TemperatureRetrofit temp;
        public ArrayList<WeatherRetrofit> weather;

        public long getDt() {
                return dt;
        }

        public TemperatureRetrofit getTemp() {
                return temp;
        }

        public ArrayList<WeatherRetrofit> getWeather() {
                return weather;
        }
}
