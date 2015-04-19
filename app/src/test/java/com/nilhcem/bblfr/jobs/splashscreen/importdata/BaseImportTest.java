package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.model.JsonToDatabaseDao;
import com.nilhcem.bblfr.model.baggers.City;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BaseImportTest {

    @Mock Preferences prefs;
    @Spy JsonToDatabaseDao<City> dao;
    private BaseImport<City> importer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(dao).deleteExistingData();
        doNothing().when(dao).saveJsonToDatabase(any());

        importer = new BaseImport<City>(prefs, null, new ObjectMapper(), dao, City.class) {
            @Override
            protected String getUrl() {
                return null;
            }
        };
    }

    @Test
    public void should_convert_to_json_data() {
        // Given
        String json = "{\"name\": \"Paris\", \"ville_img\": \"img/villes/BBL.jpg\",\"lat\": 48.856614, \"lng\": 2.352222}";

        // When
        City city = importer.convertToJsonData(json);

        // Then
        assertThat(city).isNotNull();
        assertThat(city.name).isEqualTo("Paris");
    }

    @Test
    public void should_return_null_if_json_is_null() {
        // Given
        String json = null;

        // When
        City city = importer.convertToJsonData(json);

        // Then
        assertThat(city).isNull();
    }

    @Test
    public void should_delete_existing_data_before_saving_if_specified() {
        // Given
        when(prefs.shouldResetData()).thenReturn(true);

        // When
        importer.saveToDatabase(new City());

        // Then
        verify(dao, times(1)).deleteExistingData();
    }

    @Test
    public void should_not_delete_existing_data_before_saving_if_not_specified() {
        // Given
        when(prefs.shouldResetData()).thenReturn(false);

        // When
        importer.saveToDatabase(new City());

        // Then
        verify(dao, times(0)).deleteExistingData();
    }

    @Test
    public void should_save_to_database_if_data_is_valid() {
        // Given
        City data = new City();

        // When
        Boolean success = importer.saveToDatabase(data);

        // Then
        verify(dao, times(1)).saveJsonToDatabase(data);
        assertThat(success).isTrue();
    }

    @Test
    public void should_not_save_to_database_if_data_is_null() {
        // Given
        City data = null;

        // When
        Boolean success = importer.saveToDatabase(data);

        // Then
        verify(dao, times(0)).saveJsonToDatabase(data);
        assertThat(success).isFalse();
    }
}
