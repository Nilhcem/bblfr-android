package com.nilhcem.bblfr;

import android.app.Application;

import com.nilhcem.bblfr.core.logging.ReleaseTree;

import timber.log.Timber;

public class BBLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
