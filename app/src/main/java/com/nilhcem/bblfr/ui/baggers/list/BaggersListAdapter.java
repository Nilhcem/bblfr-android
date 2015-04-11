package com.nilhcem.bblfr.ui.baggers.list;

import android.view.ViewGroup;

import com.nilhcem.bblfr.ui.BaseHeaderAdapter;

public class BaggersListAdapter extends BaseHeaderAdapter<String, BaggersListEntry, BaggersListHeaderView, BaggersListEntryView> {

    @Override
    protected BaggersListEntryView onCreateItemView(ViewGroup parent) {
        return new BaggersListEntryView(parent);
    }

    @Override
    protected BaggersListHeaderView onCreateHeaderView(ViewGroup parent) {
        return new BaggersListHeaderView(parent);
    }
}
