package com.nilhcem.bblfr.ui.navigationdrawer;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class NavigationDrawerEntryView extends BaseRecyclerViewHolder<NavigationDrawerEntry> {

    @InjectView(R.id.navigation_drawer_container) ViewGroup mContainer;
    @InjectView(R.id.navigation_drawer_entry_name) TextView mName;

    private NavigationDrawerEntry mEntry;

    public NavigationDrawerEntryView(ViewGroup parent) {
        super(parent, R.layout.navigation_drawer_item, false);
    }

    @Override
    public void bindData(NavigationDrawerEntry data) {
        mName.setText(data.title);
        mEntry = data;
    }

    public NavigationDrawerEntry getEntry() {
        return mEntry;
    }

    public void setActivated(boolean activated) {
        mContainer.setActivated(activated);
    }
}
