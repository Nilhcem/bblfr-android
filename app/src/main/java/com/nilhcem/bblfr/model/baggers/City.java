package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("cities")
public class City extends Model {

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty(value = "ville_img")
    @Column("picture")
    public String picture;

    @JsonProperty
    @Column("lat")
    public Double lat;

    @JsonProperty
    @Column("lng")
    public Double lng;
}
