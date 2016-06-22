package com.nilhcem.bblfr.ui.baggers.cities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.MapUtils;
import com.nilhcem.bblfr.core.utils.AppUtils;
import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseMapActivity;
import com.nilhcem.bblfr.ui.baggers.cities.fallback.CitiesFallbackActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CitiesMapActivity extends BaseMapActivity {

    private static final float DEFAULT_ZOOM = 10f;

    @Inject BaggersService mBaggersService;

    public static Intent createLaunchIntent(@NonNull Context context, boolean withNavigationDrawer) {
        Intent intent = new Intent(context,
                NetworkUtils.isNetworkAvailable(context) && AppUtils.hasGooglePlayServices(context)
                        ? CitiesMapActivity.class : CitiesFallbackActivity.class);
        intent.putExtra(EXTRA_DISABLE_DRAWER, !withNavigationDrawer);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BBLApplication.get(this).component().inject(this);
        getSupportActionBar().setTitle(R.string.baggers_map_toolbar_title);
    }

    @Override
    protected void loadMap() {
        mSubscription = mBaggersService.getBaggersCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities ->
                        mMapFragment.getMapAsync(googleMap -> onCitiesLoaded(cities, googleMap))
                );
    }

    private void onCitiesLoaded(List<City> cities, GoogleMap map) {
        Timber.d("BBL Cities loaded from DB");
        onMapFinishedLoading();

        // Set the locations in the map.
        List<Marker> markers = new ArrayList<>();
        Map<Marker, City> markerCities = new HashMap<>();
        for (City city : cities) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(city.lat, city.lng))
                    .title(city.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(MapUtils.HUE_DEFAULT))
            );
            markers.add(marker);
            markerCities.put(marker, city);
        }

        map.setOnMarkerClickListener(marker -> {
            Timber.d("City selected");
            City city = markerCities.get(marker);
            mPrefs.setFavoriteCity(city);
            startActivity(BaggersListActivity.createLaunchIntent(this, city));
            return true;
        });

        // Zoom the map indicator to user's current position
        MapUtils.moveToCurrentLocation(map, markers, mLocationProvider.getLastKnownLocation(), DEFAULT_ZOOM);
    }
}
