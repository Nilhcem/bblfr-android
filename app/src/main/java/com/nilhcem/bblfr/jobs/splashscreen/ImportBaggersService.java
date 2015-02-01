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
        return getProperJson().flatMap(this::convertToJsonData).map(this::saveToDatabase);
    }

    private Observable<String> getProperJson() {
        return Observable.create(subscriber -> {
            String json = null;
            Request request = new Request.Builder()
                    .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                    .build();

            Response response;
            try {
                response = mClient.newCall(request).execute();
                // Response starts with "var data = {", which we should remove.
                json = response.body().string().replaceFirst("[^{]*", "");
            } catch (IOException e) {
                Timber.e(e, "Error importing baggers");
            }

            subscriber.onNext(json);
            subscriber.onCompleted();
        });
    }

    private Observable<JsonData> convertToJsonData(String json) {
        JsonData jsonData = null;
        try {
            jsonData = mMapper.readValue(json, JsonData.class);
        } catch (IOException e) {
            Timber.e(e, "Error converting json to a JsonData object");
        }
        return Observable.just(jsonData);
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
