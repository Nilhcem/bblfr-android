package com.nilhcem.bblfr.model.locations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("locations")
public class Location extends Model {

    @JsonProperty
    @Column("alias")
    public String pseudo;

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty
    @Column("address")
    public String address;

    @JsonProperty
    @Column("gps")
    public String gps;

    @JsonProperty
    @Column("website")
    public String website;

    @JsonProperty
    @Column("contact")
    public String contact;

    @JsonProperty
    @Column("picture")
    public String picture;

    @JsonProperty
    public List<Interest> interests;

    @JsonProperty
    public Audience audience;
}
