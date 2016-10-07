package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.os.Build;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.baggers.Tag;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class TagsListEntryViewTest {

    private TagsListEntryView view;

    @Before
    public void setup() {
        view = new TagsListEntryView(new FrameLayout(RuntimeEnvironment.application));
    }

    @Test
    public void should_bind_data() {
        // Given
        Tag tag = new Tag("Kotlin");
        tag.id = 06L;
        TagsListEntry entry = new TagsListEntry(tag);

        // When
        view.bindData(entry);

        // Then
        assertThat(view.mName.getText().toString()).isEqualTo("#Kotlin");
    }
}
