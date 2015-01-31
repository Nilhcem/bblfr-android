package com.nilhcem.bblfr.jobs.baggers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.events.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.NetworkJob;
import com.nilhcem.bblfr.model.Baggers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class GetBaggersJob extends NetworkJob {

    @Override
    public void onRun() throws Throwable {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                .build();

        // Json starts with "var data = {"
        Response response = client.newCall(request).execute();
        String body = response.body().string().replaceFirst("[^{]*", "");

        // Deserialize string to a Json object
        ObjectMapper mapper = new ObjectMapper();
        mBus.post(new BaggersReceivedEvent(mapper.readValue(body, Baggers.class)));
    }

    @Override
    protected void onCancel() {
        mBus.post(new BaggersReceivedEvent(null));
    }
}
