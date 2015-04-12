package com.nilhcem.bblfr.model.locations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("audiences")
public class Audience extends Model {

    @JsonProperty
    @Column("profiles")
    public String profiles;

    @JsonProperty
    @Column("number")
    public String number;

    @JsonIgnore
    @Column("location_id")
    public Location location;
}
