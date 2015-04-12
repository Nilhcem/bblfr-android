package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.nilhcem.bblfr.model.baggers.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TagsListEntry implements Parcelable {

    public final long id;
    public final String name;
    private boolean mActivated;

    public TagsListEntry(Tag tag) {
        id = tag.id;
        name = String.format(Locale.US, "#%s", tag.name);
    }

    public boolean isActivated() {
        return mActivated;
    }

    public void toggleActivatedState() {
        mActivated = !mActivated;
    }

    public void resetActivatedState() {
        mActivated = false;
    }

    public static List<String> getSelectedTagsIds(List<TagsListEntry> items) {
        List<String> selectedTagsIds = new ArrayList<>();
        for (TagsListEntry item : items) {
            if (item.isActivated()) {
                selectedTagsIds.add(Long.toString(item.id));
            }
        }
        return selectedTagsIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeByte(mActivated ? (byte) 1 : (byte) 0);
    }

    private TagsListEntry(Parcel in) {
        id = in.readLong();
        name = in.readString();
        mActivated = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TagsListEntry> CREATOR = new Parcelable.Creator<TagsListEntry>() {
        public TagsListEntry createFromParcel(Parcel source) {
            return new TagsListEntry(source);
        }

        public TagsListEntry[] newArray(int size) {
            return new TagsListEntry[size];
        }
    };
}
