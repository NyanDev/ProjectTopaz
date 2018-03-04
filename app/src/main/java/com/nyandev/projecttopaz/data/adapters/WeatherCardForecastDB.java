package com.nyandev.projecttopaz.data.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.customViews.CustomFontTextView;
import com.nyandev.projecttopaz.data.database.model.TableForecast;
import com.nyandev.projecttopaz.data.database.model.TableWeather;
import com.nyandev.projecttopaz.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import hugo.weaving.DebugLog;

/**
 * Created by xuan- on 04/03/2018.
 */

public class WeatherCardForecastDB extends BaseAdapter {

    Context context;
    List<TableForecast> tableForecasts = null;
    TableWeather tableWeather;
    private LayoutInflater layoutInflater = null;

    @BindView(R.id.itemDayForecastExpand)
    TextView itemDayForecastExpand;
    @BindView(R.id.itemTemperatureForecastExpand) TextView itemTemperatureForecastExpand;

    public WeatherCardForecastDB(Context context, TableWeather tableWeather){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tableWeather = tableWeather;
        tableForecasts = new ArrayList<>();
        updateForecast();
    }

    @DebugLog
    public void updateForecast(){
        tableForecasts = tableWeather.getForecasts();
    }


    @Override
    public int getCount() {
        return tableForecasts.size();
    }

    @Override
    public TableForecast getItem(int position) {
        return tableForecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        TextView day;
        TextView temperature;
        TextView icon;
        TextView description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        TableForecast tableForecast = tableForecasts.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.item_expanded_weather_forecast, parent, false);
            viewHolder.day = (TextView) convertView.findViewById(R.id.itemDayForecastExpand);
            viewHolder.temperature = (TextView) convertView.findViewById(R.id.itemTemperatureForecastExpand);
            viewHolder.icon = (CustomFontTextView) convertView.findViewById(R.id.itemIconForecastExpand);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // -- Date --
        Date date = new Date(tableForecast.getDay() * 1000);
        String pattern = "EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dateString = dateFormat.format(date);
        viewHolder.day.setText(dateString);

        // -- Temperature --
        double temperature = tableForecast.getTemperature();
        temperature = Utility.convertTemperature((float)temperature, sharedPreferences);
        viewHolder.temperature.setText(String.format("%.1f %s", temperature, sharedPreferences.getString("temperatureUnit", "°C")));

        // -- Icon --
        String weatherId = "wi_owm_" + Integer.toString(tableForecast.getIcon());
        viewHolder.icon.setText(context.getResources().getIdentifier(weatherId, "string", context.getPackageName()));

        // -- Description --
        //String description = weatherDay.getWeather().get(0).getDescription();
        //viewHolder.description.setText(description);

        return convertView;
    }
}
