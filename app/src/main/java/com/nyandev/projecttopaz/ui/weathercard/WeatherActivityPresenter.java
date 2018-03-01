package com.nyandev.projecttopaz.ui.weathercard;

import android.support.v7.widget.SearchView;

import com.nyandev.projecttopaz.events.AllPurposeEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherActivityPresenter {

    public WeatherActivityPresenter(){

    }

    public void onQuerySubmit(String query) {
        EventBus.getDefault().postSticky(new AllPurposeEvent(query));
    }
}
