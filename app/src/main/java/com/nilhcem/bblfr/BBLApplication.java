package com.nilhcem.bblfr;

import android.app.Application;
import android.content.Context;

import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.db.Database;

import dagger.ObjectGraph;
import timber.log.Timber;

public class BBLApplication extends Application {

    private ObjectGraph mObjectGraph;

    public static BBLApplication get(Context context) {
        return (BBLApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initObjectGraph();
        initLocationProvider();
        Database.init(this);
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initObjectGraph() {
        mObjectGraph = ObjectGraph.create(new BBLModule(this));
    }

    private void initLocationProvider() {
        mObjectGraph.get(LocationProvider.class).init(this);
    }

    public void inject(Object target) {
        mObjectGraph.inject(target);
    }
}
