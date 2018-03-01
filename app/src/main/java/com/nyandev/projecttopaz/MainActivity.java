package com.nyandev.projecttopaz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyandev.projecttopaz.ui.weathercard.WeatherActivity;

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
        startActivity(WeatherActivity.getStartIntent(this));

/**
        setContentView(R.layout.activity_main);
        // ensure that we set all the default value from preference manager
        PreferenceManager.setDefaultValues(this, R.xml.prefs_main, false);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //fragments
        WeatherCardFragmentOld weatherCardFragment = new WeatherCardFragmentOld();
        ft.add(R.id.fragments_view, weatherCardFragment)
                .commit();

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(
                new GooglePlayDriver(MainActivity.this)
        );
        dispatcher.mustSchedule(
                dispatcher.newJobBuilder()
                        .setService(UpdateDataService.class)
                        .setTag("UpdateDataService")
                        .setRecurring(true)
                        .setTrigger(Trigger.executionWindow(3600, 3600 * 3))// will trigger between 1 hour and 3 hours
                        .setConstraints(Constraint.ON_ANY_NETWORK)
                        .setReplaceCurrent(false)
                        .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                        .build()
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final String[] from = new String[]{"cityName"};
        final int[] to = new int[]{android.R.id.text1};
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

    public void onQuerySubmit(SearchView searchView, String query) {
        EventBus.getDefault().postSticky(new AllPurposeEvent(query));
        searchView.setQuery("", false);
        searchView.setIconified(true);
        searchView.onActionViewCollapsed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                Log.i("home", "home");
                break;
            case R.id.action_update:
                updateData(900000);

        }
        return true;
    }

    public void updateData(int timer) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long current = System.currentTimeMillis();
        if (current - sharedPreferences.getLong("lastAction", -1) >= timer) {
            sharedPreferences.edit().putLong("lastAction", current).apply();
            // send event to update recycler view
            EventBus.getDefault().postSticky(new AllPurposeEvent("fetchNewData"));
        }
    }
}
*/
    }
}