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
import com.nyandev.projecttopaz.data.api.WeatherDay;
import com.nyandev.projecttopaz.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xuan- on 26/08/2017.
 */

public class WeatherExpandableForecastAdapter extends BaseAdapter{

    Context context;
    ArrayList<WeatherDay> weatherForecast = new ArrayList<>();
    private LayoutInflater layoutInflater = null;
    @BindView(R.id.itemDayForecastExpand) TextView itemDayForecastExpand;
    @BindView(R.id.itemTemperatureForecastExpand) TextView itemTemperatureForecastExpand;

    public WeatherExpandableForecastAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public WeatherExpandableForecastAdapter(Context context, ArrayList<WeatherDay> weatherForecasts){
        this.context = context;
        this.weatherForecast = weatherForecasts;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void clearForecast(){
        weatherForecast.clear();
        notifyDataSetChanged();
    }

    public void removeForecast(int position){
        weatherForecast.remove(position);
        notifyDataSetChanged();
    }

    public void addWeatherForecast(List<WeatherDay> weatherDay){
        weatherForecast.addAll(weatherDay);
        notifyDataSetChanged();
    }

    public void clearWeatherForecast(){
        weatherForecast.clear();
    }


    @Override
    public int getCount() {
        return weatherForecast.size();
    }

    @Override
    public WeatherDay getItem(int position) {
        return weatherForecast.get(position);
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
        WeatherDay weatherDay = weatherForecast.get(position);
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
        Date date = new Date(weatherDay.getDt() * 1000);
        String pattern = "EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dateString = dateFormat.format(date);
        viewHolder.day.setText(dateString);

        // -- Temperature --
        double temperature = weatherDay.getTemp().getDay();
        temperature = Utility.convertTemperature((float)temperature, sharedPreferences);
        viewHolder.temperature.setText(String.format("%.1f %s", temperature, sharedPreferences.getString("temperatureUnit", "°C")));

        // -- Icon --
        String weatherId = "wi_owm_" + Integer.toString(weatherDay.getWeather().get(0).getId());
        viewHolder.icon.setText(context.getResources().getIdentifier(weatherId, "string", context.getPackageName()));

        // -- Description --
        //String description = weatherDay.getWeather().get(0).getDescription();
        //viewHolder.description.setText(description);

        return convertView;
    }
}
