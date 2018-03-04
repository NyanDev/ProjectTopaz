package com.nyandev.projecttopaz;

import android.app.Application;

import com.nyandev.projecttopaz.data.models.database.WeatherDatabase;
import com.nyandev.projecttopaz.di.component.DaggerNetComponent;
import com.nyandev.projecttopaz.di.component.NetComponent;
import com.nyandev.projecttopaz.di.module.AppModule;
import com.nyandev.projecttopaz.di.module.NetModule;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by xuan- on 03/03/2018.
 */

public class App extends Application {
    private NetComponent mNetComponent;
    final static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(WEATHER_BASE_URL))
                .build();

        // init DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.E);
        //FlowManager.getDatabase(WeatherDatabase.class).reset(this);

    }

    public NetComponent getNetComponent(){
        return mNetComponent;
    }
}
