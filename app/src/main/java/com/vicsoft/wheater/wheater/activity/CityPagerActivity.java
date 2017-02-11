package com.vicsoft.wheater.wheater.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.CityListFragment;
import com.vicsoft.wheater.wheater.fragment.CityPagerFragment;

public class CityPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();

        if(fm.findFragmentById(R.id.citi_pager_fragment)==null){

            fm.beginTransaction().add(R.id.citi_pager_fragment, new CityPagerFragment()).commit();
        }
    }
}
