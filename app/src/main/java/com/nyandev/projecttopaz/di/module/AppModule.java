package com.nyandev.projecttopaz.di.module;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xuan- on 03/03/2018.
 */

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }
}
