package com.nilhcem.bblfr.ui.about;

import android.content.Intent;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
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
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Shadows.shadowOf(startedIntent);
        assertThat(shadowIntent.getDataString()).contains("lancez-vous-dans-les-brown-bag-lunches");
    }
}
