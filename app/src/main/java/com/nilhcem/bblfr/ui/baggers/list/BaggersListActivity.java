package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.ui.recyclerview.EmptyRecyclerView;
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

    @InjectView(R.id.baggers_list_recycler_view) EmptyRecyclerView mRecyclerView;
    @InjectView(R.id.loading_view) View mEmptyView;

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
        setToolbarTitle(0);
        initRecyclerView();
    }

    @Override
    public void onFilterChanged(LongSparseArray<Tag> selectedTags) {
        unsubscribeSubscription();
        mAdapter.updateItems(null, null);
        mRecyclerView.scrollToPosition(0);

        mSubscription = AppObservable.bindActivity(this, mBaggersService.getBaggers(this, mCity.id, selectedTags))
                .subscribeOn(Schedulers.io())
                .subscribe(this::onBaggersLoaded);
    }

    private void setToolbarTitle(int nbBaggers) {
        String title;

        if (TextUtils.isEmpty(mCity.name)) {
            title = getString(R.string.baggers_map_toolbar_title);
        } else if (nbBaggers == 0) {
            title = getString(R.string.baggers_list_toolbar_title, mCity.name);
        } else if (nbBaggers == 1) {
            title = getString(R.string.baggers_list_toolbar_title_one, mCity.name);
        } else {
            title = getString(R.string.baggers_list_toolbar_title_many, nbBaggers, mCity.name);
        }
        getSupportActionBar().setTitle(title);
    }

    private void onBaggersLoaded(List<BaggersListEntry> baggers) {
        Timber.d("Baggers loaded from DB");
        mAdapter.updateItems(baggers, NetworkUtils.getAbsoluteUrl(mCity.picture));
        setToolbarTitle(baggers.size());
    }

    private void initRecyclerView() {
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaggersListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
