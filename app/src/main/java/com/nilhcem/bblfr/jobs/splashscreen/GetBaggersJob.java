package com.nilhcem.bblfr.jobs.splashscreen;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.events.splashscreen.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.NetworkJob;
import com.nilhcem.bblfr.model.Baggers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class GetBaggersJob extends NetworkJob {

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
        mBus.post(new BaggersReceivedEvent(mMapper.readValue(body, Baggers.class)));
    }

    @Override
    protected void onCancel() {
        mBus.post(new BaggersReceivedEvent(null));
    }
}
