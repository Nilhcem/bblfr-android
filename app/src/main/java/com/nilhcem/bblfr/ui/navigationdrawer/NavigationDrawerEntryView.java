package com.nilhcem.bblfr.ui.navigationdrawer;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.utils.CompatibilityUtils;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class NavigationDrawerEntryView extends BaseRecyclerViewHolder<NavigationDrawerEntry> {

    @InjectView(R.id.navigation_drawer_entry_name) TextView mName;

    private Drawable mDrawable;
    private NavigationDrawerEntry mEntry;

    public NavigationDrawerEntryView(ViewGroup parent) {
        super(parent, R.layout.navigation_drawer_item, false);
    }

    @Override
    public void bindData(NavigationDrawerEntry data) {
        mName.setText(data.title);
        mEntry = data;
        mDrawable = CompatibilityUtils.getDrawable(getContext(), mEntry.drawable);
    }

    public NavigationDrawerEntry getEntry() {
        return mEntry;
    }

    public void setActivated(boolean activated) {
        mName.setTextAppearance(getContext(), activated ? R.style.DrawerEntry_Selected : R.style.DrawerEntry);

        int colorResId = activated ? R.color.primary : R.color.primary_dark;
        mDrawable.setColorFilter(getContext().getResources().getColor(colorResId), PorterDuff.Mode.SRC_ATOP);
        mName.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);

        mName.setActivated(activated);
    }
}
