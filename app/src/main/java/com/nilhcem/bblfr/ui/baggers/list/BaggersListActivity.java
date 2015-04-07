package com.nilhcem.bblfr.ui.baggers.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BaggersListActivity extends BaseActivity {

    private static final String EXTRA_CITY_ID = "mCityId";
    private static final String EXTRA_CITY_NAME = "mCityName";

    @Inject BaggersService mBaggersService;
    private Long mCityId;
    private String mCityName;

    public static void launch(@NonNull Context context, City city) {
        Intent intent = new Intent(context, BaggersListActivity.class);
        if (city != null) {
            intent.putExtra(EXTRA_CITY_ID, city.id);
            intent.putExtra(EXTRA_CITY_NAME, city.name);
        }
        context.startActivity(intent);
    }

    public BaggersListActivity() {
        super(R.layout.baggers_list_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromExtra();
        setToolbarTitle();

        mSubscription = AppObservable.bindActivity(this, mBaggersService.getBaggers(mCityId))
                .subscribeOn(Schedulers.io())
                .subscribe(this::onBaggersLoaded);
    }

    private void setToolbarTitle() {
        String title;

        if (TextUtils.isEmpty(mCityName)) {
            title = getString(R.string.baggers_map_toolbar_title);
        } else {
            title = getString(R.string.baggers_list_toolbar_title, mCityName);
        }
        getSupportActionBar().setTitle(title);
    }

    private void getDataFromExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(EXTRA_CITY_ID)) {
                mCityId = extras.getLong(EXTRA_CITY_ID);
            }
            mCityName = getIntent().getStringExtra(EXTRA_CITY_NAME);
        }
    }

    private void onBaggersLoaded(List<Bagger> baggers) {
        Timber.d("Baggers loaded from DB");
    }
}
