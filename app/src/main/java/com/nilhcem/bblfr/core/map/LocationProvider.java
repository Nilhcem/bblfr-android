package com.nilhcem.bblfr.core.map;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.nilhcem.bblfr.core.utils.AppUtils;

public class LocationProvider {

    private Location mLastLocation;

    public synchronized void initSync(Context context) {
        if (AppUtils.hasGooglePlayServices(context) && AppUtils.isGeolocAllowed(context)) {
            GoogleApiClient client = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .build();

            ConnectionResult connectionResult = client.blockingConnect();
            if (connectionResult.getErrorCode() == ConnectionResult.SUCCESS) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
            }

            if (client.isConnected()) {
                client.disconnect();
            }
        }
    }

    public synchronized Location getLastKnownLocation() {
        return mLastLocation;
    }
}
