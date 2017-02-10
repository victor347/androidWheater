package com.vicsoft.wheater.wheater.activity;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.CityPagerFragment;

public class ForecastActivity extends AppCompatActivity {

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

            fm.beginTransaction().add(R.id.forecast_fragment, new CityPagerFragment()).commit();
        }
    }
}


