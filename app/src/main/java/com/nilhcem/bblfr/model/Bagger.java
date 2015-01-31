package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bagger {

    @JsonProperty(value = "name") String mName;
    @JsonProperty(value = "bio") String mBio;
    @JsonProperty(value = "picture") String mPictureUrl;
    @JsonProperty(value = "websites") List<Website> mWebsites;
    @JsonProperty(value = "twitter") String mTwitter;
    @JsonProperty(value = "contact") String mContact;
    @JsonProperty(value = "mail") String mEmail;
    @JsonProperty(value = "location") String mLocation;
    @JsonProperty(value = "sessions") List<Session> mSessions;
    @JsonProperty(value = "tags") List<String> mTags;
    @JsonProperty(value = "cities") List<String> mCities;

    public String getName() {
        return mName;
    }

    public String getBio() {
        return mBio;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public List<Website> getWebsites() {
        return mWebsites;
    }

    public String getTwitter() {
        return mTwitter;
    }

    public String getContact() {
        return mContact;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getLocation() {
        return mLocation;
    }

    public List<Session> getSessions() {
        return mSessions;
    }

    public List<String> getTags() {
        return mTags;
    }

    public List<String> getCities() {
        return mCities;
    }
}
