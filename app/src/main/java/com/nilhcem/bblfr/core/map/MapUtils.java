package com.nilhcem.bblfr.core.map;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import rx.Observable;

public class MapUtils {

    public static final float HUE_DEFAULT = 25.0f;

    private MapUtils() {
        throw new UnsupportedOperationException();
    }

    public static Observable<GoogleMap> getGoogleMapObservable(MapFragment fragment) {
        return Observable.defer(() -> {
            GoogleMap map = fragment.getMap();
            return Observable.just(map);
        });
    }

    public static LatLng gpsToLatLng(String gps) {
        String[] gpsSplit = gps.split(",");
        return new LatLng(Double.parseDouble(gpsSplit[0].trim()), Double.parseDouble(gpsSplit[1].trim()));
    }

    public static void moveToCurrentLocation(GoogleMap map, List<Marker> markers, Location lastLocation) {
        map.setMyLocationEnabled(true);

        Location location = lastLocation;
        if (location == null && markers != null && !markers.isEmpty()) {
            moveToMarkerBounds(map, markers);
        } else {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 11));
        }
    }

    private static void moveToMarkerBounds(GoogleMap map, List<Marker> markers) {
        // Calculate the bounds of all the markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        // Obtain a movement description object
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        // Move the map
        map.moveCamera(cu);
    }
}
