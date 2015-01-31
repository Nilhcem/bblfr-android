package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("baggers")
public class Bagger extends Model {

    @JsonProperty(value = "name")
    @Column("name")
    public String mName;

    @JsonProperty(value = "bio")
    @Column("bio")
    public String mBio;

    @JsonProperty(value = "picture")
    @Column("picture")
    public String mPictureUrl;

    @JsonProperty(value = "websites")
    public List<Website> mWebsites;

    @JsonProperty(value = "twitter")
    @Column("twitter")
    public String mTwitter;

    @JsonProperty(value = "contact")
    @Column("contact")
    public String mContact;

    @JsonProperty(value = "mail")
    @Column("mail")
    public String mEmail;

    @JsonProperty(value = "location")
    @Column("location")
    public String mLocation;

    @JsonProperty(value = "sessions")
    public List<Session> mSessions;

    @JsonProperty(value = "tags")
//    @Column("tags")
    public List<String> mTags;

    @JsonProperty(value = "cities")
    public List<String> mCities;

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
