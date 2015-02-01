package com.nilhcem.bblfr.model.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nilhcem.bblfr.model.Bagger;
import com.nilhcem.bblfr.model.BaggerCity;
import com.nilhcem.bblfr.model.BaggerTag;
import com.nilhcem.bblfr.model.City;
import com.nilhcem.bblfr.model.JsonData;
import com.nilhcem.bblfr.model.Session;
import com.nilhcem.bblfr.model.Tag;
import com.nilhcem.bblfr.model.Website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ollie.Ollie;

public class JsonToDatabaseDao {

    @Inject
    public JsonToDatabaseDao() {
    }

    public void saveJsonToDatabase(JsonData jsonData) {
        SQLiteDatabase database = Ollie.getDatabase();
        database.beginTransaction();
        try {
            Map<String, City> citiesMap = saveCities(jsonData.getCities());
            Map<String, Tag> tagsMap = saveTags(jsonData.getBaggers());
            saveBaggers(jsonData.getBaggers(), citiesMap, tagsMap);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private Map<String, City> saveCities(List<City> cities) {
        Map<String, City> map = new HashMap<>();
        for (City city : cities) {
            city.save();
            map.put(city.getName(), city);
        }
        return map;
    }

    private Map<String, Tag> saveTags(List<Bagger> baggers) {
        Map<String, Tag> map = new HashMap<>();
        for (Bagger bagger : baggers) {
            List<String> tagNames = bagger.getTags();
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
            saveBaggerWebsites(bagger, bagger.getWebsites());
            saveBaggerSessions(bagger, bagger.getSessions());
            saveBaggerCities(bagger, bagger.getCities(), citiesMap);
            saveBaggerTags(bagger, bagger.getTags(), tagsMap);
        }
    }

    private void saveBaggerWebsites(Bagger bagger, List<Website> websites) {
        if (websites != null) {
            for (Website website : websites) {
                website.setBagger(bagger);
                website.save();
            }
        }
    }

    private void saveBaggerSessions(Bagger bagger, List<Session> sessions) {
        if (sessions != null) {
            for (Session session : sessions) {
                session.setBagger(bagger);
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
