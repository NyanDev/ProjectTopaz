package com.nyandev.projecttopaz;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyandev.projecttopaz.ui.weathercard.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    public android.support.v4.widget.SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.prefs_main, false);
        startActivity(WeatherActivity.getStartIntent(this));
    }
}