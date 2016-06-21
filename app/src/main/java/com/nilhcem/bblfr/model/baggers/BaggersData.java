package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class BaggersData {

    @JsonProperty
    public List<Bagger> speakers;

    @JsonProperty
    public Map<String, City> cities;
}
