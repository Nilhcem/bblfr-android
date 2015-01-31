package com.nilhcem.bblfr.model;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("baggers_cities")
public class BaggerCity extends Model {

    @Column("bagger")
    public Bagger mBagger;

    @Column("city")
    public City mCity;

    public BaggerCity() {
    }

    public BaggerCity(Bagger bagger, City city) {
        super();
        mBagger = bagger;
        mCity = city;
    }
}
