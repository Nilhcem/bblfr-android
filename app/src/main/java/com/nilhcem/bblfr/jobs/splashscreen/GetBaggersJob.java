package com.nilhcem.bblfr.jobs.splashscreen;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.events.splashscreen.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.NetworkJob;
import com.nilhcem.bblfr.model.JsonData;
import com.nilhcem.bblfr.model.dao.BaggersDao;
import com.nilhcem.bblfr.model.dao.CitiesDao;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.inject.Inject;

public class GetBaggersJob extends NetworkJob {

    @Inject BaggersDao mBaggersDao;
    @Inject CitiesDao mCitiesDao;

    public GetBaggersJob(Context context) {
        super(context);
    }

    @Override
    public void onRun() throws Throwable {

        Request request = new Request.Builder()
                .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                .build();
        Response response = mClient.newCall(request).execute();

        // Response starts with "var data = {", which we should remove.
        String body = response.body().string().replaceFirst("[^{]*", "");

        // Post the deserialized Json object.
        JsonData baggers = mMapper.readValue(body, JsonData.class);
        mCitiesDao.save(baggers.getCities());
        mBaggersDao.save(baggers.getBaggers());
        mBus.post(new BaggersReceivedEvent(baggers));
    }

    @Override
    protected void onCancel() {
        mBus.post(new BaggersReceivedEvent(null));
    }
}
