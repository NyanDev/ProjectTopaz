package com.nyandev.projecttopaz.di.component;

import com.nyandev.projecttopaz.di.module.AppModule;
import com.nyandev.projecttopaz.di.module.NetModule;
import com.nyandev.projecttopaz.ui.settings.SettingsFragment;
import com.nyandev.projecttopaz.ui.settings.SettingsFragmentPresenter;
import com.nyandev.projecttopaz.ui.weathercard.WeatherFragment;
import com.nyandev.projecttopaz.ui.weathercard.WeatherFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuan- on 03/03/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(SettingsFragment settingsFragment);
    void inject(SettingsFragmentPresenter settingsFragmentPresenter);
    void inject(WeatherFragment weatherFragment);
    void inject(WeatherFragmentPresenter weatherFragmentPresenter);
}
