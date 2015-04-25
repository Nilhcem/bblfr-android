package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.view.View;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.baggers.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BBLRobolectricTestRunner.class)
public class CitiesFallbackAdapterTest {

    private CitiesFallbackAdapter adapter;
    @Spy TestOnCitySelectedListener listener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        adapter = new CitiesFallbackAdapter(listener);
    }

    @Test
    public void should_call_listener_with_given_city_data_when_entry_is_selected() {
        // Given
        City city = new City();
        city.name = "Franconville";
        CitiesFallbackEntryView entryView = mock(CitiesFallbackEntryView.class);
        when(entryView.getData()).thenReturn(city);
        View view = new View(RuntimeEnvironment.application);
        view.setTag(entryView);

        // When
        adapter.onClick(view);

        // Then
        verify(listener).onCitySelected(city);
    }

    public static class TestOnCitySelectedListener implements CitiesFallbackAdapter.OnCitySelectedListener {
        @Override
        public void onCitySelected(City selectedCity) {
        }
    }
}
