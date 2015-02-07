package com.nilhcem.bblfr.model.locations.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.locations.Audience;
import com.nilhcem.bblfr.model.locations.Interest;
import com.nilhcem.bblfr.model.locations.Location;
import com.nilhcem.bblfr.model.locations.LocationInterest;
import com.nilhcem.bblfr.model.locations.LocationsData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ollie.Ollie;

public class JsonToDatabaseDao implements com.nilhcem.bblfr.model.JsonToDatabaseDao<LocationsData> {

    @Inject
    public JsonToDatabaseDao() {
    }

    @Override
    public void saveJsonToDatabase(LocationsData data) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            Map<String, Interest> interestsMap = saveInterests(data.locations);
            saveLocations(data.locations, interestsMap);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private Map<String, Interest> saveInterests(List<Location> locations) {
        Map<String, Interest> map = new HashMap<>();

        for (Location location : locations) {
            List<Interest> interests = location.interests;
            if (interests != null) {
                for (Interest interest : interests) {
                    if (map.get(interest.name) == null) {
                        interest.save();
                        map.put(interest.name, interest);
                    }
                }
            }
        }
        return map;
    }

    private void saveLocations(List<Location> locations, Map<String, Interest> interestsMap) {
        for (Location location : locations) {
            location.save();
            saveLocationInterests(location, location.interests, interestsMap);
            saveLocationAudience(location, location.audience);
        }
    }

    private void saveLocationInterests(Location location, List<Interest> interests, Map<String, Interest> interestsMap) {
        if (interests != null) {
            for (Interest locationInterest : interests) {
                Interest interest = interestsMap.get(locationInterest.name);
                if (interest != null) {
                    LocationInterest li = new LocationInterest(location, interest);
                    li.save();
                }
            }
        }
    }

    private void saveLocationAudience(Location location, Audience audience) {
        if (audience != null) {
            audience.location = location;
            audience.save();
        }
    }
}
