package com.nilhcem.bblfr.ui;

import android.os.Bundle;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.events.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.baggers.GetBaggersJob;
import com.nilhcem.bblfr.ui.base.BaseActivity;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobManager.addJob(new GetBaggersJob());
    }

    public void onEventMainThread(BaggersReceivedEvent event) {
        Timber.d("Baggers received");
    }
}
