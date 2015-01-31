package com.nilhcem.bblfr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("sessions")
public class Session extends Model {

    @JsonProperty(value = "title")
    @Column("title")
    public String mTitle;

    @JsonProperty(value = "summary")
    @Column("summary")
    public String mSummary;

    @JsonIgnore
    @Column("bagger")
    public Bagger mBagger;

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setBagger(Bagger bagger) {
        mBagger = bagger;
    }
}
