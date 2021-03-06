package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("websites")
public class Website extends Model {

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty
    @Column("url")
    public String url;

    @JsonIgnore
    @Column("bagger_id")
    public Bagger bagger;
}
