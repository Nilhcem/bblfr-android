package com.nilhcem.bblfr.jobs.locations;

import com.nilhcem.bblfr.model.locations.Location;
import com.nilhcem.bblfr.model.locations.dao.LocationsDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class LocationsService {

    @Inject LocationsDao mDao;

    public Observable<List<Location>> getLocations() {
        return Observable.defer(() -> Observable.just(mDao.getLocations()));
    }
}
