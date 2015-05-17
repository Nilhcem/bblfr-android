package com.nilhcem.bblfr;

import android.app.Application;
import android.content.Context;

import com.nilhcem.bblfr.core.dagger.BBLComponent;
import com.nilhcem.bblfr.core.dagger.BBLModule;
import com.nilhcem.bblfr.core.dagger.DaggerBBLComponent;
import com.nilhcem.bblfr.core.db.Database;
import com.nilhcem.bblfr.core.log.ReleaseTree;

import timber.log.Timber;

public class BBLApplication extends Application {

    private BBLComponent mComponent;

    public static BBLApplication get(Context context) {
        return (BBLApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initGraph();
        Database.init(this);
    }

    public BBLComponent component() {
        return mComponent;
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private void initGraph() {
        mComponent = DaggerBBLComponent.builder()
                .bBLModule(new BBLModule(this))
                .build();
    }
}
