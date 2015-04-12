package com.nilhcem.bblfr;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.ui.SecondActivity;
import com.nilhcem.bblfr.ui.baggers.cities.fallback.CitiesFallbackActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListEntryView;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListHeaderView;
import com.nilhcem.bblfr.ui.baggers.cities.CitiesMapActivity;
import com.nilhcem.bblfr.ui.locations.LocationsMapActivity;
import com.nilhcem.bblfr.ui.splashscreen.SplashscreenActivity;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        injects = {
                LocationProvider.class,
                SplashscreenActivity.class,
                CitiesMapActivity.class,
                CitiesFallbackActivity.class,
                BaggersListActivity.class,
                BaggersListHeaderView.class,
                BaggersListEntryView.class,
                LocationsMapActivity.class,
                SecondActivity.class
        }
)
public class BBLModule {

    private static final int DISK_CACHE_SIZE = 52_428_800; // 50MB (50 * 1024 * 1024)

    private final BBLApplication mApp;

    public BBLModule(BBLApplication app) {
        mApp = app;
    }

    @Provides @Singleton Preferences providePreferences() {
        return new Preferences(mApp);
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(mApp.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);
        return client;
    }

    @Provides @Singleton ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Provides @Singleton Picasso providePicasso(OkHttpClient client) {
        return new Picasso.Builder(mApp)
                .downloader(new OkHttpDownloader(client))
                .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
                .build();
    }
}
