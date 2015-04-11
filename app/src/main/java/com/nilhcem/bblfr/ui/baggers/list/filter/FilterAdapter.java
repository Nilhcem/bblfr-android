package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;

import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.ui.BaseHeaderAdapter;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import java.util.List;

public class FilterAdapter extends BaseHeaderAdapter<Void, Tag> {

    @Override
    protected BaseRecyclerViewHolder onCreateEntryView(ViewGroup parent) {
        return new FilterEntryView(parent);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateHeaderView(ViewGroup parent) {
        return new FilterHeaderView(parent);
    }

    public void updateItems(List<Tag> tags) {
        super.updateItems(null, tags);
    }
}
