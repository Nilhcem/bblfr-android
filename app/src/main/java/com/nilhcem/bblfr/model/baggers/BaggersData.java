package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BaggersData {

    @JsonProperty
    public List<Bagger> baggers;

    @JsonProperty
    public List<City> cities;
}
