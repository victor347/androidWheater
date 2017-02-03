package com.vicsoft.wheater.wheater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = ForecastActivity.class.getCanonicalName();

    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Log.v(TAG, "Pasando por onCreate");

        Forecast forecast =  new Forecast(30.2f, 15.6f, 25, "Cielo despejado", R.drawable.ico_02);

        mMaxTemp = (TextView) findViewById(R.id.tempMax);
        mMinTemp = (TextView) findViewById(R.id.tempMin);
        mHumidity = (TextView) findViewById(R.id.humedity);
        mDescription = (TextView) findViewById(R.id.description);
        mForecastImage = (ImageView) findViewById(R.id.imageForecast);

        setForecast(forecast);
    }

    private void setForecast(Forecast forecast) {
        mMaxTemp.setText(String.format(getString(R.string.tempMaxFormat),forecast.getMaxTemp()));
        mMinTemp.setText(String.format(getString(R.string.tempMinFormat),forecast.getMinTemp()));
        mHumidity.setText(String.format(getString(R.string.humidityFormat),forecast.getHumidity()));
        mDescription.setText(String.valueOf(forecast.getDescription()));
        mForecastImage.setImageResource(forecast.getIcon());
    }


}


