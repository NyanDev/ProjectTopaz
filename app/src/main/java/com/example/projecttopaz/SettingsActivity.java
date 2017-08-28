package com.example.projecttopaz;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.projecttopaz.events.AllPurposeEvent;

import butterknife.BindArray;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 20/08/2017.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @BindArray(R.array.temperatureUnits) String[] temperatureUnits;
    @BindArray(R.array.temperatureUnitsValues) String[] temperatureUnitsValues;
    @BindArray(R.array.pressureUnits) String[] pressureUnits;
    @BindArray(R.array.pressureUnitsValues) String[] pressureUnitsValues;
    @BindArray(R.array.speedUnits) String[] speedUnits;
    @BindArray(R.array.speedUnitsValues) String[] speedUnitsValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        ButterKnife.bind(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("apiKey")){
            // TODO 2: Test if apikey is valid
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(new AllPurposeEvent("preferencesActivityEvent"));
    }

    @SuppressLint("ValidFragment")
    private class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_main);
        }
    }
}
