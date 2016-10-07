package com.nilhcem.bblfr.core.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.ui.splashscreen.SplashscreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class IntentUtilsTest {

    private Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
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
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        Intent mailExtra = intent.getParcelableExtra(Intent.EXTRA_INTENT);

        assertThat(intent.getStringExtra(Intent.EXTRA_TITLE)).isEqualTo(title);
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
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        assertThat(intent.getAction()).isEqualTo(Intent.ACTION_VIEW);
        assertThat(intent.getDataString()).isEqualTo(site);
    }

    @Test
    public void should_restart_splash_screen_clearing_stack() {
        // When
        IntentUtils.restartApp(activity);

        // Then
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();

        assertThat(intent.getComponent().getClassName()).isEqualTo(SplashscreenActivity.class.getName());
        assertThat(intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isEqualTo(Intent.FLAG_ACTIVITY_NEW_TASK);
        assertThat(intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TASK).isEqualTo(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
