package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.core.utils.StringUtils;
import com.nilhcem.bblfr.model.baggers.BaggersData;
import com.nilhcem.bblfr.model.baggers.dao.JsonToDatabaseDao;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

import static com.nilhcem.bblfr.BuildConfig.WS_BAGGERS_HR_URL;
import static com.nilhcem.bblfr.BuildConfig.WS_BAGGERS_URL;
import static com.nilhcem.bblfr.BuildConfig.WS_DATA_ENDPOINT;

public class ImportBaggers extends BaseImport<BaggersData> {

    @Inject
    public ImportBaggers(Preferences prefs, OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao dao) {
        super(prefs, client, mapper, dao, BaggersData.class);
    }

    @Override
    protected String getUrl() {
        return StringUtils.appendOptional(WS_DATA_ENDPOINT, mPrefs.isUsingHrMode() ? WS_BAGGERS_HR_URL : WS_BAGGERS_URL);
    }
}
