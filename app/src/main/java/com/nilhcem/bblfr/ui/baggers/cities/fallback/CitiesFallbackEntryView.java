package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

import butterknife.InjectView;

public class CitiesFallbackEntryView extends BaseRecyclerViewHolder<City> {

    @InjectView(R.id.cities_fallback_item_name) TextView mName;

    public CitiesFallbackEntryView(ViewGroup parent) {
        super(parent, R.layout.cities_fallback_item);
    }

    @Override
    public void bindData(City data) {
        super.bindData(data);
        mName.setText(data.name);
    }
}
