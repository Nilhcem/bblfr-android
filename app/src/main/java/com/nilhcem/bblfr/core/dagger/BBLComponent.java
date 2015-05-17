package com.nilhcem.bblfr.core.dagger;

import com.nilhcem.bblfr.ui.baggers.cities.CitiesMapActivity;
import com.nilhcem.bblfr.ui.baggers.cities.fallback.CitiesFallbackActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListEntryView;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListHeaderView;
import com.nilhcem.bblfr.ui.locations.LocationsMapActivity;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivity;
import com.nilhcem.bblfr.ui.settings.SettingsFragment;
import com.nilhcem.bblfr.ui.splashscreen.SplashscreenActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                BBLModule.class
        }
)
public interface BBLComponent {

    void inject(BaggersListActivity activity);

    void inject(BaggersListEntryView entryView);

    void inject(BaggersListHeaderView headerView);

    void inject(CitiesFallbackActivity activity);

    void inject(CitiesMapActivity activity);

    void inject(LocationsMapActivity activity);

    void inject(NavigationDrawerActivity activity);

    void inject(SettingsFragment fragment);

    void inject(SplashscreenActivity activity);
}
