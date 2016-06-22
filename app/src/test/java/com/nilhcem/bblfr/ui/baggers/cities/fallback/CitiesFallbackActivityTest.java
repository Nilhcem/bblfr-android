package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.content.Intent;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CitiesFallbackActivityTest {

    private CitiesFallbackActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(CitiesFallbackActivity.class);
    }

    @Test
    public void should_start_baggers_list_activity_when_city_is_selected() {
        // Given
        City city = new City();
        city.name = "Paris";
        city.lat = 42d;
        city.lng = 1d;

        // When
        activity.onCitySelected(city);

        // Then
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName()).isEqualTo(BaggersListActivity.class.getName());
        assertThat(((City) intent.getParcelableExtra("mCity")).name).isEqualTo("Paris");
    }
}
