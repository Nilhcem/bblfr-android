package com.nilhcem.bblfr;

import android.app.Application;
import android.content.Context;

import com.nilhcem.bblfr.core.logging.JobQueueLogger;
import com.nilhcem.bblfr.core.logging.ReleaseTree;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;

import timber.log.Timber;

public class BBLApplication extends Application {

    private JobManager mJobManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initJobManager();
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private void initJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new JobQueueLogger())
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        mJobManager = new JobManager(this, configuration);
    }

    public JobManager getJobManager() {
        return mJobManager;
    }

    public static BBLApplication get(Context context) {
        return (BBLApplication) context.getApplicationContext();
    }
}
