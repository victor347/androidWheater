package com.vicsoft.wheater.wheater.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.fragment.ForecastFragment;
import com.vicsoft.wheater.wheater.model.Forecast;

import java.util.LinkedList;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastViewHolder> {

    private LinkedList<Forecast> mForecast;
    private Context mContext;

    public ForecastRecyclerViewAdapter(LinkedList<Forecast> forecast, Context context) {
        super();
        mForecast = forecast;
        mContext = context;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_forecast, parent, false);

        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

        holder.bindForecast(mForecast.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return mForecast.size();
    }

    protected class ForecastViewHolder extends RecyclerView.ViewHolder {

        private TextView mMaxTemp;
        private TextView mMinTemp;
        private TextView mHumidity;
        private TextView mDescription;
        private ImageView mForecastImage;


        public ForecastViewHolder(View itemView) {
            super(itemView);


            mMaxTemp = (TextView) itemView.findViewById(R.id.tempMax);
            mMinTemp = (TextView) itemView.findViewById(R.id.tempMin);
            mHumidity = (TextView) itemView.findViewById(R.id.humedity);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mForecastImage = (ImageView) itemView.findViewById(R.id.imageForecast);
        }

        public void bindForecast(Forecast forecast, Context context) {

            float maxTemp = forecast.getMaxTemp();
            float minTemp = forecast.getMinTemp();
            String units = "°C";

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            boolean mShowCelsius = pref.getBoolean(ForecastFragment.PREFERENCE_SHOW_CELSIUS, true);

            if (!mShowCelsius) {

                maxTemp = toFahrenheit(maxTemp);
                minTemp = toFahrenheit(minTemp);
                units = "°F";
            }


            mMaxTemp.setText(String.format(context.getString(R.string.tempMaxFormat), maxTemp, units));
            mMinTemp.setText(String.format(context.getString(R.string.tempMinFormat), minTemp, units));
            mHumidity.setText(String.format(context.getString(R.string.humidityFormat), forecast.getHumidity()));
            mDescription.setText(String.valueOf(forecast.getDescription()));
            mForecastImage.setImageResource(forecast.getIcon());


        }

        private float toFahrenheit(float temp) {

            return (temp * 1.8f) + 32;
        }
    }


}
