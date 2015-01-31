package com.nilhcem.bblfr.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nilhcem.bblfr.BBLApplication;
import com.path.android.jobqueue.JobManager;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends ActionBarActivity {

    protected EventBus mEventBus = EventBus.getDefault();
    protected JobManager mJobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobManager = BBLApplication.get(this).getJobManager();
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
