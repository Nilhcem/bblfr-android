package com.nilhcem.bblfr.ui.baggers.cities;

import android.content.Intent;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.ui.baggers.cities.fallback.CitiesFallbackActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class CitiesMapActivityTest {

    @Test
    public void should_create_launch_intent_with_disabled_drawer_when_specified() {
        // Given
        boolean enableDrawer = false;

        // When
        Intent intent = CitiesMapActivity.createLaunchIntent(RuntimeEnvironment.application, enableDrawer);

        // Then
        assertThat(intent.getComponent().getClassName()).isEqualTo(CitiesFallbackActivity.class.getName());
        assertThat(intent.getBooleanExtra("mDisableDrawer", false)).isTrue();
    }

    @Test
    public void should_create_launch_intent_with_enabled_drawer_when_specified() {
        // Given
        boolean enableDrawer = true;

        // When
        Intent intent = CitiesMapActivity.createLaunchIntent(RuntimeEnvironment.application, enableDrawer);

        // Then
        assertThat(intent.getComponent().getClassName()).isEqualTo(CitiesFallbackActivity.class.getName());
        assertThat(intent.getBooleanExtra("mDisableDrawer", true)).isFalse();
    }
}
