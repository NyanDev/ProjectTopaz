package com.nyandev.projecttopaz.ui.weathercard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.ui.settings.SettingsActivity;

/**
 * Created by xuan- on 01/03/2018.
 */

public class WeatherActivity extends AppCompatActivity {

    WeatherActivityPresenter weatherActivityPresenter;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, WeatherActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherActivityPresenter = new WeatherActivityPresenter();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        WeatherFragment weatherFragment = new WeatherFragment();
        ft.add(R.id.fragment_weather, weatherFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                displayToast("menu_settings");
                startActivity(SettingsActivity.getStartIntent(this));
                return true;
            case R.id.action_search:
                displayToast("action_search");
                return true;
            case R.id.action_update:
                displayToast("action_update");
                return true;
            default:
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initSearchView(Menu menu){
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                weatherActivityPresenter.onQuerySubmit(query);
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}
