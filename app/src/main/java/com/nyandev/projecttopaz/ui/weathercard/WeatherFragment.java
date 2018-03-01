package com.nyandev.projecttopaz.ui.weathercard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.adapters.WeatherCardRecyclerAdapter;
import com.nyandev.projecttopaz.utils.SwipeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherFragment extends Fragment {

    @BindView(R.id.rv_weatherCard)
    RecyclerView recyclerView;

    private WeatherPresenter mPresenter;
    LinearLayoutManager linearLayoutManager;
    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView(view);
    }


    public void initView(View view){
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(view.getContext());
        mPresenter = new WeatherPresenter(weatherCardRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mPresenter.weatherCardRecyclerAdapter);

        ItemTouchHelper.Callback callback = new SwipeHelper(weatherCardRecyclerAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }


}
