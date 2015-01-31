package com.nilhcem.bblfr.ui.splashscreen;

import android.os.Bundle;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.events.splashscreen.BaggersReceivedEvent;
import com.nilhcem.bblfr.jobs.splashscreen.GetBaggersJob;
import com.nilhcem.bblfr.model.dao.BaggersDao;
import com.nilhcem.bblfr.ui.BaseActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashscreenActivity extends BaseActivity {

    @Inject BaggersDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);
        mJobManager.addJob(new GetBaggersJob(this));
    }

    public void onEventMainThread(BaggersReceivedEvent event) {
        Timber.d("Baggers received");

    }
}
