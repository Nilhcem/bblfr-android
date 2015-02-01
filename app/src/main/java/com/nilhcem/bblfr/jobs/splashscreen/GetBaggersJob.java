package com.nilhcem.bblfr.jobs.splashscreen;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.events.splashscreen.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.NetworkJob;
import com.nilhcem.bblfr.model.JsonData;
import com.nilhcem.bblfr.model.dao.JsonToDatabaseDao;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.inject.Inject;

public class GetBaggersJob extends NetworkJob {

    @Inject JsonToDatabaseDao mJsonToDatabase;

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
        JsonData jsonData = mMapper.readValue(body, JsonData.class);
        mJsonToDatabase.saveJsonToDatabase(jsonData);

        mBus.post(new BaggersReceivedEvent(jsonData)); // TODO
    }

    @Override
    protected void onCancel() {
        mBus.post(new BaggersReceivedEvent(null));
    }
}
