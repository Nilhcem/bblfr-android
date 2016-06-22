package com.nilhcem.bblfr.ui.about;

import android.content.Intent;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AboutActivityTest {

    private AboutActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(AboutActivity.class);
    }

    @Test
    public void should_create_launch_intent() {
        // When
        Intent intent = AboutActivity.createLaunchIntent(RuntimeEnvironment.application);

        // Then
        assertThat(intent.getComponent().getClassName()).isEqualTo(AboutActivity.class.getName());
    }

    @Test
    public void should_open_blog_article_when_clicking_on_button() {
        // When
        activity.findViewById(R.id.about_see_article).callOnClick();

        // Then
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertThat(intent.getDataString()).contains("lancez-vous-dans-les-brown-bag-lunches");
    }
}
