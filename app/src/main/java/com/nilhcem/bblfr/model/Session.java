package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
