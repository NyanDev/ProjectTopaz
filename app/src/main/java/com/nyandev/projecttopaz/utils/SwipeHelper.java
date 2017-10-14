package com.nyandev.projecttopaz.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.nyandev.projecttopaz.adapters.WeatherCardRecyclerAdapter;

/**
 * Created by xuan- on 26/08/2017.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback{

    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;

    public SwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeHelper(WeatherCardRecyclerAdapter weatherCardRecyclerAdapter){
        super(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT);
        this.weatherCardRecyclerAdapter = weatherCardRecyclerAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        weatherCardRecyclerAdapter.removeWeather(viewHolder.getAdapterPosition());
    }
}
