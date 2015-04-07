package com.nilhcem.bblfr.ui;

import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.LocationProvider;

import javax.inject.Inject;

public abstract class BaseMapActivity extends BaseActivity {

    protected MapFragment mMapFragment;

    @Inject protected LocationProvider mLocationProvider;

    protected BaseMapActivity() {
        super(R.layout.map_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.locations_map);
    }
}
