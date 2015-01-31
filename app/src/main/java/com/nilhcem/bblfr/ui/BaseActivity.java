package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nilhcem.bblfr.BBLApplication;
import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;
    @Inject protected EventBus mEventBus;
    @Inject protected JobManager mJobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = BBLApplication.get(this);
        mApplication.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override
    protected void onPause() {
        mEventBus.unregister(this);
        super.onPause();
    }
}
