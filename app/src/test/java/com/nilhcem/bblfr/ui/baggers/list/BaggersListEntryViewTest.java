package com.nilhcem.bblfr.ui.baggers.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.Contact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class BaggersListEntryViewTest {

    private BaggersListEntryView view;

    @Before
    public void setup() {
        view = new BaggersListEntryView(new FrameLayout(RuntimeEnvironment.application));
    }

    @Test
    public void should_set_textview_visibility_to_gone_if_text_is_null() {
        // Given
        TextView tv = new TextView(RuntimeEnvironment.application);
        tv.setVisibility(View.VISIBLE);

        // When
        view.setTextIfAny(tv, null);

        // Then
        assertThat(tv.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void should_set_textview_visibility_to_gone_if_text_is_empty() {
        // Given
        TextView tv = new TextView(RuntimeEnvironment.application);
        tv.setVisibility(View.VISIBLE);

        // When
        view.setTextIfAny(tv, "");

        // Then
        assertThat(tv.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void should_set_text_and_textview_visibility_to_visible() {
        // Given
        TextView tv = new TextView(RuntimeEnvironment.application);
        tv.setVisibility(View.GONE);

        // When
        view.setTextIfAny(tv, "Hello!");

        // Then
        assertThat(tv.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(tv.getText().toString()).isEqualTo("Hello!");
    }

    @Test
    public void should_send_email_intent_when_clicking_on_button_to_contact_the_bagger() {
        // Given
        Context c = RuntimeEnvironment.application;
        Button button = new Button(c);
        button.setTag("me@example.com");

        // When
        view.onInviteButtonClicked(button);

        // Then
        ShadowActivity shadowActivity = Shadows.shadowOf(Robolectric.setupActivity(Activity.class));
        Intent intent = shadowActivity.getNextStartedActivity();
        Intent mailExtra = intent.getParcelableExtra(Intent.EXTRA_INTENT);

        assertThat(intent.getStringExtra(Intent.EXTRA_TITLE)).isEqualTo(c.getString(R.string.baggers_list_contact_chooser_title));
        assertThat(mailExtra.getData().getScheme()).isEqualTo("mailto");
        assertThat(mailExtra.getStringExtra(Intent.EXTRA_SUBJECT)).isEqualTo(c.getString(R.string.baggers_list_contact_email_subject));
        assertThat(mailExtra.getData().getSchemeSpecificPart()).isEqualTo("me@example.com");
    }

    @Test
    public void should_bind_data() {
        // Given
        Bagger bagger = new Bagger();
        bagger.name = "Gautier";
        bagger.bio = "Software Craftsman";
        bagger.contacts = new Contact();
        BaggersListEntry entry = new BaggersListEntry(RuntimeEnvironment.application, bagger);

        // When
        view.bindData(entry);

        // Then
        assertThat(view.mName.getText().toString()).isEqualTo("Gautier");
        assertThat(view.mBio.getText().toString()).isEqualTo("Software Craftsman");
    }
}
