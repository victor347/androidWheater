package com.vicsoft.wheater.wheater.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.activity.SettingsActivity;
import com.vicsoft.wheater.wheater.model.City;
import com.vicsoft.wheater.wheater.model.Forecast;

/**
 * Created by victor on 8/02/2017.
 * --
 */

public class ForecastFragment extends Fragment {

    public static final String TAG = ForecastFragment.class.getCanonicalName();
    private static final int REQUEST_UNITS = 235;
    public static final String PREFERENCE_SHOW_CELSIUS = "showCelsius";
    private static final String ARG_CITY = "city";

    private TextView mCityName;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;
    private boolean mShowCelsius;

    private City mCity;

    public static ForecastFragment newInstace (City city){

        ForecastFragment fragment = new ForecastFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CITY, city);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if(getArguments() != null){

            mCity = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        Log.v(TAG, "Pasando por onCreate");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mShowCelsius = pref.getBoolean(PREFERENCE_SHOW_CELSIUS, true);


        mCityName = (TextView) root.findViewById(R.id.city);
        mMaxTemp = (TextView) root.findViewById(R.id.tempMax);
        mMinTemp = (TextView) root.findViewById(R.id.tempMin);
        mHumidity = (TextView) root.findViewById(R.id.humedity);
        mDescription = (TextView) root.findViewById(R.id.description);
        mForecastImage = (ImageView) root.findViewById(R.id.imageForecast);

        setForecast(mCity.getForecast());
        mCityName.setText(mCity.getName());
        return root;
    }

    private void setForecast(Forecast forecast) {

        float maxTemp = forecast.getMaxTemp();
        float minTemp = forecast.getMinTemp();
        String units = "°C";

        if (!mShowCelsius) {

            maxTemp = toFahrenheit(maxTemp);
            minTemp = toFahrenheit(minTemp);
            units = "°F";
        }

        mMaxTemp.setText(String.format(getString(R.string.tempMaxFormat), maxTemp, units));
        mMinTemp.setText(String.format(getString(R.string.tempMinFormat), minTemp, units));
        mHumidity.setText(String.format(getString(R.string.humidityFormat), forecast.getHumidity()));
        mDescription.setText(String.valueOf(forecast.getDescription()));
        mForecastImage.setImageResource(forecast.getIcon());

        mCity.setForecast(forecast);;
    }

    private float toFahrenheit(float temp) {

        return (temp * 1.8f) + 32;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.show_settings) {

            Intent intent = new Intent(getActivity(), SettingsActivity.class);

            if (mShowCelsius) {
                intent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.rBCelcius);
            } else {
                intent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.rBFarenheit);
            }

            //startActivity(intent);
            startActivityForResult(intent, REQUEST_UNITS);
            return true;
        }

        return superReturn;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNITS) {

            final boolean oldShowCelcius = mShowCelsius;

            if (resultCode == Activity.RESULT_OK) {

                int optionSelected = data.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.rBFarenheit);

                if (optionSelected == R.id.rBFarenheit) {

                    mShowCelsius = false;

                } else if (optionSelected == R.id.rBCelcius) {

                    mShowCelsius = true;
                }

                setForecast(mCity.getForecast());

                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius).apply();

                //  Toast.makeText(this, R.string.actualizado, Toast.LENGTH_LONG).show();

                if (mShowCelsius != oldShowCelcius) {
                    if(getView()!=null)
                    Snackbar.make(getView(), R.string.actualizado, Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mShowCelsius = oldShowCelcius;
                                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius).apply();
                                    setForecast(mCity.getForecast());
                                }
                            }).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
