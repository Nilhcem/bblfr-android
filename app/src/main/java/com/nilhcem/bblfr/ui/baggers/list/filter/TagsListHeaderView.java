package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

public class TagsListHeaderView extends BaseRecyclerViewHolder<String> {

    public TagsListHeaderView(ViewGroup parent) {
        super(parent, R.layout.tags_list_header, false);
    }

    @Override
    public void bindData(String data) {
        // Do nothing.
    }
}
