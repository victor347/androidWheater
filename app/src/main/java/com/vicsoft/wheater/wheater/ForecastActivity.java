package com.vicsoft.wheater.wheater;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = ForecastActivity.class.getCanonicalName();
    private static final int REQUEST_UNITS = 235;
    public static final String PREFERENCE_SHOW_CELSIUS = "showCelsius";

    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;
    private boolean mShowCelsius;
    private Forecast mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Log.v(TAG, "Pasando por onCreate");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        mShowCelsius = pref.getBoolean(PREFERENCE_SHOW_CELSIUS, true);

        Forecast forecast = new Forecast(30.2f, 15.6f, 25, "Cielo despejado", R.drawable.ico_02);

        mMaxTemp = (TextView) findViewById(R.id.tempMax);
        mMinTemp = (TextView) findViewById(R.id.tempMin);
        mHumidity = (TextView) findViewById(R.id.humedity);
        mDescription = (TextView) findViewById(R.id.description);
        mForecastImage = (ImageView) findViewById(R.id.imageForecast);

        setForecast(forecast);
    }

    private void setForecast(Forecast forecast) {

        float maxTemp = forecast.getMaxTemp();
        float minTemp = forecast.getMinTemp();
        String units = "°C";

        if(!mShowCelsius){

            maxTemp = toFahrenheit(maxTemp);
            minTemp = toFahrenheit(minTemp);
            units = "°F" ;
        }

        mMaxTemp.setText(String.format(getString(R.string.tempMaxFormat), maxTemp, units));
        mMinTemp.setText(String.format(getString(R.string.tempMinFormat), minTemp, units));
        mHumidity.setText(String.format(getString(R.string.humidityFormat), forecast.getHumidity()));
        mDescription.setText(String.valueOf(forecast.getDescription()));
        mForecastImage.setImageResource(forecast.getIcon());

        mForecast = forecast;
    }

    private float toFahrenheit(float temp) {

        return (temp * 1.8f)+32;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.show_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);

            if(mShowCelsius){
                intent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.rBCelcius);
            }
            else{
                intent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.rBFarenheit);
            }

            //startActivity(intent);
            startActivityForResult(intent, REQUEST_UNITS);
            return true;
        }

        return superReturn;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNITS) {
            if (resultCode == RESULT_OK) {

                int optionSelected = data.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.rBFarenheit);

                if (optionSelected == R.id.rBFarenheit) {

                    mShowCelsius =false;

                } else if (optionSelected == R.id.rBCelcius) {

                    mShowCelsius =true;
                }

                setForecast(mForecast);

                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius).apply();

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}


