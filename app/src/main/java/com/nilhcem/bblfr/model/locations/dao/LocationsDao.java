package com.nilhcem.bblfr.model.locations.dao;

import com.nilhcem.bblfr.model.locations.Location;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.query.Select;
import rx.Observable;

@Singleton
public class LocationsDao {

    @Inject
    public LocationsDao() {
    }

    public Observable<List<Location>> getLocations() {
        return Select.from(Location.class).observable();
    }
}
