package com.vicsoft.wheater.wheater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Pasando por onCreate");

        Button changeSystemButton = (Button) findViewById(R.id.changeSystem);

        changeSystemButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Log.v(TAG, "Boton Pulsado");

        ImageView systemImage = (ImageView) findViewById(R.id.systemImage);

        systemImage.setImageResource(R.drawable.offline_weather2);

    }
}
