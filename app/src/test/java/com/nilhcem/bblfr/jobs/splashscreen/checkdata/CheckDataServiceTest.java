package com.nilhcem.bblfr.jobs.splashscreen.checkdata;

import android.location.Location;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.dao.BaggersDao;
import com.nilhcem.bblfr.model.baggers.dao.CitiesDao;
import com.nilhcem.bblfr.model.locations.dao.LocationsDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CheckDataServiceTest {

    @Mock Location location;
    private CheckDataService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new CheckDataService(
                mock(Preferences.class),
                mock(CitiesDao.class),
                mock(BaggersDao.class),
                mock(LocationsDao.class),
                mock(LocationProvider.class));
    }

    @Test
    public void should_return_null_if_location_or_cities_are_null() {
        // Given
        List<City> cities = new ArrayList<>();

        // When
        City closestCity1 = service.findClosestCity(null, cities);
        City closestCity2 = service.findClosestCity(location, null);

        // Then
        assertThat(closestCity1).isEqualTo(closestCity2);
        assertThat(closestCity1).isNull();
    }

    @Test
    public void should_return_closest_city() {
        // Given
        when(location.getLatitude()).thenReturn(24.4);
        when(location.getLongitude()).thenReturn(12.0);

        City city1 = new City();
        city1.lat = 42.0;
        city1.lng = 2.1;

        City city2 = new City();
        city2.lat = 24.4;
        city2.lng = 12.2;

        City city3 = new City();
        city3.lat = 7.2;
        city3.lng = 14.3;

        // When
        City closestCity = service.findClosestCity(location, Arrays.asList(city1, city2, city3));

        // Then
        assertThat(closestCity).isEqualTo(city2);
    }
}
