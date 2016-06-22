package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.map.LocationProvider;
import com.nilhcem.bblfr.core.utils.NetworkUtils;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class BaseMapActivity extends NavigationDrawerActivity {

    protected MapFragment mMapFragment;

    @Inject protected LocationProvider mLocationProvider;

    @BindView(R.id.maps_error_container) ViewGroup mErrorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.locations_map_container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onReloadMapsButtonClicked();
    }

    @OnClick(R.id.maps_error_button)
    void onReloadMapsButtonClicked() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            mErrorContainer.setVisibility(View.GONE);
            onMapFinishedLoading();
            loadMap();
        } else {
            mErrorContainer.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void loadMap();

    protected void onMapFinishedLoading() {
        mErrorContainer.setVisibility(View.GONE);
    }
}
