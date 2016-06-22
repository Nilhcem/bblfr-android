package com.nilhcem.bblfr.core.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.baggers.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static com.nilhcem.bblfr.core.prefs.Preferences.KEY_FAV_CITY_LAT;
import static com.nilhcem.bblfr.core.prefs.Preferences.KEY_FAV_CITY_LNG;
import static com.nilhcem.bblfr.core.prefs.Preferences.KEY_HR_MODE;
import static com.nilhcem.bblfr.core.prefs.Preferences.KEY_LAST_DOWNLOAD;
import static com.nilhcem.bblfr.core.prefs.Preferences.KEY_RESET_DB;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PreferencesTest {

    private Preferences prefs;
    private SharedPreferences sharedPrefs;

    @Before
    public void setup() {
        prefs = new Preferences(RuntimeEnvironment.application);
        sharedPrefs = RuntimeEnvironment.application.getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Test
    public void should_return_0_as_last_download_date_at_first_launch_if_none() {
        // Given
        sharedPrefs.edit().remove(KEY_LAST_DOWNLOAD).commit();

        // When
        long lastDownloadDate = prefs.getLastDownloadDate();

        // Then
        assertThat(lastDownloadDate).isEqualTo(0L);
    }

    @Test
    public void should_return_last_download_date_if_set() {
        // Given
        long firstTime = new Date().getTime();
        sharedPrefs.edit().remove(KEY_LAST_DOWNLOAD).commit();

        // When
        prefs.setDownloadDate();
        long lastDownloadDate = prefs.getLastDownloadDate();
        long lastTime = new Date().getTime();

        // Then
        assertThat(lastDownloadDate).isAtLeast(firstTime);
        assertThat(lastDownloadDate).isAtMost(lastTime);
    }

    @Test
    public void should_remove_reset_database_flag_when_setting_a_download_date() {
        // Given
        sharedPrefs.edit().putBoolean(KEY_RESET_DB, true).commit();

        // When
        prefs.setDownloadDate();

        // Then
        assertThat(sharedPrefs.contains(KEY_RESET_DB)).isFalse();
    }

    @Test
    public void should_create_a_pair_of_lat_lng_when_getting_favorite_city() {
        // Given
        sharedPrefs.edit().putString(KEY_FAV_CITY_LAT, "42").putString(KEY_FAV_CITY_LNG, "24").commit();

        // When
        Pair<String, String> result = prefs.getFavoriteCityLatLng();

        // Then
        assertThat(result.first).isEqualTo("42");
        assertThat(result.second).isEqualTo("24");
    }

    @Test
    public void should_return_null_if_lat_or_lng_is_missing() {
        // Given
        sharedPrefs.edit().remove(KEY_FAV_CITY_LAT).putString(KEY_FAV_CITY_LNG, "24").commit();

        // When
        Pair<String, String> result = prefs.getFavoriteCityLatLng();

        // Then
        assertThat(result).isNull();
    }

    @Test
    public void should_save_favorite_city_coordinates_in_prefs_and_object_in_memory() {
        // Given
        City city = new City();
        city.lat = 10.5d;
        city.lng = 20.4d;

        // When
        prefs.setFavoriteCity(city);

        // Then
        assertThat(prefs.mCity).isSameAs(city);
        assertThat(sharedPrefs.getString(KEY_FAV_CITY_LAT, null)).isEqualTo("10.5");
        assertThat(sharedPrefs.getString(KEY_FAV_CITY_LNG, null)).isEqualTo("20.4");
    }

    @Test
    public void reset_should_remove_all_keys_except_hr_mode_and_should_set_reset_flag_to_true() {
        // Given
        sharedPrefs.edit().remove(KEY_RESET_DB).putBoolean(KEY_HR_MODE, true).putString(KEY_FAV_CITY_LAT, "10").putLong(KEY_LAST_DOWNLOAD, 10L).commit();

        // When
        prefs.reset();
        boolean shouldResetData = prefs.shouldResetData();

        // Then
        assertThat(shouldResetData).isTrue();
        assertThat(sharedPrefs.getBoolean(KEY_RESET_DB, false)).isTrue();
        assertThat(sharedPrefs.getBoolean(KEY_HR_MODE, false)).isTrue();
        assertThat(sharedPrefs.contains(KEY_FAV_CITY_LAT)).isFalse();
        assertThat(sharedPrefs.contains(KEY_LAST_DOWNLOAD)).isFalse();
    }

    @Test
    public void should_toggle_hr_mode_in_shared_prefs() {
        // Given
        sharedPrefs.edit().remove(KEY_HR_MODE);

        // When
        prefs.toggleMode();
        boolean res1 = prefs.isUsingHrMode();
        prefs.toggleMode();
        boolean res2 = !prefs.isUsingHrMode();

        // Then
        assertThat(res1).isEqualTo(res2);
        assertThat(res1).isTrue();
    }
}
