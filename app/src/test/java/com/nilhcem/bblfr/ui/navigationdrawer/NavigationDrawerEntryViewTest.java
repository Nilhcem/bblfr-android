package com.nilhcem.bblfr.ui.navigationdrawer;

import android.os.Build;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;
import static com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerEntry.HOSTS;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
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
