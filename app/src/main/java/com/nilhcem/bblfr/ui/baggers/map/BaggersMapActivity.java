package com.nilhcem.bblfr.ui.baggers.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.MapUtils;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseMapActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BaggersMapActivity extends BaseMapActivity {

    private static final float DEFAULT_ZOOM = 9f;

    @Inject BaggersService mBaggersService;

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, BaggersMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.baggers_map_toolbar_title);

        mSubscription = AppObservable.bindActivity(this,
                Observable.zip(
                        mBaggersService.getBaggersCities(),
                        MapUtils.getGoogleMapObservable(mMapFragment),
                        Pair::create))
                .subscribeOn(Schedulers.io())
                .subscribe(pair -> onCitiesLoaded(pair.first, pair.second));
    }

    private void onCitiesLoaded(List<City> cities, GoogleMap map) {
        Timber.d("BBL Cities loaded from DB");

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

        // Open a new activity when selecting a city
        map.setOnMarkerClickListener(marker -> {
            BaggersListActivity.launch(this, markerCities.get(marker));
            return true;
        });

        // Zoom the map indicator to user's current position
        MapUtils.moveToCurrentLocation(map, markers, mLocationProvider.getLastKnownLocation(), DEFAULT_ZOOM);
    }
}
