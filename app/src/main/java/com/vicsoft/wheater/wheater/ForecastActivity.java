package com.vicsoft.wheater.wheater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = ForecastActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast);
    }
}


