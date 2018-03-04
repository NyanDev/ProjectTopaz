package com.nyandev.projecttopaz.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.nyandev.projecttopaz.data.adapters.WeatherCardAdapterDB;
import com.nyandev.projecttopaz.data.adapters.WeatherCardRecyclerAdapter;

/**
 * Created by xuan- on 26/08/2017.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback{

    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;
    WeatherCardAdapterDB weatherCardAdapterDB;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(WeatherCardRecyclerAdapter weatherCardRecyclerAdapter){
        super(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT);
        this.weatherCardRecyclerAdapter = weatherCardRecyclerAdapter;
    }

    public SwipeHelper(WeatherCardAdapterDB weatherCardAdapterDB){
        super(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT);
        this.weatherCardAdapterDB = weatherCardAdapterDB;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        weatherCardAdapterDB.removeWeather(viewHolder.getAdapterPosition());
    }
}
