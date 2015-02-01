package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("cities")
public class City extends Model {

    @JsonProperty(value = "name")
    @Column("name")
    public String mName;

    @JsonProperty(value = "ville_img")
    @Column("picture")
    public String mPicture;

    @JsonProperty(value = "lat")
    @Column("lat")
    public Double mLat;

    @JsonProperty(value = "lng")
    @Column("lng")
    public Double mLng;

    public String getName() {
        return mName;
    }

    public String getPicture() {
        return mPicture;
    }

    public Double getLat() {
        return mLat;
    }

    public Double getLng() {
        return mLng;
    }
}
