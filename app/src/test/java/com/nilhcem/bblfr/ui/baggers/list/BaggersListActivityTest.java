package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Intent;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.baggers.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import edu.emory.mathcs.backport.java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class BaggersListActivityTest {

    private BaggersListActivity activity;

    @Before
    public void setup() {
        City city = new City();
        city.name = "Hong Kong";
        Intent intent = new Intent(RuntimeEnvironment.application, BaggersListActivity.class);
        intent.putExtra("mCity", city);
        activity = Robolectric.buildActivity(BaggersListActivity.class).withIntent(intent).create().get();
    }

    @Test
    public void should_create_launch_intent_with_extra_data() {
        // Given
        City city = new City();
        city.name = "Shenzhen";

        // When
        Intent intent = BaggersListActivity.createLaunchIntent(RuntimeEnvironment.application, city);

        // Then
        assertThat(intent.getComponent().getClassName()).isEqualTo(BaggersListActivity.class.getName());
        assertThat(((City) intent.getParcelableExtra("mCity")).name).isEqualTo("Shenzhen");
    }

    @Test
    public void should_display_city_name_in_title_when_list_is_not_filtered() {
        // Given
        activity.resetFilter();

        // When
        activity.setToolbarTitle(0);

        // Then
        assertThat(activity.getSupportActionBar().getTitle()).contains("Hong Kong");
    }

    @Test
    public void should_display_number_of_results_when_list_is_filtered() {
        // Given
        activity.onFilterChanged(Arrays.asList(new String[]{"Docker", "Android"}));

        // When
        activity.setToolbarTitle(42);

        // Then
        assertThat(activity.getSupportActionBar().getTitle()).contains("42");
    }
}
