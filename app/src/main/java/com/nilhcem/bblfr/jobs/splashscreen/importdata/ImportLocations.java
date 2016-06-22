package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.core.utils.StringUtils;
import com.nilhcem.bblfr.model.locations.LocationsData;
import com.nilhcem.bblfr.model.locations.dao.JsonToDatabaseDao;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static com.nilhcem.bblfr.BuildConfig.WS_ENDPOINT;
import static com.nilhcem.bblfr.BuildConfig.WS_LOCATIONS_URL;

public class ImportLocations extends BaseImport<LocationsData> {

    @Inject
    public ImportLocations(Preferences prefs, OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao dao) {
        super(prefs, client, mapper, dao, LocationsData.class);
    }

    @Override
    protected String getUrl() {
        return StringUtils.appendOptional(WS_ENDPOINT, WS_LOCATIONS_URL);
    }
}
