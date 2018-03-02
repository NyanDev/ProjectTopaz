package com.nyandev.projecttopaz.ui.settings;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;

import com.nyandev.projecttopaz.R;

import butterknife.BindArray;
import butterknife.BindString;

/**
 * Created by xuan- on 28/02/2018.
 */

public class SettingsFragmentPresenter {

    private Context mContext;

    public SettingsFragmentPresenter(Context context){
        this.mContext = context;
    }

    public void showAbout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(R.string.app_name);
        final WebView webView = new WebView(mContext);
        String about =
                "<p>An opensource weather app.</p>" +
                        "<p>Developed by <a href='mailto:xnhoang@gmail.com'>Xuan-Nghia Hoang</a></p>" +
                        "<p>Logo icon is made by <a href='https://smashicons.com/'>Smashicons</a> found on <a href='http://www.flaticon.com/'>Flaticons</a>. </p>" +
                        "<p>Weather icons are <a href='https://erikflowers.github.io/weather-icons/'>Weather Icons</a>, by <a href='http://www.twitter.com/artill'>Lukas Bischoff</a> and <a href='http://www.twitter.com/Erik_UX'>Erik Flowers</a>, under the <a href='http://scripts.sil.org/OFL'>SIL OFL 1.1</a> licence."+
                        "<p>Data provided by <a href='http://openweathermap.org/'>OpenWeatherMap</a>, under the <a href='http://creativecommons.org/licenses/by-sa/2.0/'>Creative Commons license</a>" ;
        webView.loadData(about, "text/html", "UTF-8");
        alert.setView(webView);
        alert.create();
        alert.show();
    }
}

