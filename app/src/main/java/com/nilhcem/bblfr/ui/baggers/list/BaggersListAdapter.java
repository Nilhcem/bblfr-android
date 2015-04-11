package com.nilhcem.bblfr.ui.baggers.list;

import android.view.ViewGroup;

import com.nilhcem.bblfr.ui.BaseHeaderAdapter;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

public class BaggersListAdapter extends BaseHeaderAdapter<String, BaggersListEntry> {

    @Override
    protected BaseRecyclerViewHolder onCreateEntryView(ViewGroup parent) {
        return new BaggersListEntryView(parent);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateHeaderView(ViewGroup parent) {
        return new BaggersListHeaderView(parent);
    }
}
