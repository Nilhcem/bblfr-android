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
import rx.functions.Func0;
import timber.log.Timber;

public class ImportBaggersService {

    @Inject OkHttpClient mClient;
    @Inject ObjectMapper mMapper;
    @Inject JsonToDatabaseDao mDao;

    public Observable<Boolean> importBaggers() {
        // With defer, the Observable returned won't call the following blocking method until we subscribe to it.
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                Boolean success = Boolean.FALSE;
                Request request = new Request.Builder()
                        .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                        .build();

                Response response;
                try {
                    response = mClient.newCall(request).execute();

                    // Response starts with "var data = {", which we should remove.
                    String body = response.body().string().replaceFirst("[^{]*", "");
                    JsonData jsonData = mMapper.readValue(body, JsonData.class);

                    mDao.saveJsonToDatabase(jsonData);
                    success = Boolean.TRUE;
                } catch (IOException e) {
                    Timber.e(e, "Error importing baggers");
                }
                return Observable.just(success);
            }
        });
    }
}
