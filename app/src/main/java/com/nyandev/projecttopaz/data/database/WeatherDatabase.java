package com.nyandev.projecttopaz.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by xuan- on 03/03/2018.
 */

@Database(name = WeatherDatabase.NAME, version = WeatherDatabase.VERSION)
public class WeatherDatabase {
    public static final String NAME = "WeatherDatabase";
    public static final int VERSION = 1;
}
