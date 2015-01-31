package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Website {

    @JsonProperty(value = "title") String mTitle;
    @JsonProperty(value = "href") String mLink;

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }
}
