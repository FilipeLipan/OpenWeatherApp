package com.example.lispa.openweatherapp.utils;

/**
 * Created by lispa on 13/11/2016.
 */

public class FormatterUtil {
    public static int kevinFormatter(double number){
        int n = (int) (number-273.15);
        return n;
    }
}
