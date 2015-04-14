package com.nilhcem.bblfr.ui.navigationdrawer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.nilhcem.bblfr.R;

import timber.log.Timber;

public enum NavigationDrawerEntry {

    FIND_BAGGER(R.string.drawer_find_bagger, 0),
    CHANGE_CITY(R.string.drawer_choose_city, 0),
    ABOUT(R.string.drawer_how_to, 0),
    HOSTS(R.string.drawer_where_to, 0),
    SETTINGS(R.string.drawer_settings, 0);

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
