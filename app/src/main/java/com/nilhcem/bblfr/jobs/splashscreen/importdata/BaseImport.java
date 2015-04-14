package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.text.TextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.model.JsonToDatabaseDao;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InterruptedIOException;

import rx.Observable;
import timber.log.Timber;

public abstract class BaseImport<T> {

    private String mUrl;
    private Class<T> mClazz;

    private OkHttpClient mClient;
    private ObjectMapper mMapper;
    private JsonToDatabaseDao<T> mDao;

    public BaseImport(OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao<T> dao, String url, Class<T> clazz) {
        mClient = client;
        mMapper = mapper;
        mDao = dao;

        mUrl = url;
        mClazz = clazz;
    }

    public Observable<Boolean> importData() {
        return getProperJson(mUrl).map(this::convertToJsonData).map(this::saveToDatabase);
    }

    protected Observable<String> getProperJson(String url) {
        return Observable.create(subscriber -> {
            String json = null;
            Request request = new Request.Builder()
                    .url(url)
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

    private T convertToJsonData(String json) {
        T jsonData = null;

        if (!TextUtils.isEmpty(json)) {
            try {
                jsonData = mMapper.readValue(json, mClazz);
            } catch (IOException e) {
                Timber.e(e, "Error converting to a json object");
            }
        }
        return jsonData;
    }

    private Boolean saveToDatabase(T data) {
        Boolean success = Boolean.FALSE;
        if (data != null) {
            mDao.saveJsonToDatabase(data);
            success = Boolean.TRUE;
        }
        return success;
    }
}
