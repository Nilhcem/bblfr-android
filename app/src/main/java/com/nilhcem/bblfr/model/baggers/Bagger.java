package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("baggers")
public class Bagger extends Model {

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty
    @Column("bio")
    public String bio;

    @JsonProperty
    @Column("picture")
    public String picture;

    @JsonProperty
    public List<Website> websites;

    @JsonProperty
    @Column("twitter")
    public String twitter;

    @JsonProperty
    @Column("contact")
    public String contact;

    @JsonProperty
    @Column("mail")
    public String mail;

    @JsonProperty
    @Column("location")
    public String location;

    @JsonProperty
    public List<Session> sessions;

    @JsonProperty
    public List<String> tags;

    @JsonProperty
    public List<String> cities;
}
