package com.nilhcem.bblfr.ui.navigationdrawer;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class NavigationDrawerHeaderView extends BaseRecyclerViewHolder<Void> {

    @InjectView(R.id.navigation_header_flavor) TextView mFlavor;

    public NavigationDrawerHeaderView(ViewGroup parent) {
        super(parent, R.layout.navigation_drawer_header, false);
    }

    public void setHrMode(boolean hrMode) {
        mFlavor.setText(hrMode ? R.string.drawer_flavor_hr : R.string.drawer_flavor_default);
    }
}
