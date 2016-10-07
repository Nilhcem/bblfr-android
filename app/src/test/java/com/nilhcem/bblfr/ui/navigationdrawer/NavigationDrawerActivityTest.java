package com.nilhcem.bblfr.ui.navigationdrawer;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.google.common.truth.Truth;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class NavigationDrawerActivityTest {

    private TestNavigationDrawerActivity activity;
    @Mock DrawerLayout drawer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Intent intent = new Intent(RuntimeEnvironment.application, TestNavigationDrawerActivity.class);
        intent.putExtra("mSelectedDrawerItem", NavigationDrawerEntry.ABOUT.name());
        activity = Robolectric.buildActivity(TestNavigationDrawerActivity.class).withIntent(intent).create().get();

        activity.mNavigationDrawer = new NavigationDrawerActivity.NavigationDrawerViewHolder();
        activity.mNavigationDrawer.mLayout = drawer;
    }

    @Test
    public void should_close_drawer_if_visible_on_back_pressed() {
        // Given
        when(drawer.isDrawerVisible(GravityCompat.START)).thenReturn(true);

        // When
        activity.onBackPressed();

        // Then
        verify(drawer).closeDrawer(GravityCompat.START);
    }

    @Test
    public void should_start_baggers_list_activity_if_drawer_is_not_visible_on_back_pressed_and_current_activity_is_not_a_baggers_list_activity() {
        // Given
        when(drawer.isDrawerVisible(GravityCompat.START)).thenReturn(false);

        // When
        activity.onBackPressed();

        // Then
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Shadows.shadowOf(startedIntent);
        Truth.assertThat(shadowIntent.getIntentClass().getName()).isEqualTo(BaggersListActivity.class.getName());
    }

    public static class TestNavigationDrawerActivity extends NavigationDrawerActivity {
    }
}
