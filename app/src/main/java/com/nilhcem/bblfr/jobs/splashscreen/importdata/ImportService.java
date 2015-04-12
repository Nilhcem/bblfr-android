package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.util.Pair;

import javax.inject.Inject;

import rx.Observable;

public class ImportService {

    @Inject ImportBaggers baggersService;
    @Inject ImportLocations locationsService;

    public Observable<Pair<Boolean, Boolean>> importData() {
        return Observable.zip(
                baggersService.importData(),
                locationsService.importData(),
                Pair::create);
    }
}
