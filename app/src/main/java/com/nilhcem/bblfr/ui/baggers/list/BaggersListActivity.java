package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.ui.baggers.list.filter.FilterActivity;

import java.util.List;

import butterknife.InjectView;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BaggersListActivity extends FilterActivity {

    @InjectView(R.id.baggers_list_recycler_view) RecyclerView mRecyclerView;

    private BaggersListAdapter mAdapter;

    public static void launch(@NonNull Context context, City city) {
        context.startActivity(createLaunchIntent(context, BaggersListActivity.class, city));
    }

    public BaggersListActivity() {
        super(R.layout.baggers_list_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle();
        initRecyclerView();

        mSubscription = AppObservable.bindActivity(this, mBaggersService.getBaggers(this, mCity.id))
                .subscribeOn(Schedulers.io())
                .subscribe(this::onBaggersLoaded);
    }

    private void setToolbarTitle() {
        String title;

        if (TextUtils.isEmpty(mCity.name)) {
            title = getString(R.string.baggers_map_toolbar_title);
        } else {
            title = getString(R.string.baggers_list_toolbar_title, mCity.name);
        }
        getSupportActionBar().setTitle(title);
    }

    private void onBaggersLoaded(List<BaggersListEntry> baggers) {
        Timber.d("Baggers loaded from DB");
        mAdapter.updateItems(NetworkUtils.getAbsoluteUrl(mCity.picture), baggers);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaggersListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onFilterChanged(List<Tag> selectedTags) {

    }
}
