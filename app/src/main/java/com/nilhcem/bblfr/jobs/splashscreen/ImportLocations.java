package com.nilhcem.bblfr.jobs.splashscreen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.model.locations.LocationsData;
import com.nilhcem.bblfr.model.locations.dao.JsonToDatabaseDao;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import static com.nilhcem.bblfr.BuildConfig.WS_ENDPOINT;
import static com.nilhcem.bblfr.BuildConfig.WS_LOCATIONS_URL;

public class ImportLocations extends BaseImport<LocationsData> {

    @Inject
    public ImportLocations(OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao dao) {
        super(client, mapper, dao, WS_ENDPOINT + WS_LOCATIONS_URL, LocationsData.class);
    }
}
