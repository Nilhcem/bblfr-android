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

    private static final String PREFS_NAME = "bblfr";
    private static final String KEY_LAST_DOWNLOAD = "last_download_date";
    private static final String KEY_FAV_CITY_LAT = "city_lat";
    private static final String KEY_FAV_CITY_LNG = "city_lng";

    private SharedPreferences mPrefs;

    @Inject
    public Preferences(Application app) {
        mPrefs = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public long getLastDownloadDate() {
        return mPrefs.getLong(KEY_LAST_DOWNLOAD, 0);
    }

    public void setDownloadDate() {
        mPrefs.edit().putLong(KEY_LAST_DOWNLOAD, new Date().getTime()).apply();
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
        mPrefs.edit().putString(KEY_FAV_CITY_LAT, city.lat.toString()).apply();
        mPrefs.edit().putString(KEY_FAV_CITY_LNG, city.lng.toString()).apply();
    }

    public void reset() {
        mPrefs.edit().clear().apply();
    }
}
