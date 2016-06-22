package com.nilhcem.bblfr.ui.navigationdrawer;

import android.widget.FrameLayout;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;
import static com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerEntry.HOSTS;

@RunWith(BBLRobolectricTestRunner.class)
public class NavigationDrawerEntryViewTest {

    private NavigationDrawerEntryView view;

    @Before
    public void setup() {
        view = new NavigationDrawerEntryView(new FrameLayout(RuntimeEnvironment.application));
    }

    @Test
    public void should_bind_data() {
        // Given
        NavigationDrawerEntry entry = HOSTS;

        // When
        view.bindData(entry);

        // Then
        assertThat(view.mName.getText().toString()).isEqualTo(RuntimeEnvironment.application.getString(HOSTS.title));
    }
}
