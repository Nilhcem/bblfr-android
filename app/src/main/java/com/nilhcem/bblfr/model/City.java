package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {

    @JsonProperty(value = "name") String mName;
    @JsonProperty(value = "ville_img") String mCityPicture;
    @JsonProperty(value = "lat") double mLat;
    @JsonProperty(value = "lng") double mLong;

    public String getName() {
        return mName;
    }

    public String getCityPicture() {
        return mCityPicture;
    }

    public double getLat() {
        return mLat;
    }

    public double getLong() {
        return mLong;
    }
}
