package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {

    @JsonProperty(value = "title") String mTitle;
    @JsonProperty(value = "summary") String mSummary;

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }
}
