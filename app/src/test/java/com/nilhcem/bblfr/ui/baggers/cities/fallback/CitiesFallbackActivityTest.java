package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.content.Intent;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
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
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Shadows.shadowOf(startedIntent);
        assertThat(shadowIntent.getComponent().getClassName()).isEqualTo(BaggersListActivity.class.getName());
        assertThat(((City) shadowIntent.getParcelableExtra("mCity")).name).isEqualTo("Paris");
    }
}
