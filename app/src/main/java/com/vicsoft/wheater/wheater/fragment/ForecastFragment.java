package com.vicsoft.wheater.wheater.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.ViewSwitcher;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.activity.SettingsActivity;
import com.vicsoft.wheater.wheater.model.City;
import com.vicsoft.wheater.wheater.model.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ForecastFragment extends Fragment {

    public static final String TAG = ForecastFragment.class.getCanonicalName();
    public static final String PREFERENCE_SHOW_CELSIUS = "showCelsius";
    private static final int REQUEST_UNITS = 235;
    private static final String ARG_CITY = "city";

    private TextView mCityName;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;
    private boolean mShowCelsius;
    private City mCity;
    private ViewSwitcher mViewSwitcher;

    public static ForecastFragment newInstace(City city) {

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

        if (getArguments() != null) {

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
        mViewSwitcher = (ViewSwitcher) root.findViewById(R.id.view_switcher);
        mViewSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
        mViewSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);
        downloadForecast();

        return root;
    }

    private void downloadForecast(){

        AsyncTask<City, Integer, Forecast> wheaterDownloader = new AsyncTask<City, Integer, Forecast>() {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                mViewSwitcher.setDisplayedChild(0);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Forecast forecast) {
                super.onPostExecute(forecast);

                if(forecast != null) {
                    mCity.setForecast(forecast);
                    setForecast(forecast);
                    mViewSwitcher.setDisplayedChild(1);
                }
                else{

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(R.string.error);
                    alertDialog.setMessage(R.string.error_descargando_info);
                    alertDialog.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            downloadForecast();
                        }
                    });
                    alertDialog.show();

                }

            }

            @Override
            protected Forecast doInBackground(City... cities) {

                URL url = null;
                InputStream input = null;

                try {
                    url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&appid=3688455f211758c19da1a2906e9434d2&units=metric&lang=es", cities[0].getName()));

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(3000);
                    con.connect();
                    int responseLenght = con.getContentLength();
                    byte data[] = new byte[1024];
                    long currentBytes = 0;
                    int downloadBytes;
                    input = con.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    while((downloadBytes = input.read(data)) != -1){
                        sb.append(new String(data, 0, downloadBytes));
                    }

                    JSONObject jsonRoot = new JSONObject(sb.toString());
                    JSONArray days = jsonRoot.getJSONArray("list");
                    JSONObject today = days.getJSONObject(0);
                    float max = (float) today.getJSONObject("temp").getDouble("max");
                    float min = (float) today.getJSONObject("temp").getDouble("min");
                    float humidity = (float) today.getDouble("humidity");
                    String description = today.getJSONArray("weather").getJSONObject(0).getString("description");
                    String icon = today.getJSONArray("weather").getJSONObject(0).getString("icon");

                    int iconInt = Integer.parseInt(icon.substring(0, icon.length()-1));
                    int iconResource;
                    switch (iconInt){
                        case 1:
                            iconResource = R.drawable.ico_01;
                            break;
                        case 2:
                            iconResource = R.drawable.ico_02;
                            break;
                        case 3:
                            iconResource = R.drawable.ico_03;
                            break;
                        case 4:
                            iconResource = R.drawable.ico_04;
                            break;
                        case 9:
                            iconResource = R.drawable.ico_09;
                            break;
                        case 10:
                            iconResource = R.drawable.ico_10;
                            break;
                        case 11:
                            iconResource = R.drawable.ico_11;
                            break;
                        case 13:
                            iconResource = R.drawable.ico_13;
                            break;
                        case 50:
                            iconResource = R.drawable.ico_50;
                            break;
                        default:
                            iconResource = R.drawable.ico_01;
                            break;
                    }

                    return new Forecast(max, min, humidity, description, iconResource);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        wheaterDownloader.execute(mCity);


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

        mCityName.setText(mCity.getName());
        mMaxTemp.setText(String.format(getString(R.string.tempMaxFormat), maxTemp, units));
        mMinTemp.setText(String.format(getString(R.string.tempMinFormat), minTemp, units));
        mHumidity.setText(String.format(getString(R.string.humidityFormat), forecast.getHumidity()));
        mDescription.setText(String.valueOf(forecast.getDescription()));
        mForecastImage.setImageResource(forecast.getIcon());

        mCity.setForecast(forecast);
        ;
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
                    if (getView() != null)
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
