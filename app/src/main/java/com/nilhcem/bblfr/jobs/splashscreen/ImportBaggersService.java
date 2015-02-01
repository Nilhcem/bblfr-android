package com.nilhcem.bblfr.jobs.splashscreen;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.model.JsonData;
import com.nilhcem.bblfr.model.dao.JsonToDatabaseDao;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

public class ImportBaggersService {

    @Inject OkHttpClient mClient;
    @Inject ObjectMapper mMapper;
    @Inject JsonToDatabaseDao mDao;

    public Observable<Boolean> importBaggers() {
        return getJsonData().map(this::saveToDatabase);
    }

    private Observable<JsonData> getJsonData() {
        return Observable.create(subscriber -> {
            JsonData jsonData = null;

            Request request = new Request.Builder()
                    .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                    .build();

            Response response;
            try {
                response = mClient.newCall(request).execute();

                // Response starts with "var data = {", which we should remove.
                String body = response.body().string().replaceFirst("[^{]*", "");
                jsonData = mMapper.readValue(body, JsonData.class);
            } catch (IOException e) {
                Timber.e(e, "Error importing baggers");
            }

            subscriber.onNext(jsonData);
            subscriber.onCompleted();
        });
    }

    private Boolean saveToDatabase(JsonData jsonData) {
        Boolean success = Boolean.FALSE;
        if (jsonData != null) {
            mDao.saveJsonToDatabase(jsonData);
            success = Boolean.TRUE;
        }
        return success;
    }
}
