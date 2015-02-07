package com.nilhcem.bblfr.model.baggers;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("baggers_cities")
public class BaggerCity extends Model {

    @Column("bagger_id")
    public Bagger bagger;

    @Column("city_id")
    public City city;

    public BaggerCity(Bagger bagger, City city) {
        this.bagger = bagger;
        this.city = city;
    }
}
