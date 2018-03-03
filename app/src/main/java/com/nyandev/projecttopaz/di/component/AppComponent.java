package com.nyandev.projecttopaz.di.component;

import com.nyandev.projecttopaz.App;
import com.nyandev.projecttopaz.MainActivity;
import com.nyandev.projecttopaz.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xuan- on 03/03/2018.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(App app);
}
