package com.nilhcem.bblfr.model.baggers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("sessions")
public class Session extends Model {

    @JsonProperty
    @Column("title")
    public String title;

    @JsonProperty
    @Column("summary")
    public String summary;

    @JsonIgnore
    @Column("bagger_id")
    public Bagger bagger;
}
