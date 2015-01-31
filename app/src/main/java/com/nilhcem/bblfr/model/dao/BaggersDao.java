package com.nilhcem.bblfr.model.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.Bagger;
import com.nilhcem.bblfr.model.BaggerCity;
import com.nilhcem.bblfr.model.City;
import com.nilhcem.bblfr.model.JsonData;
import com.nilhcem.bblfr.model.Session;
import com.nilhcem.bblfr.model.Website;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.Ollie;

@Singleton
public class BaggersDao {

    @Inject
    CitiesDao mCitiesDao;

    public JsonData getData() {
        // TODO
        return null;
    }

    public void save(List<Bagger> baggers) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            for (Bagger bagger : baggers) {
                bagger.save();

                List<Website> websites = bagger.getWebsites();
                if (websites != null) {
                    for (Website website : websites) {
                        website.setBagger(bagger);
                        website.save();
                    }
                }
                List<Session> sessions = bagger.getSessions();
                if (sessions != null) {
                    for (Session session : sessions) {
                        session.setBagger(bagger);
                        session.save();
                    }
                }

                for (String cityName : bagger.getCities()) {
                    City city = mCitiesDao.getByName(cityName);
                    if (city != null) {
                        BaggerCity bc = new BaggerCity(bagger, city);
                        bc.save();
                    }
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }
}
