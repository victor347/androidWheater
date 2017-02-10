package com.vicsoft.wheater.wheater.adapter;



import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import com.vicsoft.wheater.wheater.fragment.ForecastFragment;
import com.vicsoft.wheater.wheater.model.Cities;

/**
 * Created by victo on 9/02/2017.
 */

public class CityPagerAdapter extends FragmentPagerAdapter {

    private Cities mCities;

    public CityPagerAdapter(FragmentManager fm) {
        super(fm);
        mCities = new Cities();
    }

    @Override
    public Fragment getItem(int position) {

        ForecastFragment fragment = ForecastFragment.newInstace(mCities.getCities().get(position));
        return fragment;
    }

    @Override
    public int getCount() {

        return mCities.getCount();
    }
}
