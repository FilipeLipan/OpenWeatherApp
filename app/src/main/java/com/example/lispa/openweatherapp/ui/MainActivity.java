package com.example.lispa.openweatherapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.lispa.openweatherapp.R;
import com.example.lispa.openweatherapp.R2;
import com.example.lispa.openweatherapp.api.OpenWeatherApi;
import com.example.lispa.openweatherapp.api.OpenWeatherService;
import com.example.lispa.openweatherapp.models.DailyWeather;
import com.example.lispa.openweatherapp.models.OpenWeather;
import com.example.lispa.openweatherapp.modelsRetrofit.DailyWeatherRetrofit;
import com.example.lispa.openweatherapp.modelsRetrofit.OpenWeatherRetrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    public static final String DAILY_WEATHER = "daily_weather";
    public static final String CITY_NAME = "city_name";
    private OpenWeatherRetrofit mOpenWeatherRetrofit;
    private OpenWeather mOpenWeather;


    @BindView(R2.id.floating_search_view)
    FloatingSearchView mFloatingSearchView;
    @BindView(R2.id.dailyButton)
    Button mDailyButton;
    @BindView(R2.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R2.id.temperatureLabel)
    TextView mTemperatureLabel;
    @BindView(R2.id.maxTempValue)
    TextView mMaxTemperature;
    @BindView(R2.id.minTempValue)
    TextView mMinTemperature;
    @BindView(R2.id.summaryLabel)
    TextView mSummaryLabel;
    @BindView(R2.id.cityLabel)
    TextView mCityLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOpenWeather = new OpenWeather();

        ButterKnife.bind(this);
        toggleRefresh();

        mFloatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (currentQuery.equals("")) {
                    Toast.makeText(getBaseContext(), "Choose a city", Toast.LENGTH_SHORT).show();
                } else {
                    if (isConnectionAvailable()) {
                        //Http raw method
                        //getForecastHttp(currentQuery);

                        //retrofit method
                        getForecastRetrofit(currentQuery);
                    } else {
                        Toast.makeText(getBaseContext(), "Connection unavailable!, Please check your connection!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void getForecastHttp(String city) {
        GetForecast getForecast = new GetForecast();
        getForecast.execute(city);
    }


    public class GetForecast extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            toggleRefresh();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String resposta = null;

                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + params[0].toString().replaceAll(" ", "%20") + "&mode=json&cnt=" + OpenWeatherApi.API_KEY);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    resposta = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String linha;
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }
                if (buffer.length() == 0) {
                    resposta = null;
                }
                resposta = buffer.toString();

                parseJsonObjectIntoOpenWeatherObject(new JSONObject(resposta));

            } catch (Exception io) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setStuff();
            toggleRefresh();
        }
    }

    private void parseJsonObjectIntoOpenWeatherObject(JSONObject openWeatherJson) {
        try {
            JSONObject city = openWeatherJson.getJSONObject("city");
            JSONArray list = openWeatherJson.getJSONArray("list");

            mOpenWeather = new OpenWeather(city.getString("name"));

            for (int n = 0; n < list.length(); n++) {

                JSONObject day = list.getJSONObject(n);
                Long dateLong = day.getLong("dt");
                JSONObject temperature = day.getJSONObject("temp");
                double temp = temperature.getDouble("day");
                double temMin = temperature.getInt("min");
                double tempMax = temperature.getDouble("max");
                JSONArray weathers = day.getJSONArray("weather");
                JSONObject weather = weathers.getJSONObject(0);
                String desciption = weather.getString("description");

                DailyWeather dailyWeather = new DailyWeather(dateLong, temMin, tempMax, temp, desciption);
                mOpenWeather.getDailyWeather().add(dailyWeather);
            }
        } catch (JSONException e) {
        }
    }

    //get forecast from openWeather with retrofit
    private void getForecastRetrofit(String cidade) {
        toggleRefresh();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(OpenWeatherService.OPEN_WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherService service = retrofit.create(OpenWeatherService.class);
        Call<OpenWeatherRetrofit> makeWeatherRequest = service.getWeatherByCity(cidade, "json", "7", OpenWeatherApi.API_KEY);

        makeWeatherRequest.enqueue(new Callback<OpenWeatherRetrofit>() {
            @Override
            public void onResponse(Call<OpenWeatherRetrofit> call, Response<OpenWeatherRetrofit> response) {
                if (response.isSuccessful()) {
                    //receive body into the retrofit model
                    mOpenWeatherRetrofit = response.body();
                    transferModels();
                } else {
                    Toast.makeText(getBaseContext(), "Couldn't find this city!", Toast.LENGTH_SHORT).show();
                }
                toggleRefresh();
            }

            @Override
            public void onFailure(Call<OpenWeatherRetrofit> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Couldn't connect to server!", Toast.LENGTH_SHORT).show();
                toggleRefresh();
            }
        });

    }

    //get the models from the modelsRetrofit package and set the variable in the models package
    private void transferModels() {
        //transfer to the models
        mOpenWeather = new OpenWeather(mOpenWeatherRetrofit.getCity().getName());
        for (DailyWeatherRetrofit daily : mOpenWeatherRetrofit.getDaily()) {
            DailyWeather dailyWeather = new DailyWeather(daily.getDt(), daily.getTemp().getTempMin(),
                    daily.getTemp().getTempMax(), daily.getTemp().getTemp(), daily.getWeather().get(0).getMain());
            mOpenWeather.getDailyWeather().add(dailyWeather);
        }

        if (mOpenWeatherRetrofit.getDaily().size() > 0) {
            if (mOpenWeatherRetrofit.getDaily().get(0).getWeather().size() > 0) {
                setStuff();
            }
        }
    }

    //put stuff together
    private void setStuff() {
        mCityLabel.setText(mOpenWeather.getCity());
        mSummaryLabel.setText(mOpenWeather.getDailyWeather().get(0).getDesciption());
        mMaxTemperature.setText(mOpenWeather.getDailyWeather().get(0).getTempMax() + "");
        mMinTemperature.setText(mOpenWeather.getDailyWeather().get(0).getTempMin() + "");
        mTemperatureLabel.setText(mOpenWeather.getDailyWeather().get(0).getTemp() + "");
    }

    //verify connection
    private boolean isConnectionAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }


    //toggle the loading progress bar
    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.dailyButton)
    public void dailyStart() {
        Intent intent = new Intent(this, DailyActivity.class);
        intent.putExtra(CITY_NAME, mOpenWeather.getCity());
        intent.putParcelableArrayListExtra(DAILY_WEATHER, mOpenWeather.getDailyWeather());
        startActivity(intent);
    }

}
