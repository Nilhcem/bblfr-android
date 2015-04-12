package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.core.ui.recyclerview.EmptyRecyclerView;
import com.nilhcem.bblfr.core.ui.recyclerview.SimpleDividerItemDecoration;
import com.nilhcem.bblfr.core.utils.CompatibilityUtils;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Displays a list of cities.
 * <p>
 * Fallback for {@link com.nilhcem.bblfr.ui.baggers.cities.CitiesMapActivity}
 * for devices without Google Play Services)
 * </p>
 */
public class CitiesFallbackActivity extends BaseActivity implements CitiesFallbackAdapter.OnCitySelectedListener {

    @Inject Preferences mPrefs;
    @Inject BaggersService mBaggersService;

    @InjectView(R.id.cities_fallback_recycler_view) EmptyRecyclerView mRecyclerView;
    @InjectView(R.id.loading_view) View mEmptyView;

    private CitiesFallbackAdapter mAdapter;

    public CitiesFallbackActivity() {
        super(R.layout.cities_fallback_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.baggers_map_toolbar_title);
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSubscription = AppObservable.bindActivity(this, mBaggersService.getBaggersCities())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onCitiesLoaded);
    }

    @Override
    public void onCitySelected(City city) {
        Timber.d("City selected");
        mPrefs.setFavoriteCity(city);
        BaggersListActivity.launch(this, city);
    }

    private void initRecyclerView() {
        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CompatibilityUtils.getDrawable(this, R.drawable.line_divider)));
        mAdapter = new CitiesFallbackAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onCitiesLoaded(List<City> cities) {
        Timber.d("BBL Cities loaded from DB");
        mAdapter.updateItems(cities);
    }
}
