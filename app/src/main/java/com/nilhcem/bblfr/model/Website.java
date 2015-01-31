package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("websites")
public class Website extends Model {

    @JsonProperty(value = "title")
    @Column("title")
    public String mTitle;

    @JsonProperty(value = "href")
    @Column("href")
    public String mLink;

    @JsonIgnore
    @Column("bagger")
    public Bagger mBagger;

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public void setBagger(Bagger bagger) {
        mBagger = bagger;
    }
}
