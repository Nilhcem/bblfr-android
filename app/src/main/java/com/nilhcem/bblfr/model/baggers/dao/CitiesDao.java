package com.nilhcem.bblfr.model.baggers.dao;

import com.nilhcem.bblfr.model.baggers.City;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.query.Select;

@Singleton
public class CitiesDao {

    @Inject
    public CitiesDao() {
    }

    public List<City> getCities() {
        return Select.from(City.class).orderBy("name ASC").fetch();
    }
}
