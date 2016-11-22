package com.example.lispa.openweatherapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.lispa.openweatherapp.utils.FormatterUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lispa on 18/11/2016.
 */
public class DailyWeather implements Parcelable {
    private long mDateLong;
    private double mTempMin;
    private double mTempMax;
    private double mTemp;
    private String mDesciption;

    public DailyWeather(long mDateLong, double mTempMin, double mTempMax,double mTemp, String mDesciption) {
        this.mDateLong = mDateLong;
        this.mTempMin = mTempMin;
        this.mTempMax = mTempMax;
        this.mTemp = mTemp;
        this.mDesciption = mDesciption;
    }

    public String getDayOfTheWeek(String timezone) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Date dateTime = new Date(mDateLong * 1000);
        return formatter.format(dateTime);
    }

    public int getTemp() {
        return FormatterUtil.kevinFormatter(mTemp);
    }

    public int getTempMin() {
        return FormatterUtil.kevinFormatter(mTempMin);
    }

    public int getTempMax() {
        return FormatterUtil.kevinFormatter(mTempMax);
    }

    public String getDesciption() {
        return mDesciption;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mDateLong);
        dest.writeDouble(this.mTempMin);
        dest.writeDouble(this.mTempMax);
        dest.writeDouble(this.mTemp);
        dest.writeString(this.mDesciption);
    }

    protected DailyWeather(Parcel in) {
        this.mDateLong = in.readLong();
        this.mTempMin = in.readDouble();
        this.mTempMax = in.readDouble();
        this.mTemp = in.readDouble();
        this.mDesciption = in.readString();
    }

    public static final Creator<DailyWeather> CREATOR = new Creator<DailyWeather>() {
        @Override
        public DailyWeather createFromParcel(Parcel source) {
            return new DailyWeather(source);
        }

        @Override
        public DailyWeather[] newArray(int size) {
            return new DailyWeather[size];
        }
    };

    public void setDateLong(long mDateLong) {
        this.mDateLong = mDateLong;
    }

    public void setTempMin(double mTempMin) {
        this.mTempMin = mTempMin;
    }

    public void setTempMax(double mTempMax) {
        this.mTempMax = mTempMax;
    }

    public void setTemp(double mTemp) {
        this.mTemp = mTemp;
    }

    public void setDesciption(String desciption) {
        mDesciption = desciption;
    }
}
