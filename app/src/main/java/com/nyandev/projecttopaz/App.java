package com.nyandev.projecttopaz;

import android.app.Application;

import com.nyandev.projecttopaz.di.component.DaggerNetComponent;
import com.nyandev.projecttopaz.di.component.NetComponent;
import com.nyandev.projecttopaz.di.module.AppModule;
import com.nyandev.projecttopaz.di.module.NetModule;

/**
 * Created by xuan- on 03/03/2018.
 */

public class App extends Application {
    private NetComponent mNetComponent;
    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    final static String API_KEY = "56f39e475af4af7888645b0a20f3ae03";

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(WEATHER_BASE_URL))
                .build();

    }

    public NetComponent getNetComponent(){
        return mNetComponent;
    }
}
