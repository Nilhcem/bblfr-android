package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.ui.BaseHeaderAdapter;

import java.util.Collections;
import java.util.List;

public class TagsListAdapter extends BaseHeaderAdapter<String, TagsListEntry, TagsListHeaderView, TagsListEntryView> implements View.OnClickListener {

    public interface OnFilterChangeListener {
        void onFilterChanged(List<String> selectedTagsIds);
    }

    private final OnFilterChangeListener mListener;

    public TagsListAdapter(@NonNull OnFilterChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected TagsListEntryView onCreateItemView(ViewGroup parent) {
        return new TagsListEntryView(parent);
    }

    @Override
    protected TagsListHeaderView onCreateHeaderView(ViewGroup parent) {
        return new TagsListHeaderView(parent);
    }

    @Override
    protected void onBindItemView(TagsListEntryView view, TagsListEntry item) {
        super.onBindItemView(view, item);
        view.setOnClickListener(this);
    }

    public void resetFilter() {
        for (TagsListEntry item : mItems) {
            item.resetActivatedState();
        }
        updateItems(mItems, true);
    }

    @Override
    public void updateItems(List<TagsListEntry> items) {
        this.updateItems(items, true);
    }

    public void updateItems(List<TagsListEntry> items, boolean notifyOnFilterChange) {
        super.updateItems(items, "");
        if (notifyOnFilterChange) {
            mListener.onFilterChanged(Collections.emptyList());
        }
    }

    @Override
    public void onClick(View v) {
        TagsListEntryView view = (TagsListEntryView) v.getTag();

        // Toggle selected tag, then notify.
        view.getTag().toggleActivatedState();
        notifyItemChanged(view.getAdapterPosition());
        mListener.onFilterChanged(TagsListEntry.getSelectedTagsIds(mItems));
    }
}
