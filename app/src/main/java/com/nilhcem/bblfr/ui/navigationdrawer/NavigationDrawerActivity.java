package com.nilhcem.bblfr.ui.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.about.AboutActivity;
import com.nilhcem.bblfr.ui.baggers.cities.CitiesMapActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;
import com.nilhcem.bblfr.ui.locations.LocationsMapActivity;
import com.nilhcem.bblfr.ui.settings.SettingsActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public abstract class NavigationDrawerActivity extends BaseActivity implements View.OnClickListener {

    @Inject Preferences mPrefs;

    private static final String EXTRA_DRAWER_ITEM = "mSelectedDrawerItem";
    protected static final String EXTRA_DISABLE_DRAWER = "mDisableDrawer";

    private ActionBarDrawerToggle mDrawerToggle;

    /* Some activities can decide not to have a navigation drawer */
    private boolean mHasNavigationDrawer;

    /* Intent to launch when an item was selected and the drawer has closed */
    private Intent mPendingDrawerIntent;

    /* The MenuDrawerItem corresponding to the actual activity (useful for highlighting the selected category) */
    private NavigationDrawerEntry mSelectedDrawerItem;

    // A way to perform multiple ButterKnife injections on the same instance object.
    private NavigationDrawerViewHolder mNavigationDrawer;

    static class NavigationDrawerViewHolder {
        @InjectView(R.id.navigation_drawer_layout) DrawerLayout mLayout;
        @InjectView(R.id.navigation_content_frame) FrameLayout mContent;
        @InjectView(R.id.navigation_drawer_view) RecyclerView mRecyclerView;
    }

    private NavigationDrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataFromExtra();
        initLayout();
    }

    @Override
    public void onBackPressed() {
        if (!mHasNavigationDrawer) {
            super.onBackPressed();
            return;
        }

        DrawerLayout drawer = mNavigationDrawer.mLayout;
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (NavigationDrawerEntry.FIND_BAGGER.equals(mSelectedDrawerItem)) {
                super.onBackPressed();
            } else {
                finish();
                Intent intent = BaggersListActivity.createLaunchIntent(this, mPrefs.getCity());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityWithoutTransition(intent);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHasNavigationDrawer) {
            mDrawerToggle.syncState();
            mDrawerAdapter.setSelectedItem(mSelectedDrawerItem);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mHasNavigationDrawer && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (!(v.getTag() instanceof NavigationDrawerEntryView)) {
            Timber.w("onClick called with an unexpected object");
            return;
        }

        NavigationDrawerEntryView view = (NavigationDrawerEntryView) v.getTag();
        NavigationDrawerEntry entry = view.getEntry();

        onNavigationDrawerEntryClicked(entry);
        if (!mSelectedDrawerItem.equals(entry)) {
            switch (entry) {
                case CHANGE_CITY:
                    mPendingDrawerIntent = CitiesMapActivity.createLaunchIntent(this, true);
                    break;
                case ABOUT:
                    mPendingDrawerIntent = AboutActivity.createLaunchIntent(this);
                    break;
                case HOSTS:
                    mPendingDrawerIntent = LocationsMapActivity.createLaunchIntent(this);
                    break;
                case SETTINGS:
                    mPendingDrawerIntent = SettingsActivity.createLaunchIntent(this);
                    break;
                default:
                    mPendingDrawerIntent = BaggersListActivity.createLaunchIntent(this, mPrefs.getCity());
                    break;
            }
            mPendingDrawerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            mPendingDrawerIntent.putExtra(EXTRA_DRAWER_ITEM, entry.name());
        }
        mNavigationDrawer.mLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void finish() {
        super.finish();
        if (mHasNavigationDrawer) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        // Override setContentView to have a specific view injection in the drawer layout.
        View inflated = LayoutInflater.from(this).inflate(layoutResID, getParentView(), false);
        ButterKnife.inject(this, inflated);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        setContentView(inflated);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mNavigationDrawer.mContent.addView(view);
        initNavigationDrawer();
    }

    private void initLayout() {
        mNavigationDrawer = new NavigationDrawerViewHolder();
        super.setContentView(R.layout.navigation_drawer_activity);
        ButterKnife.inject(mNavigationDrawer, this);

        if (mHasNavigationDrawer) {
            RecyclerView recyclerView = mNavigationDrawer.mRecyclerView;
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mDrawerAdapter = new NavigationDrawerAdapter(this, this);
            recyclerView.setAdapter(mDrawerAdapter);
        } else {
            lockNavigationDrawer();
        }
    }

    private void initNavigationDrawer() {
        if (mHasNavigationDrawer) {
            final DrawerLayout drawer = mNavigationDrawer.mLayout;
            mDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    if (mPendingDrawerIntent != null) {
                        startActivityWithoutTransition(mPendingDrawerIntent);
                        finish();
                        mPendingDrawerIntent = null;
                    }
                    super.onDrawerClosed(drawerView);
                }
            };

            drawer.setDrawerListener(mDrawerToggle);
            drawer.setDrawerShadow(R.drawable.drawer_shadow_start, GravityCompat.START);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void setDataFromExtra() {
        Bundle extras = getIntent().getExtras();
        String drawerItemValue = null;
        if (extras != null) {
            mHasNavigationDrawer = !extras.getBoolean(EXTRA_DISABLE_DRAWER);
            drawerItemValue = extras.getString(EXTRA_DRAWER_ITEM);
        }
        mSelectedDrawerItem = NavigationDrawerEntry.valueOf(drawerItemValue, NavigationDrawerEntry.FIND_BAGGER);
    }

    private void startActivityWithoutTransition(Intent intent) {
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    protected boolean isNavigationDrawerVisible() {
        return mNavigationDrawer.mLayout.isDrawerVisible(GravityCompat.START);
    }

    protected void lockNavigationDrawer() {
        mNavigationDrawer.mLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    protected void unlockNavigationDrawer() {
        mNavigationDrawer.mLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    protected void onNavigationDrawerEntryClicked(NavigationDrawerEntry entry) {
        // Hook that may be overridden.
    }

    protected ViewGroup getParentView() {
        return mNavigationDrawer.mContent;
    }
}
