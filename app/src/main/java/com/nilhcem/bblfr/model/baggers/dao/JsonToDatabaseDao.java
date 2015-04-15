package com.nilhcem.bblfr.model.baggers.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.BaggerCity;
import com.nilhcem.bblfr.model.baggers.BaggerTag;
import com.nilhcem.bblfr.model.baggers.BaggersData;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.model.baggers.Website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ollie.Ollie;

public class JsonToDatabaseDao extends com.nilhcem.bblfr.model.JsonToDatabaseDao<BaggersData> {

    @Inject
    public JsonToDatabaseDao() {
    }

    @Override
    public void saveJsonToDatabase(BaggersData data) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            deleteExistingData(database);

            Map<String, City> citiesMap = saveCities(data.cities);
            Map<String, Tag> tagsMap = saveTags(data.baggers);
            saveBaggers(data.baggers, citiesMap, tagsMap);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void deleteExistingData(SQLiteDatabase database) {
        database.delete("baggers_tags", null, null);
        database.delete("baggers_cities", null, null);
        database.delete("sessions", null, null);
        database.delete("websites", null, null);
        database.delete("baggers", null, null);
        database.delete("tags", null, null);
        database.delete("cities", null, null);
    }

    private Map<String, City> saveCities(List<City> cities) {
        Map<String, City> map = new HashMap<>();
        for (City city : cities) {
            city.save();
            map.put(city.name, city);
        }
        return map;
    }

    private Map<String, Tag> saveTags(List<Bagger> baggers) {
        Map<String, Tag> map = new HashMap<>();
        for (Bagger bagger : baggers) {
            List<String> tagNames = bagger.tags;
            if (tagNames != null) {
                for (String tagName : tagNames) {
                    Tag tag = map.get(tagName);
                    if (tag == null) {
                        tag = new Tag(tagName);
                        tag.save();
                        map.put(tagName, tag);
                    }
                }
            }
        }
        return map;
    }

    private void saveBaggers(List<Bagger> baggers, Map<String, City> citiesMap, Map<String, Tag> tagsMap) {
        for (Bagger bagger : baggers) {
            bagger.save();
            saveBaggerWebsites(bagger, bagger.websites);
            saveBaggerSessions(bagger, bagger.sessions);
            saveBaggerCities(bagger, bagger.cities, citiesMap);
            saveBaggerTags(bagger, bagger.tags, tagsMap);
        }
    }

    private void saveBaggerWebsites(Bagger bagger, List<Website> websites) {
        if (websites != null) {
            for (Website website : websites) {
                website.bagger = bagger;
                website.save();
            }
        }
    }

    private void saveBaggerSessions(Bagger bagger, List<Session> sessions) {
        if (sessions != null) {
            for (Session session : sessions) {
                session.bagger = bagger;
                session.save();
            }
        }
    }

    private void saveBaggerCities(Bagger bagger, List<String> cities, Map<String, City> citiesMap) {
        if (cities != null) {
            for (String cityName : cities) {
                City city = citiesMap.get(cityName);
                if (city != null) {
                    BaggerCity bc = new BaggerCity(bagger, city);
                    bc.save();
                }
            }
        }
    }

    private void saveBaggerTags(Bagger bagger, List<String> tags, Map<String, Tag> tagsMap) {
        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagsMap.get(tagName);
                if (tag != null) {
                    BaggerTag bt = new BaggerTag(bagger, tag);
                    bt.save();
                }
            }
        }
    }
}
