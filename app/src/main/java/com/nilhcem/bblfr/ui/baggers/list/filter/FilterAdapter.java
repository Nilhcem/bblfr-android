package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.ui.BaseHeaderAdapter;

import java.util.List;

public class FilterAdapter extends BaseHeaderAdapter<Void, Tag, FilterHeaderView, FilterEntryView> implements View.OnClickListener {

    public interface OnFilterChangeListener {
        void onFilterChanged(LongSparseArray<Tag> selectedTags);
    }

    private final OnFilterChangeListener mListener;
    private LongSparseArray<Tag> mSelectedTags = new LongSparseArray<>();

    public FilterAdapter(@NonNull OnFilterChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected FilterEntryView onCreateItemView(ViewGroup parent) {
        return new FilterEntryView(parent);
    }

    @Override
    protected FilterHeaderView onCreateHeaderView(ViewGroup parent) {
        return new FilterHeaderView(parent);
    }

    @Override
    protected void onBindItemView(FilterEntryView view, Tag item) {
        super.onBindItemView(view, item);
        view.setOnClickListener(this);
        view.setIsSelected(mSelectedTags.get(item.id) != null);
    }

    public void updateItems(List<Tag> tags) {
        super.updateItems(null, tags);
        mSelectedTags.clear();
        mListener.onFilterChanged(mSelectedTags);
    }

    @Override
    public void onClick(View v) {
        FilterEntryView view = (FilterEntryView) v.getTag();

        // Toggle selected tag, then notify.
        Tag tag = view.getTag();
        if (mSelectedTags.get(tag.id) == null) {
            mSelectedTags.put(tag.id, tag);
        } else {
            mSelectedTags.remove(tag.id);
        }
        notifyItemChanged(view.getAdapterPosition());
        mListener.onFilterChanged(mSelectedTags);
    }
}
