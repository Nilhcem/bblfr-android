package com.nilhcem.bblfr.ui.locations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.utils.StringUtils;
import com.nilhcem.bblfr.model.locations.Audience;
import com.nilhcem.bblfr.model.locations.Interest;
import com.nilhcem.bblfr.model.locations.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LocationsInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    @InjectView(R.id.info_window_name) TextView mName;
    @InjectView(R.id.info_window_address) TextView mAddress;
    @InjectView(R.id.info_window_contact) TextView mContact;
    @InjectView(R.id.info_window_audience) TextView mAudience;
    @InjectView(R.id.info_window_interests) TextView mInterests;

    private View mLayout;
    private Context mContext;
    private Map<Marker, Location> mLocations;

    public LocationsInfoWindowAdapter(Context context, Map<Marker, Location> markerLocations) {
        mContext = context;
        mLocations = markerLocations;
        mLayout = LayoutInflater.from(context).inflate(R.layout.locations_map_info_window, null);
        ButterKnife.inject(this, mLayout);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // Use default InfoWindow frame.
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Location data = getLocationForMarker(marker);

        setHtmlTextIfNotEmpty(mName, mContext.getString(R.string.location_name, data.name));
        setHtmlTextIfNotEmpty(mAddress, data.address);
        setHtmlTextIfNotEmpty(mContact, mContext.getString(R.string.location_contact, data.contact));
        setHtmlTextIfNotEmpty(mAudience, formatAudienceText(data.audience));
        setHtmlTextIfNotEmpty(mInterests, formatInterestsText(data.interests));

        return mLayout;
    }

    private void setHtmlTextIfNotEmpty(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(Html.fromHtml(text));
        }
    }

    private String formatAudienceText(@NonNull Audience audience) {
        String audienceStr = StringUtils.appendOptional(audience.profiles, audience.number, StringUtils.HTML_LINE_SEPARATOR);
        return mContext.getString(R.string.location_audience, audienceStr);
    }

    private String formatInterestsText(@NonNull List<Interest> interests) {
        List<String> interestsNames = new ArrayList<>();
        for (Interest interest : interests) {
            interestsNames.add(interest.name);
        }
        String interestsStr = StringUtils.strJoin(interestsNames, StringUtils.COMMA_SEPARATOR);
        return mContext.getString(R.string.location_interests, interestsStr);
    }

    public Location getLocationForMarker(@NonNull Marker marker) {
        return mLocations.get(marker);
    }
}
