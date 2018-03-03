package com.nyandev.projecttopaz;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyandev.projecttopaz.di.component.DaggerNetComponent;
import com.nyandev.projecttopaz.di.component.NetComponent;
import com.nyandev.projecttopaz.di.module.NetModule;
import com.nyandev.projecttopaz.ui.weathercard.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    public android.support.v4.widget.SimpleCursorAdapter mAdapter;
    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private NetComponent mNetComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.prefs_main, false);

        startActivity(WeatherActivity.getStartIntent(this));
    }
}