package com.nilhcem.bblfr.jobs.splashscreen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.model.baggers.BaggersData;
import com.nilhcem.bblfr.model.baggers.dao.JsonToDatabaseDao;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import static com.nilhcem.bblfr.BuildConfig.WS_BAGGERS_URL;
import static com.nilhcem.bblfr.BuildConfig.WS_ENDPOINT;

public class ImportBaggers extends BaseImport<BaggersData> {

    @Inject
    public ImportBaggers(OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao dao) {
        super(client, mapper, dao, WS_ENDPOINT + WS_BAGGERS_URL, BaggersData.class);
    }
}
