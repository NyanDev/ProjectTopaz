package com.nyandev.projecttopaz.ui.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyandev.projecttopaz.R;

import butterknife.BindArray;
import butterknife.BindString;

/**
 * Created by xuan- on 01/03/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // not needed because fragment
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs_main, rootKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void showAbout(){
        // tell presenter to show about
    }

}
