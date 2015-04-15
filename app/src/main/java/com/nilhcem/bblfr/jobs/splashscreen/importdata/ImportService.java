package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.util.Pair;

import com.nilhcem.bblfr.core.prefs.Preferences;

import javax.inject.Inject;

import rx.Observable;

public class ImportService {

    @Inject ImportBaggers mBaggersService;
    @Inject ImportLocations mLocationsService;

    public Observable<Pair<Boolean, Boolean>> importData() {
        return Observable.zip(
                mBaggersService.importData(),
                mLocationsService.importData(),
                Pair::create);
    }
}
