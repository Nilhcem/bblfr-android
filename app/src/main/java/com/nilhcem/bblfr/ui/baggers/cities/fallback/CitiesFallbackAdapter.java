package com.nilhcem.bblfr.ui.baggers.cities.fallback;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseHeaderAdapter;
import com.nilhcem.bblfr.ui.BaseRecyclerViewHolder;

public class CitiesFallbackAdapter extends BaseHeaderAdapter<Void, City, BaseRecyclerViewHolder<Void>, BaseRecyclerViewHolder<City>>
        implements View.OnClickListener {

    public interface OnCitySelectedListener {
        void onCitySelected(City selectedCity);
    }

    private final OnCitySelectedListener mListener;

    public CitiesFallbackAdapter(@NonNull OnCitySelectedListener listener) {
        mListener = listener;
    }

    @Override
    protected BaseRecyclerViewHolder<City> onCreateItemView(ViewGroup parent) {
        return new CitiesFallbackEntryView(parent);
    }

    @Override
    protected void onBindItemView(BaseRecyclerViewHolder<City> view, City item) {
        super.onBindItemView(view, item);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CitiesFallbackEntryView view = (CitiesFallbackEntryView) v.getTag();
        City city = view.getCity();
        mListener.onCitySelected(city);
    }
}
