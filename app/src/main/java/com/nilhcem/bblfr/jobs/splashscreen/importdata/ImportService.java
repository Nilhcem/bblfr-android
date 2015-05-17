package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.util.Pair;

import javax.inject.Inject;

import rx.Observable;

public class ImportService {

    private final ImportBaggers mBaggersService;
    private final ImportLocations mLocationsService;

    @Inject
    public ImportService(ImportBaggers importBaggers, ImportLocations importLocations) {
        mBaggersService = importBaggers;
        mLocationsService = importLocations;
    }

    public Observable<Pair<Boolean, Boolean>> importData() {
        return Observable.zip(
                mBaggersService.importData(),
                mLocationsService.importData(),
                Pair::create);
    }
}
