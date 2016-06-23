package com.nilhcem.bblfr.ui.baggers.cities;

import android.content.Intent;
import android.os.Build;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.ui.baggers.cities.fallback.CitiesFallbackActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
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
