package com.nilhcem.bblfr.ui.locations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.map.MapUtils;
import com.nilhcem.bblfr.jobs.locations.LocationsService;
import com.nilhcem.bblfr.model.locations.Location;
import com.nilhcem.bblfr.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LocationsMapActivity extends BaseActivity {

    @Inject LocationProvider mLocationProvider;
    @Inject LocationsService mLocationsService;

    private Subscription mSubscription;

    public LocationsMapActivity() {
        super(R.layout.locations_map_activity);
    }

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, LocationsMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.location_toolbar_title);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.locations_map);

        mSubscription = AppObservable.bindActivity(this,
                Observable.zip(
                        mLocationsService.getLocations(),
                        MapUtils.getGoogleMapObservable(mapFragment),
                        Pair::create))
                .subscribeOn(Schedulers.io())
                .subscribe(pair -> onHostsLoaded(pair.first, pair.second));
    }

    @Override
    protected void onStop() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.onStop();
    }

    private void onHostsLoaded(List<Location> locations, GoogleMap map) {
        Timber.d("BBL Hosts loaded from DB");

        // Set the locations in the map.
        List<Marker> markers = new ArrayList<>();
        Map<Marker, Location> markerLocations = new HashMap<>();
        for (Location location : locations) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(MapUtils.gpsToLatLng(location.gps))
                    .title(location.name)
                    .snippet(location.address)
                    .icon(BitmapDescriptorFactory.defaultMarker(25.0f))
            );
            markers.add(marker);
            markerLocations.put(marker, location);
        }

        // Custom infowindow.
        map.setInfoWindowAdapter(new LocationsInfoWindowAdapter(this, markerLocations));

        // Open the company's website when clicking on the infowindow.
        map.setOnInfoWindowClickListener(marker -> {
            String website = markerLocations.get(marker).website;
            if (!TextUtils.isEmpty(website)) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                startActivity(browserIntent);
            }
        });

        // Zoom the map indicator to user's current position
        MapUtils.moveToCurrentLocation(map, markers, mLocationProvider.getLastKnownLocation());
    }
}
