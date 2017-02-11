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
        mCities.add(new City("Madrid"));
        mCities.add(new City("Barcelona"));
        mCities.add(new City("Bogota"));
        mCities.add(new City("Medellin"));
        mCities.add(new City("San Francisco"));
        mCities.add(new City("New York"));

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
