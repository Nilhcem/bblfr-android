package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Baggers {

    @JsonProperty(value = "baggers") List<Bagger> mBaggers;
    @JsonProperty(value = "cities") List<City> mCities;

    public List<Bagger> getBaggers() {
        return mBaggers;
    }

    public List<City> getCities() {
        return mCities;
    }
}
