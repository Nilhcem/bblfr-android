package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("contacts")
public class Contact extends Model {

    @JsonProperty
    @Column("twitter")
    public String twitter;

    @JsonProperty
    @Column("mail")
    public String mail;

    @JsonIgnore
    @Column("bagger_id")
    public Bagger bagger;
}
