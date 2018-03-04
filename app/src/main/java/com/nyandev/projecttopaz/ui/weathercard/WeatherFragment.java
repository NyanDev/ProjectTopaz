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

import com.nyandev.projecttopaz.App;
import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.data.adapters.WeatherCardRecyclerAdapter;
import com.nyandev.projecttopaz.data.events.AllPurposeEvent;
import com.nyandev.projecttopaz.utils.SwipeHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import hugo.weaving.DebugLog;
import retrofit2.Retrofit;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherFragment extends Fragment {

    @BindView(R.id.rv_weatherCard)
    RecyclerView recyclerView;

    private WeatherFragmentPresenter mPresenter;
    LinearLayoutManager linearLayoutManager;
    WeatherCardRecyclerAdapter weatherCardRecyclerAdapter;

    @Nullable
    @Override
    @DebugLog
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_card, container, false);
        return view;
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when an event is posted
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onMessageEvent(AllPurposeEvent event){
            mPresenter.fetchWeatherCity(event.getMessage());
            weatherCardRecyclerAdapter.notifyDataSetChanged();
    }

    public void initView(View view){
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        weatherCardRecyclerAdapter = new WeatherCardRecyclerAdapter(view.getContext());
        mPresenter = new WeatherFragmentPresenter(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mPresenter.weatherCardRecyclerAdapter);

        ItemTouchHelper.Callback callback = new SwipeHelper(weatherCardRecyclerAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

}
