package com.nilhcem.bblfr.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.Baggers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, Baggers>() {

            @Override
            protected Baggers doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BuildConfig.WS_ENDPOINT + BuildConfig.WS_BAGGERS_URL)
                        .build();
                try {
                    // Json starts with "var data = {"
                    Response response = client.newCall(request).execute();
                    String body = response.body().string().replaceFirst("[^{]*", "");

                    // Deserialize string to a Json object
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(body, Baggers.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Baggers baggers) {
                super.onPostExecute(baggers);

            }
        }.execute();
    }
}
