package com.nyandev.projecttopaz.data.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyandev.projecttopaz.R;
import com.nyandev.projecttopaz.data.models.database.model.TableForecast;
import com.nyandev.projecttopaz.data.models.database.model.TableWeather;
import com.nyandev.projecttopaz.utils.Utility;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * Created by xuan- on 04/03/2018.
 */

public class WeatherCardAdapterDB extends RecyclerView.Adapter<WeatherCardAdapterDB.ViewHolder>{

    private Context context;
    private LayoutInflater inflater = null;
    private List<TableWeather> tableWeathers = new ArrayList<>();
    private List<TableForecast> tableForecasts = new ArrayList<>();
    private WeatherCardForecastDB weatherCardForecastDB;

    public WeatherCardAdapterDB(Context context){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tableWeathers = SQLite.select()
                .from(TableWeather.class)
                .queryList();

    }

    @DebugLog
    public void updateWeather(){
        tableWeathers = SQLite.select()
                .from(TableWeather.class)
                .queryList();
        updateForecasts();
        notifyDataSetChanged();
    }

    public void updateForecasts(){
        weatherCardForecastDB.updateForecast();
    }

    @DebugLog
    public void removeWeather(int position){
        TableWeather tableWeather = tableWeathers.get(position);
        tableWeathers.remove(tableWeather);
        tableWeather.delete();
        notifyItemRemoved(position);
    }

    @DebugLog
    public TableWeather getWeatherInfo(int position){
        return tableWeathers.get(position);
    }

    @Override
    public WeatherCardAdapterDB.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new WeatherCardAdapterDB.ViewHolder(vi);
    }


    @Override
    public void onBindViewHolder(final WeatherCardAdapterDB.ViewHolder holder, final int position) {
        final TableWeather tableWeather = tableWeathers.get(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        holder.lastUpdate = new Date();

        // ---------- weather day -------------
        Date date = new Date(tableWeather.getDay() * 1000);
        String pattern = "EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dateString = dateFormat.format(date);
        holder.cardItemDay.setText(dateString);

        // ---------- weather city -------------
        holder.cardItemLocation.setText(tableWeather.getCityName());

        // ---------- weather icon -------------
        String weatherId = "wi_owm_" + Integer.toString(tableWeather.getIcon());
        holder.cardItemIcon.setText(context.getResources().getIdentifier(weatherId, "string", context.getPackageName()));

        // ---------- weather temperature -------------
        double mainTemp = Utility.convertTemperature((float)tableWeather.getTemperature(), sharedPreferences);
        String tempUnit = sharedPreferences.getString("temperatureUnit", "°C");
        holder.cardItemTemperature.setText(String.format("%.1f %s", mainTemp, tempUnit));

        // ---------- weather description -------------
        holder.cardItemDescription.setText(tableWeather.getDescription());

        // ---------- weather wind  -------------

        float windSpeed = Utility.convertWindSpeed((float)tableWeather.getWindSpeed(), sharedPreferences);
        String speedUnit = sharedPreferences.getString("speedUnit", "m/s");
        holder.cardItemWind.setText(String.format("%.1f %s", windSpeed, speedUnit));

        String windDirectionString = "wi_direction_" + Utility.windDegreeToDirection(tableWeather.getWindDegree());
        holder.cardItemWindDirection.setText(context.getResources().getIdentifier(windDirectionString, "string", context.getPackageName()));

        // ---------- weather pressure  -------------

        float pressure = Utility.convertPressure((float)tableWeather.getPressure(), sharedPreferences);
        String pressureUnit = sharedPreferences.getString("pressureUnit"," hPa");
        holder.cardItemPressure.setText(String.format("%.1f %s", pressure, pressureUnit));

        // ---------- weather humidity  -------------

        float humidity = (float)tableWeather.getHumidity();
        holder.cardItemHumidity.setText(String.format("%s", humidity));

        // ---------- weather expandable layout -------------
        tableForecasts = tableWeather.getForecasts();
        weatherCardForecastDB = new WeatherCardForecastDB(context, tableWeather);
        holder.lv_forecast.setAdapter(weatherCardForecastDB);

    }

    @Override
    public int getItemCount() {
        return tableWeathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardItemLocation)
        TextView cardItemLocation;
        @BindView(R.id.cardItemDescription) TextView cardItemDescription;
        @BindView(R.id.cardItemTemperature) TextView cardItemTemperature;
        @BindView(R.id.cardItemWindDirection) TextView cardItemWindDirection;
        @BindView(R.id.cardItemDay) TextView cardItemDay;
        @BindView(R.id.cardItemIcon) TextView cardItemIcon;

        @BindView(R.id.cardItemWindIcon) TextView cardItemWindIcon;
        @BindView(R.id.cardItemWind) TextView cardItemWind;
        @BindView(R.id.carditemPressureIcon) TextView cardItemPressureIcon;
        @BindView(R.id.cardItemPressure) TextView cardItemPressure;
        @BindView(R.id.cardItemHumidityIcon) TextView cardItemHumidityIcon;
        @BindView(R.id.cardItemHumidity) TextView cardItemHumidity;

        @BindView(R.id.lv_forecast)
        ListViewCompat lv_forecast;
        @BindView(R.id.weatherCard)
        CardView weatherCard;

        Date lastUpdate;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            final ExpandableLayout expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.cardItemForecastExpansion);
            weatherCard.setOnClickListener(new View.OnClickListener() {
                @DebugLog
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    expandableLayout.toggle();
                    updateForecasts();
                }
            });
        }
    }
}

