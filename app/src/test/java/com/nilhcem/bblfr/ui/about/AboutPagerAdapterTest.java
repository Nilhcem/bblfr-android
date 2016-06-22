package com.nilhcem.bblfr.ui.about;

import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nilhcem.bblfr.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AboutPagerAdapterTest {

    private AboutPagerAdapter adapter = new AboutPagerAdapter();

    @Before
    public void setup() {
        adapter = new AboutPagerAdapter();
    }

    @Test
    public void should_fill_adapter_data_with_step_entries_on_instantiation() {
        // When
        // (setup instantiation)

        // Then
        assertThat(adapter.getCount()).isEqualTo(AboutPagerStepEntry.values().length);
        assertThat(adapter.getCount()).isEqualTo(4);
    }

    @Test
    public void should_create_imageview_and_add_it_to_the_container() {
        // Given
        FrameLayout container = new FrameLayout(RuntimeEnvironment.application);

        // When
        adapter.instantiateItem(container, 0);

        // Then
        assertThat(container.getChildCount()).isEqualTo(1);
        assertThat(container.getChildAt(0)).isInstanceOf(ImageView.class);
    }
}
