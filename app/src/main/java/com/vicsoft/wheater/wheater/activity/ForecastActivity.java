package com.vicsoft.wheater.wheater.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vicsoft.wheater.wheater.R;

public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = ForecastActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);
    }
}


