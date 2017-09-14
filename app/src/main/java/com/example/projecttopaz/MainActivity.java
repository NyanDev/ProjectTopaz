package com.example.projecttopaz;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import com.example.projecttopaz.events.AllPurposeEvent;
import com.example.projecttopaz.fragments.WeatherCardFragment;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_SETTINGS = 1;
//    private static final String[] SUGGESTIONS = {
//            "Bauru", "Sao Paulo", "Rio de Janeiro",
//            "Bahia", "Mato Grosso", "Minas Gerais",
//            "Tocantins", "Rio Grande do Sul", "Porto", "Paris", "Panama"
//    };

    public android.support.v4.widget.SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //fragments
        WeatherCardFragment weatherCardFragment = new WeatherCardFragment();
        ft.add(R.id.fragments_view, weatherCardFragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);

        final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new android.support.v4.widget.SimpleCursorAdapter(
                getApplication(),
                R.layout.item_search_suggestion,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.i("searchView", "onSuggestionSelec");
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {

                String query = searchView.getSuggestionsAdapter().getCursor().getString(1);
                Log.i("onSuggestionClick", query);
                onQuerySubmit(searchView, query);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQuerySubmit(searchView, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
//                    @Override
//                    public Cursor runQuery(CharSequence constraint) {
//                        final MatrixCursor mc = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
//                        for (int i=0; i<SUGGESTIONS.length; i++) {
//                            if (SUGGESTIONS[i].toLowerCase().startsWith(constraint.toString().toLowerCase()))
//                                mc.addRow(new Object[] {i, SUGGESTIONS[i]});
//                        }
//                        return mc;
//                    }
//                });
//                searchView.setSuggestionsAdapter(mAdapter);
                return false;
            }
        });
        return true;
    }

    public void onQuerySubmit(SearchView searchView, String query){
        EventBus.getDefault().postSticky(new AllPurposeEvent(query));
        searchView.setQuery("", false);
        searchView.setIconified(true);
        searchView.onActionViewCollapsed();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                Log.i("home","home");
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SETTINGS:
                //displaySettings();


            //TODO 2: handle the update of data
            //WeatherRecyclerFragment weatherRecyclerFragment = new WeatherRecyclerFragment();
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragments_view, weatherRecyclerFragment).commit();

        }
    }

    private void displaySettings(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();

        builder.append("\n Temperature unit : " + sharedPreferences.getString("temperatureUnit", "°C"));
        builder.append("\n Pressure unit : " + sharedPreferences.getString("pressureUnit", "hPa"));
        builder.append("\n Speed unit : " + sharedPreferences.getString("speedUnit", "m/s"));

    }

}
