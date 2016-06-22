package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TagsListActivityTest {

    private TestTagsListActivity activity;
    @Mock DrawerLayout drawer;
    TagsListAdapter tagsAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        City city = new City();
        city.name = "Shanghai";
        Intent intent = new Intent(RuntimeEnvironment.application, TestTagsListActivity.class);
        intent.putExtra("mCity", city);
        activity = Robolectric.buildActivity(TestTagsListActivity.class).withIntent(intent).create().get();

        TagsListActivity.FilterDrawerViewHolder holder = new TagsListActivity.FilterDrawerViewHolder();
        holder.mLayout = drawer;
        activity.mFilterDrawer = holder;

        tagsAdapter = new TagsListAdapter(selectedTagsIds -> {
        });
        tagsAdapter = spy(tagsAdapter);
        activity.mTagsAdapter = tagsAdapter;
    }

    @Test
    public void should_create_launch_intent_with_extra_data() {
        // Given
        City city = new City();
        city.name = "Dalian";

        // When
        Intent intent = BaggersListActivity.createLaunchIntent(RuntimeEnvironment.application, city);

        // Then
        assertThat(intent.getComponent().getClassName()).isEqualTo(BaggersListActivity.class.getName());
        assertThat(((City) intent.getParcelableExtra("mCity")).name).isEqualTo("Dalian");
    }

    @Test
    public void should_open_filter_drawer_when_clicking_on_menu_icon() {
        // Given
        MenuItem item = new RoboMenuItem(R.id.action_filter_baggers);

        // When
        activity.onOptionsItemSelected(item);

        // Then
        verify(drawer).openDrawer(GravityCompat.END);
    }

    @Test
    public void should_close_tags_drawer_when_clicking_on_menu_icon_and_drawer_is_already_opened() {
        // Given
        MenuItem item = new RoboMenuItem(R.id.action_filter_baggers);
        when(drawer.isDrawerOpen(GravityCompat.END)).thenReturn(true);

        // When
        activity.onOptionsItemSelected(item);

        // Then
        verify(drawer).closeDrawer(GravityCompat.END);
    }

    @Test
    public void should_close_filter_drawer_when_pressing_back_if_visible() {
        // Given
        activity.onFilterChanged(Arrays.asList("Android"));
        when(drawer.isDrawerVisible(GravityCompat.END)).thenReturn(true);

        // When
        activity.onBackPressed();

        // Then
        assertThat(activity.mIsFiltered).isTrue();
        verify(drawer).closeDrawer(GravityCompat.END);
    }

    @Test
    public void should_reset_filters_if_any_on_back_pressed_when_drawer_is_not_visible() {
        // Given
        activity.onFilterChanged(Arrays.asList("Android"));
        when(drawer.isDrawerVisible(GravityCompat.END)).thenReturn(false);

        // When
        activity.onBackPressed();

        // Then
        assertThat(activity.mIsFiltered).isFalse();
        verify(drawer, times(0)).closeDrawer(GravityCompat.END);
    }

    @Test
    public void should_set_filter_flag_to_true_when_filter_changes_and_has_data() {
        // Given
        List<String> data = Arrays.asList("Android");

        // When
        activity.onFilterChanged(data);

        // Then
        assertThat(activity.mIsFiltered).isTrue();
    }

    @Test
    public void should_set_filter_flag_to_false_when_filter_changes_and_is_empty() {
        // Given
        List<String> data = new ArrayList<>();

        // When
        activity.onFilterChanged(data);

        // Then
        assertThat(activity.mIsFiltered).isFalse();
    }

    @Test
    public void should_reset_filter() {
        // Given
        activity.onFilterChanged(Arrays.asList("Android"));

        // When
        activity.resetFilter();

        // Then
        assertThat(activity.mIsFiltered).isFalse();
        verify(tagsAdapter).resetFilter();
    }

    @Test
    public void should_set_data_from_extra() {
        // Given When
        // setup()

        // Then
        assertThat(activity.mCity.name).isEqualTo("Shanghai");
    }

    public static class TestTagsListActivity extends TagsListActivity {
    }
}
