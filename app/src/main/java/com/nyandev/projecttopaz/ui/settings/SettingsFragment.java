package com.nyandev.projecttopaz.ui.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nyandev.projecttopaz.R;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.OnClick;

/**
 * Created by xuan- on 01/03/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    @BindString(R.string.settings_about)
    String settingsAbout;


    SettingsFragmentPresenter mPresenter;

    public SettingsFragment() {
        // not needed because fragment
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs_main, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new SettingsFragmentPresenter(view.getContext());
        //showAbout();

    }

    public void showAbout(){
        mPresenter.showAbout();
    }

}
