package com.nilhcem.bblfr.core.utils;

import android.app.Activity;
import android.content.Intent;

import com.nilhcem.bblfr.ui.splashscreen.SplashscreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class IntentUtilsTest {

    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(Activity.class).create().get();
    }

    @Test
    public void should_start_email_intent() {
        // Given
        String title = "Sending email...";
        String recipient = "g@ut.er";
        String subject = "Hello!";

        // When
        IntentUtils.startEmailIntent(activity, title, recipient, subject);

        // Then
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);
        Intent mailExtra = (Intent) shadowIntent.getParcelableExtra(Intent.EXTRA_INTENT);

        assertThat(shadowIntent.getStringExtra(Intent.EXTRA_TITLE)).isEqualTo(title);
        assertThat(mailExtra.getData().getScheme()).isEqualTo("mailto");
        assertThat(mailExtra.getStringExtra(Intent.EXTRA_SUBJECT)).isEqualTo(subject);
        assertThat(mailExtra.getData().getSchemeSpecificPart()).isEqualTo(recipient);
    }

    @Test
    public void should_start_site_intent() {
        // Given
        String site = "http://www.nilhcem.com";

        // When
        IntentUtils.startSiteIntent(activity, site);

        // Then
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);

        assertThat(shadowIntent.getAction()).isEqualTo(Intent.ACTION_VIEW);
        assertThat(shadowIntent.getDataString()).isEqualTo(site);
    }

    @Test
    public void should_restart_splash_screen_clearing_stack() {
        // When
        IntentUtils.restartApp(activity);

        // Then
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);

        assertThat(shadowIntent.getComponent().getClassName()).isEqualTo(SplashscreenActivity.class.getName());
        assertThat(shadowIntent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
        assertThat(shadowIntent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TASK).isEqualTo(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
