package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.os.Parcel;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.model.baggers.Tag;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class TagsListEntryTest {

    @Test
    public void should_restore_from_parcelable() {
        // Given
        Tag tag = new Tag("Rust");
        tag.id = 25L;
        TagsListEntry entry = new TagsListEntry(tag);
        entry.toggleActivatedState();

        // When
        Parcel parcel = Parcel.obtain();
        entry.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Then
        TagsListEntry fromParcel = TagsListEntry.CREATOR.createFromParcel(parcel);
        assertThat(fromParcel.id).isEqualTo(25L);
        assertThat(fromParcel.name).isEqualTo("#Rust");
        assertThat(fromParcel.isActivated()).isTrue();
    }
}
