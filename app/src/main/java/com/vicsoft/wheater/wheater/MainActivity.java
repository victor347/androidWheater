package com.vicsoft.wheater.wheater;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Pasando por onCreate");

        Button changeSystemButton = (Button) findViewById(R.id.changeSystem);
        Button changeSystemButton2 = (Button) findViewById(R.id.changeSystem2);

        MainClickHandler mainClickHandler = new MainClickHandler(this);

        changeSystemButton.setOnClickListener(mainClickHandler);
        changeSystemButton2.setOnClickListener(mainClickHandler);
    }

}


class MainClickHandler implements View.OnClickListener {

    private Activity context;

    public MainClickHandler(Activity context) {

        this.context = context;
    }

    @Override
    public void onClick(View v) {

        Log.v(MainActivity.TAG, "Pasando por onCreate");

        ImageView systemImage = (ImageView) context.findViewById(R.id.systemImage);

        if (v.getId() == R.id.changeSystem) {
            systemImage.setImageResource(R.drawable.offline_weather2);
        } else if (v.getId() == R.id.changeSystem2) {
            systemImage.setImageResource(R.drawable.offline_weather);
        }

    }
}