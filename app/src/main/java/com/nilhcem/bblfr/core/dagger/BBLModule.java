package com.nilhcem.bblfr.core.dagger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.BuildConfig;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import timber.log.Timber;

@Module
public class BBLModule {

    private static final int DISK_CACHE_SIZE = 52_428_800; // 50MB (50 * 1024 * 1024)

    private final BBLApplication mApp;

    public BBLModule(BBLApplication app) {
        mApp = app;
    }

    @Provides @Singleton LocationProvider provideLocationProvider() {
        return new LocationProvider();
    }

    @Provides @Singleton Preferences providePreferences() {
        return new Preferences(mApp);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(mApp.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return new OkHttpClient.Builder().cache(cache).build();
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Provides @Singleton Picasso providePicasso(OkHttpClient client) {
        Picasso.Builder builder = new Picasso.Builder(mApp).downloader(new OkHttp3Downloader(client));
        if (BuildConfig.DEBUG) {
            builder.listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri));
        }
        return builder.build();
    }
}
