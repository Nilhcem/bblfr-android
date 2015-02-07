package com.nilhcem.bblfr.model.locations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationsData {

    @JsonProperty
    public List<Location> locations;
}
