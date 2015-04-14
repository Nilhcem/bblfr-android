package com.nilhcem.bblfr.ui;

import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivity;

import javax.inject.Inject;

public abstract class BaseMapActivity extends NavigationDrawerActivity {

    protected MapFragment mMapFragment;

    @Inject protected LocationProvider mLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.locations_map);
    }
}
