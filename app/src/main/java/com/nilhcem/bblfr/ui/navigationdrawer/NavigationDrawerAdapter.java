package com.nilhcem.bblfr.ui.navigationdrawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.core.utils.AppUtils;
import com.nilhcem.bblfr.ui.BaseHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerAdapter extends BaseHeaderAdapter<Void, NavigationDrawerEntry, NavigationDrawerHeaderView, NavigationDrawerEntryView> {

    private NavigationDrawerEntry mSelectedItem;

    private final boolean mHrModeEnabled;
    private final View.OnClickListener mListener;

    public NavigationDrawerAdapter(Context context, boolean hrModeEnabled, View.OnClickListener listener) {
        mListener = listener;
        mHrModeEnabled = hrModeEnabled;

        boolean hasPlayServices = AppUtils.hasGooglePlayServices(context);
        List<NavigationDrawerEntry> entries = new ArrayList<>();
        for (NavigationDrawerEntry value : NavigationDrawerEntry.values()) {
            if (!hasPlayServices && value.equals(NavigationDrawerEntry.HOSTS)) {
                continue;
            }
            entries.add(value);
        }
        updateItems(entries, null, true);
    }

    @Override
    protected NavigationDrawerEntryView onCreateItemView(ViewGroup parent) {
        return new NavigationDrawerEntryView(parent);
    }

    @Override
    protected NavigationDrawerHeaderView onCreateHeaderView(ViewGroup parent) {
        return new NavigationDrawerHeaderView(parent);
    }

    @Override
    protected void onBindItemView(NavigationDrawerEntryView view, NavigationDrawerEntry item) {
        super.onBindItemView(view, item);
        view.setOnClickListener(mListener);
        view.setActivated(item.equals(mSelectedItem));
    }

    @Override
    protected void onBindHeaderView(NavigationDrawerHeaderView view, Void header) {
        super.onBindHeaderView(view, header);
        view.setHrMode(mHrModeEnabled);
    }

    public void setSelectedItem(NavigationDrawerEntry selectedItem) {
        mSelectedItem = selectedItem;
    }
}
