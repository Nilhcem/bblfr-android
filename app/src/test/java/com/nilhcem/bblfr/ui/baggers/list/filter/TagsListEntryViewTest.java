package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.widget.FrameLayout;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.baggers.Tag;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
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
