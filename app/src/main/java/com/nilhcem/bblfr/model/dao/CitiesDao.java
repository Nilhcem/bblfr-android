package com.nilhcem.bblfr.model.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.City;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.Ollie;
import ollie.query.Select;

@Singleton
public class CitiesDao {

    @Inject
    public CitiesDao() {
    }

    public boolean hasCities() {
        return Select.columns("COUNT(*)").from(City.class).fetchValue(Integer.class) != 0;
    }

    public City getByName(String name) {
        return Select.from(City.class).where("name = ?", name).fetchSingle();
    }

    public List<City> getAll() {
        return Select.from(City.class).fetch();
    }

    public void save(List<City> cities) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            for (City city : cities) {
                city.save();
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
