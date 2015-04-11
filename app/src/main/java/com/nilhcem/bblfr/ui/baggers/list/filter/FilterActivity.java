package com.nilhcem.bblfr.ui.baggers.list.filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.ui.recyclerview.EmptyRecyclerView;
import com.nilhcem.bblfr.core.ui.recyclerview.SimpleDividerItemDecoration;
import com.nilhcem.bblfr.core.utils.CompatibilityUtils;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public abstract class FilterActivity extends BaseActivity implements FilterAdapter.OnFilterChangeListener {

    private static final String EXTRA_CITY = "mCity";

    @Inject protected BaggersService mBaggersService;

    // A way to perform two ButterKnife injections (main + sub layouts) on the same instance
    private ViewHolder mDrawer;

    static class ViewHolder {
        @InjectView(R.id.filter_drawer_layout) DrawerLayout mLayout;
        @InjectView(R.id.filter_content_frame) FrameLayout mContent;
        @InjectView(R.id.filter_recycler_view) EmptyRecyclerView mRecyclerView;
        @InjectView(R.id.loading_view) ProgressBar mEmptyView;
        @InjectView(R.id.toolbar) Toolbar mToolbar;
    }

    private final int mSubLayoutResId;
    private Subscription mTagsSubscription;
    private FilterAdapter mAdapter;
    protected City mCity;

    public static Intent createLaunchIntent(@NonNull Context context, @NonNull Class clazz, City city) {
        Intent intent = new Intent(context, clazz);
        if (city != null) {
            intent.putExtra(EXTRA_CITY, city);
        }
        return intent;
    }

    protected FilterActivity(int layoutResId) {
        super(0);
        mSubLayoutResId = layoutResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromExtra();
        injectMainLayout();
        injectSubLayout();

        mTagsSubscription = AppObservable.bindActivity(this, mBaggersService.getBaggersTags(mCity.id))
                .subscribeOn(Schedulers.io())
                .subscribe(tags -> {
                    Timber.d("Tags loaded from database");
                    mAdapter.updateItems(tags);
                });
    }

    @Override
    protected void onStop() {
        if (mTagsSubscription != null) {
            mTagsSubscription.unsubscribe();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.baggers_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter_baggers) {
            DrawerLayout drawer = mDrawer.mLayout;
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void injectMainLayout() {
        mDrawer = new ViewHolder();
        setContentView(R.layout.filter_activity);
        ButterKnife.inject(mDrawer, this);
        setSupportActionBar(mDrawer.mToolbar);
        mDrawer.mLayout.setDrawerShadow(R.drawable.filter_drawer_shadow, GravityCompat.END);
    }

    private void injectSubLayout() {
        ViewGroup content = mDrawer.mContent;
        mDrawer.mEmptyView.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.primary_light), PorterDuff.Mode.SRC_ATOP);
        EmptyRecyclerView recyclerView = mDrawer.mRecyclerView;

        content.removeAllViews();
        View subView = getLayoutInflater().inflate(mSubLayoutResId, null, true);
        content.addView(subView);
        ButterKnife.inject(this, subView);

        recyclerView.setEmptyView(mDrawer.mEmptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(CompatibilityUtils.getDrawable(this, R.drawable.line_divider)));
        mAdapter = new FilterAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    private void getDataFromExtra() {
        mCity = getIntent().getParcelableExtra(EXTRA_CITY);
    }
}
