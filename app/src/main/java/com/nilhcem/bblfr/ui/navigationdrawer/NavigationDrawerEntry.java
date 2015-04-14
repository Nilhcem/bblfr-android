package com.nilhcem.bblfr.ui.navigationdrawer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nilhcem.bblfr.R;

import timber.log.Timber;

public enum NavigationDrawerEntry {

    FIND_BAGGER(R.string.drawer_find_bagger, R.drawable.drawer_account_ic),
    CHANGE_CITY(R.string.drawer_choose_city, R.drawable.drawer_map_ic),
    ABOUT(R.string.drawer_how_to, R.drawable.drawer_idea_ic),
    HOSTS(R.string.drawer_where_to, R.drawable.drawer_marker_ic),
    SETTINGS(R.string.drawer_settings, R.drawable.drawer_settings_ic);

    public final int title;
    public final int drawable;

    private NavigationDrawerEntry(@StringRes int titleRes, @DrawableRes int drawableRes) {
        title = titleRes;
        drawable = drawableRes;
    }

    public static NavigationDrawerEntry valueOf(String name, NavigationDrawerEntry defaultValue) {
        NavigationDrawerEntry item = null;

        if (name != null) {
            try {
                item = NavigationDrawerEntry.valueOf(name);
            } catch (IllegalArgumentException e) {
                // Do nothing, item is already null
                Timber.w(e, "Error getting value for Menu %s", name);
            }
        }
        if (item == null) {
            item = defaultValue;
        }
        return item;
    }
}
