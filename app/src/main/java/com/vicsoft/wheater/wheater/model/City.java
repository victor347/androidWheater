package com.vicsoft.wheater.wheater.model;

import java.io.Serializable;

/**
 * Created by victo on 9/02/2017.//
 */

public class City implements Serializable{

    private String mName;
    private Forecast mForecast;

    public City(String name, Forecast forecast) {
        mName = name;
        mForecast = forecast;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }
}
