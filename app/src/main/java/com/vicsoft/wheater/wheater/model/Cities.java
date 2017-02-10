package com.vicsoft.wheater.wheater.model;

import com.vicsoft.wheater.wheater.R;

import java.util.LinkedList;

/**
 * Created by victo on 9/02/2017.
 */

public class Cities {

    private LinkedList<City> mCities;

    public Cities(){

        mCities = new LinkedList<>();
        mCities.add(new City("Madrid", new Forecast(10, 20, 30, "Tormenta Electrica", R.drawable.ico_11)));
        mCities.add(new City("Bogotá", new Forecast(25, 32, 18, "Muy soleado", R.drawable.ico_01)));
        mCities.add(new City("San Francisco", new Forecast(35, 12, 51, "Tornado", R.drawable.ico_50)));
    }

    public LinkedList<City> getCities() {
        return mCities;
    }

    public int getCount(){

        return mCities.size();
    }

    public City getCity(int position){

        return mCities.get(position);
    }
}
