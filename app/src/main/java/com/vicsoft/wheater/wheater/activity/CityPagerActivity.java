package com.vicsoft.wheater.wheater.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.CityListFragment;
import com.vicsoft.wheater.wheater.fragment.CityPagerFragment;

public class CityPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_INDEX = "EXTRA_CITY_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getFragmentManager();

        if(fm.findFragmentById(R.id.citi_pager_fragment)==null){

            int cityIndex = getIntent().getIntExtra(EXTRA_CITY_INDEX, 0);

            CityPagerFragment cityPagerFragment = CityPagerFragment.newinstance(cityIndex);

            fm.beginTransaction().add(R.id.citi_pager_fragment, cityPagerFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superClase =super.onOptionsItemSelected(item);

        if(item.getItemId() == android.R.id.home){

            finish();
            return true;
        }

        return superClase;
    }
}
