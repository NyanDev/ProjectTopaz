package com.example.projecttopaz;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projecttopaz.events.AllPurposeEvent;
import com.example.projecttopaz.fragments.WeatherCardFragment;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_SETTINGS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //fragments
        //WeatherRecyclerFragment weatherRecyclerFragment = new WeatherRecyclerFragment();
        WeatherCardFragment weatherCardFragment = new WeatherCardFragment();
        ft.add(R.id.fragments_view, weatherCardFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EventBus.getDefault().postSticky(new AllPurposeEvent(query));
                searchView.setQuery("", false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO 1: autocomplete
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SETTINGS:
                // do something
                displaySettings();

                if(mustUpdate()){
                    //WeatherRecyclerFragment weatherRecyclerFragment = new WeatherRecyclerFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragments_view, weatherRecyclerFragment).commit();
                }
        }
    }

    private void displaySettings(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();

        builder.append("\n Temperature unit : " + sharedPreferences.getString("temperatureUnit", "°C"));
        builder.append("\n Pressure unit : " + sharedPreferences.getString("pressureUnit", "hPa"));
        builder.append("\n Speed unit : " + sharedPreferences.getString("speedUnit", "m/s"));

    }

    private boolean mustUpdate(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean settingsUpdated = sp.getBoolean("settingsUpdated", false);
        boolean cityUpdated = sp.getBoolean("cityUpdated", false);
        if (settingsUpdated || cityUpdated){
            sp.edit().putBoolean("settingsUpdated", false);
            sp.edit().putBoolean("cityUpdated", false);

            return true;
        }
        return false;
    }
}
