package com.nilhcem.bblfr.ui.navigationdrawer;

import android.view.ViewGroup;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

public class NavigationDrawerHeaderView extends BaseRecyclerViewHolder<String> {

    public NavigationDrawerHeaderView(ViewGroup parent) {
        super(parent, R.layout.navigation_drawer_header, false);
    }

    @Override
    public void bindData(String data) {
        // Do nothing.
    }
}
