package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.os.Build;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.core.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class ImportLocationsTest {

    @Mock Preferences prefs;
    private ImportLocations importer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        importer = new ImportLocations(prefs, null, null, null);
    }

    @Test
    public void should_return_locations_url() {
        // When
        String url = importer.getUrl();

        // Then
        assertThat(url).contains(BuildConfig.WS_LOCATIONS_URL);
    }
}
