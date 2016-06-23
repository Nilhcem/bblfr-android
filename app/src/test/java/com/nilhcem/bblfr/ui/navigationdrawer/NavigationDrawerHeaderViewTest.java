package com.nilhcem.bblfr.ui.navigationdrawer;

import android.os.Build;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class NavigationDrawerHeaderViewTest {

    private NavigationDrawerHeaderView view;

    @Before
    public void setup() {
        view = new NavigationDrawerHeaderView(new FrameLayout(RuntimeEnvironment.application));
    }

    @Test
    public void should_display_hr_text_in_hr_mode() {
        // Given
        boolean hrMode = true;

        // When
        view.setHrMode(hrMode);

        // Then
        assertThat(view.mFlavor.getText().toString()).isEqualTo(RuntimeEnvironment.application.getString(R.string.drawer_flavor_hr));
    }

    @Test
    public void should_display_nothing_in_normal_mode() {
        // Given
        boolean hrMode = false;

        // When
        view.setHrMode(hrMode);

        // Then
        assertThat(view.mFlavor.getText().toString()).isEmpty();
    }
}
