package com.vicsoft.wheater.wheater.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.CityListFragment;
import com.vicsoft.wheater.wheater.fragment.CityPagerFragment;
import com.vicsoft.wheater.wheater.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.OnCitySelectedListener{

    public static final String TAG = ForecastActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        //Cargamos el fragment en la actividad
        FragmentManager fm = getFragmentManager();

        //Comprobamos si el fragment ya esta en nuestra jerarquia
        if(fm.findFragmentById(R.id.forecast_fragment)==null){

            CityListFragment cityListFragment = new CityListFragment();
            cityListFragment.setOnCitySelectedListener(this);
            fm.beginTransaction().add(R.id.forecast_fragment, cityListFragment).commit();
        }
    }

    @Override
    public void onCitySelected(City city, int position) {



    }
}


