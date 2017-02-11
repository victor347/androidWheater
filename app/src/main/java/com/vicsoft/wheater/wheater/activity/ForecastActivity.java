package com.vicsoft.wheater.wheater.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.CityListFragment;
import com.vicsoft.wheater.wheater.fragment.CityPagerFragment;
import com.vicsoft.wheater.wheater.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.OnCitySelectedListener {

    public static final String TAG = ForecastActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int widht = metrics.widthPixels;
        int height = metrics.heightPixels;
        int dpWidth = (int) (widht / metrics.density);
        int dpHeight = (int) (height / metrics.density);
        String model = Build.MODEL;
        int dpi = metrics.densityDpi;

        Log.v(TAG, "widht: " + widht + ", height: " + height + ", dpWidth: " + dpWidth + ", dpHeight: " + dpHeight + ", model: " + model + ", dpi: " + dpi);

        setContentView(R.layout.activity_forecast);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        //Cargamos el fragment en la actividad
        FragmentManager fm = getFragmentManager();

        if (findViewById(R.id.fragment_city_pager) != null) {

            if (fm.findFragmentById(R.id.fragment_city_pager) == null) {

                fm.beginTransaction().add(R.id.fragment_city_pager, new CityPagerFragment()).commit();
            }
        }
        if (findViewById(R.id.fragment_city_list) != null) {

            if (fm.findFragmentById(R.id.fragment_city_list) == null) {

                fm.beginTransaction().add(R.id.fragment_city_list, new CityListFragment()).commit();
            }
        }

   /*     //Comprobamos si el fragment ya esta en nuestra jerarquia
        if(fm.findFragmentById(R.id.forecast_fragment)==null){


            fm.beginTransaction().add(R.id.forecast_fragment, new CityListFragment()).commit();
        }*/
    }

    @Override
    public void onCitySelected(City city, int position) {

        FragmentManager fm = getFragmentManager();

        CityPagerFragment cityPagerFragment = (CityPagerFragment) fm.findFragmentById(R.id.fragment_city_pager);

        if (cityPagerFragment != null) {

            cityPagerFragment.showCity(position);
        } else {
            Intent intent = new Intent(this, CityPagerActivity.class);
            intent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX, position);
            startActivity(intent);
        }

    }
}


