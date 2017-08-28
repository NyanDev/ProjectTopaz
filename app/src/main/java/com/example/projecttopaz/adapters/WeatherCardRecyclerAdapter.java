package com.example.projecttopaz.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttopaz.R;
import com.example.projecttopaz.events.AllPurposeEvent;
import com.example.projecttopaz.events.WeatherForecastEvent;
import com.example.projecttopaz.models.WeatherDay;
import com.example.projecttopaz.models.WeatherForecast;
import com.example.projecttopaz.models.WeatherInfo;
import com.example.projecttopaz.utils.Utility;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by xuan- on 26/08/2017.
 */

public class WeatherCardRecyclerAdapter extends RecyclerView.Adapter<WeatherCardRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<WeatherInfo> weatherInfos = new ArrayList<>();
    private ArrayList<String> locations = new ArrayList<>();
    private static LayoutInflater inflater = null;
    private ArrayList<WeatherExpandableForecastAdapter> weatherForecasts = new ArrayList<>();

    public WeatherCardRecyclerAdapter(Context context){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public WeatherCardRecyclerAdapter(Context context, ArrayList<WeatherInfo> weatherInfos){
        this.context = context;
        this.weatherInfos = weatherInfos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addWeather(WeatherInfo weatherInfo){
        String location = weatherInfo.getName();
        if (!locations.contains(location)) {
            weatherInfos.add(weatherInfo);
            locations.add(location);
            weatherForecasts.add(new WeatherExpandableForecastAdapter(context));
        }
        notifyDataSetChanged();
    }

    public void clearWeather(){
        weatherInfos.clear();
        locations.clear();
        notifyDataSetChanged();
    }

    public void removeWeather(int position){
        weatherInfos.remove(position);
        locations.remove(position);
        weatherForecasts.remove(position);
        notifyItemRemoved(position);
    }

    public void addWeatherForecast(int position, ArrayList<WeatherDay> weatherForecast){
        weatherForecasts.get(position).clearForecast();
        weatherForecasts.get(position).addWeatherForecast(weatherForecast);
        notifyDataSetChanged();
    }

    @Override
    public WeatherCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new WeatherCardRecyclerAdapter.ViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(final WeatherCardRecyclerAdapter.ViewHolder holder, final int position) {
        final WeatherInfo weatherInfo = weatherInfos.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        holder.lastUpdate = new Date();

        // ---------- weather day -------------
        Date date = new Date(weatherInfo.getDt() * 1000);
        String pattern = "EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dateString = dateFormat.format(date);
        holder.cardItemDay.setText(dateString);

        // ---------- weather city -------------
        holder.cardItemLocation.setText(weatherInfo.getName());

        // ---------- weather icon -------------
        holder.cardItemIcon.setTypeface(holder.weatherFont);
        String weatherId = "wi_owm_" + Integer.toString(weatherInfo.getWeatherList().get(0).getId());
        holder.cardItemIcon.setText(context.getResources().getIdentifier(weatherId, "string", context.getPackageName()));

        // ---------- weather temperature -------------
        double mainTemp = Utility.convertTemperature((float)weatherInfo.getMain().getTemp(), sharedPreferences);
        String tempUnit = sharedPreferences.getString("temperatureUnit", "°C");
        holder.cardItemTemperature.setText(String.format("%.1f %s", mainTemp, tempUnit));

        // ---------- weather description -------------
        holder.cardItemDescription.setText(weatherInfo.getWeatherList().get(0).getDescription());

        // ---------- weather wind direction -------------
        holder.cardItemWindDirection.setTypeface(holder.weatherFont);
        String windDirectionString = "wi_direction_" + Utility.windDegreeToDirection(weatherInfo.getWind().getDeg());
        holder.cardItemWindDirection.setText(context.getResources().getIdentifier(windDirectionString, "string", context.getPackageName()));

        // ---------- weather expandable layout -------------
        holder.lv_forecast.setAdapter(weatherForecasts.get(position));

    }

    @Override
    public int getItemCount() {
        return weatherInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardItemLocation) TextView cardItemLocation;
        @BindView(R.id.cardItemDescription) TextView cardItemDescription;
        @BindView(R.id.cardItemTemperature) TextView cardItemTemperature;
        @BindView(R.id.cardItemWindDirection) TextView cardItemWindDirection;
        @BindView(R.id.cardItemDay) TextView cardItemDay;
        @BindView(R.id.cardItemIcon) TextView cardItemIcon;

        @BindView(R.id.cardItemForecastExpansion) ExpandableLayout cardItemForecastExpansion;
        @BindView(R.id.lv_forecast) ListViewCompat lv_forecast;

        Typeface weatherFont = Typeface.createFromAsset(context.getAssets(), "font/weather.ttf");
        Date lastUpdate;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), lastUpdate.toString(), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new WeatherForecastEvent(getLayoutPosition(), cardItemLocation.getText().toString()));
                    cardItemForecastExpansion.toggle();
                }
            });
        }
    }
}
