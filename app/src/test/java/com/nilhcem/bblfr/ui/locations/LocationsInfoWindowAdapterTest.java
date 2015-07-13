package com.nilhcem.bblfr.ui.locations;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.internal.zzi;
import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.locations.Audience;
import com.nilhcem.bblfr.model.locations.Interest;
import com.nilhcem.bblfr.model.locations.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class LocationsInfoWindowAdapterTest {

    private LocationsInfoWindowAdapter adapter;
    private Marker marker;
    private Location location;

    @Before
    public void setup() {
        location = new Location();
        marker = new Marker(Mockito.mock(zzi.class));
        Map<Marker, Location> map = new HashMap<>();

        map.put(marker, location);
        adapter = new LocationsInfoWindowAdapter(RuntimeEnvironment.application, map);
    }

    @Test
    public void should_bind_data() {
        // Given
        location.name = "Xebia";
        location.address = "Haussmann";
        location.contact = "contact@xebia.fr";
        location.audience = new Audience();
        location.audience.number = "100";
        location.audience.profiles = "Craftsmen";
        location.interests = new ArrayList<>();
        location.interests.add(new Interest("Agile"));

        // When
        adapter.getInfoContents(marker);

        // Then
        assertThat(adapter.mName.getText().toString()).isEqualTo("Xebia");
        assertThat(adapter.mAddress.getText().toString()).isEqualTo("Haussmann");
        assertThat(adapter.mContact.getText().toString()).contains("contact@xebia.fr");
        assertThat(adapter.mAudience.getText().toString()).contains("100").contains("Craftsmen");
        assertThat(adapter.mInterests.getText().toString()).contains("Agile");
    }

    @Test
    public void should_set_view_to_gone_if_content_is_empty() {
        // Given
        TextView tv1 = new TextView(RuntimeEnvironment.application);
        TextView tv2 = new TextView(RuntimeEnvironment.application);

        // When
        adapter.setHtmlTextIfNotEmpty(tv1, null);
        adapter.setHtmlTextIfNotEmpty(tv2, "");

        // Then
        assertThat(tv1.getVisibility()).isEqualTo(View.GONE);
        assertThat(tv2.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void should_set_view_to_visible_if_content_is_not_empty() {
        // Given
        TextView tv = new TextView(RuntimeEnvironment.application);

        // When
        adapter.setHtmlTextIfNotEmpty(tv, "Great");

        // Then
        assertThat(tv.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(tv.getText().toString()).isEqualTo("Great");
    }
}
