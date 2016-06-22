package com.nilhcem.bblfr.jobs.splashscreen.importdata;

import android.text.TextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.model.JsonToDatabaseDao;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import timber.log.Timber;

public abstract class BaseImport<T> {

    protected final Preferences mPrefs;

    private final Class<T> mClazz;
    private final OkHttpClient mClient;
    private final ObjectMapper mMapper;
    private final JsonToDatabaseDao<T> mDao;

    public BaseImport(Preferences prefs, OkHttpClient client, ObjectMapper mapper, JsonToDatabaseDao<T> dao, Class<T> clazz) {
        mPrefs = prefs;
        mClient = client;
        mMapper = mapper;
        mDao = dao;
        mClazz = clazz;
    }

    public Observable<Boolean> importData() {
        return getProperJson(getUrl()).map(this::convertToJsonData).map(this::saveToDatabase);
    }

    protected abstract String getUrl();

    private Observable<String> getProperJson(String url) {
        return Observable.fromCallable(() -> {
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
            return json;
        });
    }

    T convertToJsonData(String json) {
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

    protected Boolean saveToDatabase(T data) {
        Boolean success = Boolean.FALSE;

        if (mPrefs.shouldResetData()) {
            mDao.deleteExistingData();
        }
        if (data != null) {
            mDao.saveJsonToDatabase(data);
            success = Boolean.TRUE;
        }
        return success;
    }
}
