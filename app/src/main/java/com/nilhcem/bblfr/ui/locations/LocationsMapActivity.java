package com.nilhcem.bblfr.ui.locations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.locations.LocationsService;
import com.nilhcem.bblfr.ui.BaseActivity;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LocationsMapActivity extends BaseActivity {

    @Inject LocationsService mLocationsService;

    private Subscription mSubscription;

    public LocationsMapActivity() {
        super(R.layout.locations_map_activity);
    }

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, LocationsMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = AppObservable.bindActivity(this, mLocationsService.getLocations())
                .subscribeOn(Schedulers.io())
                .subscribe(locations -> {
                    Timber.d("BBL Hosts loaded from DB");
                });
    }

    @Override
    protected void onStop() {
        mSubscription.unsubscribe();
        super.onStop();
    }
}
