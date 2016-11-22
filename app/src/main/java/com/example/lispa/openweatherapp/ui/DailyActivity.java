package com.example.lispa.openweatherapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lispa.openweatherapp.R;
import com.example.lispa.openweatherapp.R2;
import com.example.lispa.openweatherapp.adapters.GridAdapter;
import com.example.lispa.openweatherapp.models.DailyWeather;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyActivity extends AppCompatActivity {


    @BindView(R2.id.locationLabel)
    public TextView mLocationLabel;
    @BindView(R2.id.listDaily)
    public GridView mGridView;

    @BindView(R2.id.empty)
    public TextView mEmpty;

    private ArrayList<DailyWeather> mDailyWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        ButterKnife.bind(this);

        String location = this.getIntent().getStringExtra(MainActivity.CITY_NAME);
        mDailyWeather = this.getIntent().getParcelableArrayListExtra(MainActivity.DAILY_WEATHER);

        if(location != null){
            mLocationLabel.setText(location);
        }

        GridAdapter adapter = new GridAdapter(mDailyWeather ,this);
        mGridView.setAdapter(adapter);
        mGridView.setEmptyView(mEmpty);
    }
}
