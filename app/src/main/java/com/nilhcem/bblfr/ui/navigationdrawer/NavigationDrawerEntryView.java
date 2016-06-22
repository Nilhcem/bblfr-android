package com.nilhcem.bblfr.ui.navigationdrawer;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.utils.CompatibilityUtils;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.BindView;

public class NavigationDrawerEntryView extends BaseRecyclerViewHolder<NavigationDrawerEntry> {

    @BindView(R.id.navigation_drawer_entry_name) TextView mName;

    private Drawable mDrawable;

    public NavigationDrawerEntryView(ViewGroup parent) {
        super(parent, R.layout.navigation_drawer_item);
    }

    @Override
    public void bindData(NavigationDrawerEntry data) {
        super.bindData(data);
        mName.setText(data.title);
        mDrawable = CompatibilityUtils.getDrawable(getContext(), data.drawable);
    }

    public void setActivated(boolean activated) {
        mName.setTextAppearance(getContext(), activated ? R.style.DrawerEntry_Selected : R.style.DrawerEntry);

        int colorResId = activated ? R.color.primary : R.color.primary_dark;
        mDrawable.setColorFilter(getContext().getResources().getColor(colorResId), PorterDuff.Mode.SRC_ATOP);
        mName.setCompoundDrawablesWithIntrinsicBounds(mDrawable, null, null, null);

        mName.setActivated(activated);
    }
}
