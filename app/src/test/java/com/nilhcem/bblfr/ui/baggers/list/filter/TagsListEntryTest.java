package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.os.Build;
import android.os.Parcel;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.baggers.Tag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
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
