package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.core.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ImportBaggersTest {

    @Mock Preferences prefs;
    private ImportBaggers importer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        importer = new ImportBaggers(prefs, null, null, null);
    }

    @Test
    public void should_return_baggers_url_in_normal_mode() {
        // Given
        when(prefs.isUsingHrMode()).thenReturn(false);

        // When
        String url = importer.getUrl();

        // Then
        assertThat(url).contains(BuildConfig.WS_BAGGERS_URL);
    }

    @Test
    public void should_return_HR_baggers_url_in_HR_mode() {
        // Given
        when(prefs.isUsingHrMode()).thenReturn(true);

        // When
        String url = importer.getUrl();

        // Then
        assertThat(url).contains(BuildConfig.WS_BAGGERS_HR_URL);
    }
}
