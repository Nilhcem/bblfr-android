package com.nilhcem.bblfr.model.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.Bagger;
import com.nilhcem.bblfr.model.BaggerCity;
import com.nilhcem.bblfr.model.BaggerTag;
import com.nilhcem.bblfr.model.City;
import com.nilhcem.bblfr.model.Session;
import com.nilhcem.bblfr.model.Tag;
import com.nilhcem.bblfr.model.Website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.Ollie;

@Singleton
public class BaggersDao {

    @Inject CitiesDao mCitiesDao;

    public void save(List<Bagger> baggers) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();

        Map<String, Tag> tagsMap = new HashMap<>();

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

                List<String> tagNames = bagger.getTags();
                if (tagNames != null) {
                    for (String tagName : tagNames) {
                        Tag tag = tagsMap.get(tagName);
                        if (tag == null) {
                            tag = new Tag(tagName);
                            tag.save();
                            tagsMap.put(tagName, tag);
                        }
                        BaggerTag baggerTag = new BaggerTag(bagger, tag);
                        baggerTag.save();
                    }
                }

                Map<String, City> citiesMap = mCitiesDao.getAllInMap();
                for (String cityName : bagger.getCities()) {
                    City city = citiesMap.get(cityName);
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
