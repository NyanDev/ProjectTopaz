package com.nyandev.projecttopaz.ui.weathercard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.ui.settings.SettingsActivity;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, WeatherActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        PreferenceManager.setDefaultValues(this, R.xml.prefs_main, false);



        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //fragments
        WeatherCardFragmentOld weatherCardFragmentOld = new WeatherCardFragmentOld();
        WeatherFragment weatherFragment = new WeatherFragment();
        ft.add(R.id.fragment_weather, weatherFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                displayToast("menu_settings");
                startActivity(SettingsActivity.getStartIntent(this));
                return true;
            case android.R.id.home:
                displayToast("action_home");
                return true;
            case R.id.action_update:
                displayToast("action_update");
                return true;
            default:
                // do nothing

        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
