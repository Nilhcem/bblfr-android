package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.view.ViewGroup;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

public class FilterHeaderView extends BaseRecyclerViewHolder<String> {

    public FilterHeaderView(ViewGroup parent) {
        super(parent, R.layout.filter_list_header, false);
    }

    @Override
    public void bindData(String data) {
        // Do nothing.
    }
}
