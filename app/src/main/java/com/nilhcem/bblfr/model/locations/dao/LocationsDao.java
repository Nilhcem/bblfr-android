package com.nilhcem.bblfr.model.locations.dao;

import android.support.annotation.NonNull;

import com.nilhcem.bblfr.model.locations.Audience;
import com.nilhcem.bblfr.model.locations.Interest;
import com.nilhcem.bblfr.model.locations.Location;
import com.nilhcem.bblfr.model.locations.LocationInterest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.query.Select;

@Singleton
public class LocationsDao {

    @Inject
    public LocationsDao() {
    }

    public boolean hasData() {
        return Select.from(Location.class).fetchSingle() != null;
    }

    public List<Location> getLocations() {
        List<Location> locations = Select.from(Location.class).fetch();
        for (Location location : locations) {
            fillLocationData(location);
        }
        return locations;
    }

    private void fillLocationData(@NonNull Location location) {
        location.audience = Select.from(Audience.class).where("audiences.location_id=?", location.id).fetchSingle();
        location.interests = Select.from(Interest.class).innerJoin(LocationInterest.class).on("interests._id=locations_interests.interest_id").where("locations_interests.location_id=?", location.id).fetch();
    }
}
