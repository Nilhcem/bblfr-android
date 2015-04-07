package com.nilhcem.bblfr.model.locations;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nilhcem.bblfr.BuildConfig;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("locations")
public class Location extends Model {

    @JsonProperty
    @Column("alias")
    public String pseudo;

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty
    @Column("address")
    public String address;

    @JsonProperty
    @Column("gps")
    public String gps;

    @JsonProperty
    @Column("website")
    public String website;

    @JsonProperty
    @Column("contact")
    public String contact;

    @JsonProperty
    @Column("picture")
    public String picture;

    @JsonProperty
    public List<Interest> interests;

    @JsonProperty
    public Audience audience;

    public String getPictureUrl() {
        String pictureUrl = picture;

        if (!TextUtils.isEmpty(pictureUrl)) {
            if (!pictureUrl.startsWith("http")) {
                pictureUrl = BuildConfig.WS_ENDPOINT + pictureUrl;
            }
        }
        return pictureUrl;
    }
}
