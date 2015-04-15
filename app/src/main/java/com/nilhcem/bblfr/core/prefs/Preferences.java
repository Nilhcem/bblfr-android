package com.nilhcem.bblfr.core.prefs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.nilhcem.bblfr.model.baggers.City;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Preferences {

    public static final String PREFS_NAME = "bblfr";
    private static final String KEY_LAST_DOWNLOAD = "last_download_date";
    private static final String KEY_FAV_CITY_LAT = "city_lat";
    private static final String KEY_FAV_CITY_LNG = "city_lng";
    private static final String KEY_HR_MODE = "hr_mode";
    private static final String KEY_RESET_DB = "reset_db";

    private SharedPreferences mPrefs;

    /* In-memory data */
    private City mCity;

    @Inject
    public Preferences(Application app) {
        mPrefs = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public long getLastDownloadDate() {
        return mPrefs.getLong(KEY_LAST_DOWNLOAD, 0l);
    }

    public void setDownloadDate() {
        mPrefs.edit()
                .putLong(KEY_LAST_DOWNLOAD, new Date().getTime())
                .remove(KEY_RESET_DB)
                .apply();
    }

    public Pair<String, String> getFavoriteCityLatLng() {
        String lat = mPrefs.getString(KEY_FAV_CITY_LAT, null);
        String lng = mPrefs.getString(KEY_FAV_CITY_LNG, null);

        if (lat == null || lng == null) {
            return null;
        }
        return Pair.create(lat, lng);
    }

    public void setFavoriteCity(City city) {
        mPrefs.edit()
                .putString(KEY_FAV_CITY_LAT, city.lat.toString())
                .putString(KEY_FAV_CITY_LNG, city.lng.toString())
                .apply();
    }

    public void reset() {
        // keep KEY_HR_MODE value
        mPrefs.edit()
                .remove(KEY_LAST_DOWNLOAD)
                .remove(KEY_FAV_CITY_LAT)
                .remove(KEY_FAV_CITY_LNG)
                .putBoolean(KEY_RESET_DB, true)
                .apply();
    }

    public void keepInMemory(City city) {
        mCity = city;
    }

    public City getCity() {
        return mCity;
    }

    public boolean isUsingHrMode() {
        return mPrefs.getBoolean(KEY_HR_MODE, false);
    }

    public void toggleMode() {
        mPrefs.edit()
                .putBoolean(KEY_HR_MODE, !isUsingHrMode())
                .remove(KEY_LAST_DOWNLOAD)
                .putBoolean(KEY_RESET_DB, true)
                .apply();
    }

    public boolean shouldResetData() {
        return mPrefs.getBoolean(KEY_RESET_DB, false);
    }
}
