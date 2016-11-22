package com.example.lispa.openweatherapp.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lispa.openweatherapp.modelsRetrofit.OpenWeatherRetrofit;

/**
 * Created by lispa on 13/11/2016.
 */

public class GetForecast extends AsyncTask<Void,Void,OpenWeatherRetrofit> {

    private Context mContext;
    private OpenWeatherRetrofit mOpenWeather;

    public GetForecast(Context context){
        mContext = context;
    }


    @Override
    protected OpenWeatherRetrofit doInBackground(Void... params) {
        return new OpenWeatherRetrofit();
    }

}
