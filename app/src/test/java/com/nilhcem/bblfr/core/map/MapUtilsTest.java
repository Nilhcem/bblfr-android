package com.nilhcem.bblfr.core.map;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapUtilsTest {

    @Test
    public void should_split_gps_string_and_convert_it_to_a_LatLng_object() {
        // Given
        String gps = "47.383442,0.698555";

        // When
        LatLng result = MapUtils.gpsToLatLng(gps);

        // Then
        assertThat(Double.toString(result.latitude)).isEqualTo("47.383442");
        assertThat(Double.toString(result.longitude)).isEqualTo("0.698555");
    }

    @Test
    public void should_handle_spaces_when_converting_gps_value() {
        // Given
        String gps = "  49.383442   ,    0.708555  ";

        // When
        LatLng result = MapUtils.gpsToLatLng(gps);

        // Then
        assertThat(Double.toString(result.latitude)).isEqualTo("49.383442");
        assertThat(Double.toString(result.longitude)).isEqualTo("0.708555");
    }
}
