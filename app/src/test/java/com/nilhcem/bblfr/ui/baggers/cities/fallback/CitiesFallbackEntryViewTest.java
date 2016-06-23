package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.os.Build;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.baggers.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class CitiesFallbackEntryViewTest {

    private CitiesFallbackEntryView view;

    @Before
    public void setup() {
        view = new CitiesFallbackEntryView(new FrameLayout(RuntimeEnvironment.application));
    }

    @Test
    public void should_bind_data() {
        // Given
        City city = new City();
        city.name = "Franconville";

        // When
        view.bindData(city);

        // Then
        assertThat(view.mName.getText()).isEqualTo("Franconville");
    }
}
