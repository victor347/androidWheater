package com.vicsoft.wheater.wheater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_UNITS = "units";

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mRadioGroup = (RadioGroup) findViewById(R.id.rGUnits);

        mRadioGroup.check(getIntent().getIntExtra(EXTRA_UNITS, R.id.rBCelcius));

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptSettins();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelSettings();
            }
        });
    }

    public void acceptSettins(){

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_UNITS , mRadioGroup.getCheckedRadioButtonId());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void cancelSettings(){

        setResult(RESULT_CANCELED);
        finish();
    }
}
