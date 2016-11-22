package com.example.lispa.openweatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lispa.openweatherapp.R;
import com.example.lispa.openweatherapp.models.DailyWeather;

import java.util.ArrayList;

/**
 * Created by lispa on 18/11/2016.
 */

public class GridAdapter extends BaseAdapter {

    private ArrayList<DailyWeather> mDailyWeather;
    private Context mContext;

    public GridAdapter(ArrayList<DailyWeather> mDailyWeather, Context context) {
        this.mDailyWeather = mDailyWeather;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDailyWeather.size();
    }

    @Override
    public Object getItem(int position) {
        return mDailyWeather.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_card_list_item,null);
            holder = new ViewHolder();

            holder.temp = (TextView) convertView.findViewById(R.id.temp);
            holder.maxTemp = (TextView) convertView.findViewById(R.id.maxTemp);
            holder.minTemp = (TextView) convertView.findViewById(R.id.minTemp);
            holder.weekDay = (TextView) convertView.findViewById(R.id.weekDay);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        DailyWeather day = mDailyWeather.get(position);

        holder.temp.setText(day.getTemp() + "");
        holder.minTemp.setText(day.getTempMin() + "");
        holder.maxTemp.setText(day.getTempMax() + "");

        if(position == 0){
            holder.weekDay.setText("Today");
        }else {
            holder.weekDay.setText(day.getDayOfTheWeek("UTC"));
        }

        return convertView;
    }

    public static class ViewHolder{
        TextView maxTemp;
        TextView minTemp;
        TextView temp;
        TextView weekDay;
    }
}
