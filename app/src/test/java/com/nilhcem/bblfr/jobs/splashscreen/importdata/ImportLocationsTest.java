package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.core.prefs.Preferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
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
