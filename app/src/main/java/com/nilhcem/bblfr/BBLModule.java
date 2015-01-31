package com.nilhcem.bblfr;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.logging.JobQueueLogger;
import com.nilhcem.bblfr.jobs.baggers.GetBaggersJob;
import com.nilhcem.bblfr.ui.MainActivity;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

@Module(
        injects = {
                MainActivity.class,
                GetBaggersJob.class
        }
)
public class BBLModule {

    private static final int DISK_CACHE_SIZE = 52_428_800; // 50MB (50 * 1024 * 1024)

    private final BBLApplication mApp;

    public BBLModule(BBLApplication app) {
        mApp = app;
    }

    @Provides @Singleton EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(mApp.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            Timber.e(e, "Unable to install disk cache.");
        }
        return client;
    }

    @Provides @Singleton JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(mApp)
                .customLogger(new JobQueueLogger())
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        return new JobManager(mApp, configuration);
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }
}
