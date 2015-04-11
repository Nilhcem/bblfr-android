package com.nilhcem.bblfr.model.baggers.dao;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.BaggerCity;
import com.nilhcem.bblfr.model.baggers.BaggerTag;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.model.baggers.Website;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.query.Select;
import ollie.util.QueryUtils;

@Singleton
public class BaggersDao {

    @Inject
    public BaggersDao() {
    }

    public List<Bagger> getBaggers(Long cityId) {
        List<Bagger> baggers;

        if (cityId == null) {
            baggers = Select.from(Bagger.class).fetch();
        } else {
            baggers = Select.from(Bagger.class).innerJoin(BaggerCity.class).on("baggers._id=baggers_cities.bagger_id").where("baggers_cities.city_id=?", cityId).fetch();
        }

        for (Bagger bagger : baggers) {
            fillBaggerData(bagger);
        }
        return baggers;
    }

    private void fillBaggerData(Bagger bagger) {
        // cities
        List<City> cities = Select.from(City.class).innerJoin(BaggerCity.class).on("cities._id=baggers_cities.city_id").where("baggers_cities.bagger_id=?", bagger.id).fetch();
        bagger.cities = new ArrayList<>();
        for (City city : cities) {
            bagger.cities.add(city.name);
        }

        //sessions
        bagger.sessions = Select.from(Session.class).where("bagger_id=?", bagger.id).fetch();

        //tags
        List<Tag> tags = Select.from(Tag.class).innerJoin(BaggerTag.class).on("tags._id=baggers_tags.tag_id").where("baggers_tags.bagger_id=?", bagger.id).fetch();
        bagger.tags = new ArrayList<>();
        for (Tag tag : tags) {
            bagger.tags.add(tag.name);
        }

        //websites
        bagger.websites = Select.from(Website.class).where("bagger_id=?", bagger.id).fetch();
    }

    /**
     * Gets all the baggers tags for a specified city, sorted by tags popularity.
     */
    public List<Tag> getBaggersTags(Long cityId) {
        // TODO: Draft - Make sure the query is correct and handle the nullable cityId.
        List<Tag> tags;
        tags = QueryUtils.rawQuery(Tag.class, "SELECT DISTINCT tags.* FROM baggers_tags INNER JOIN tags ON baggers_tags.tag_id = tags._id INNER JOIN baggers on baggers_tags.bagger_id = baggers._id INNER JOIN baggers_cities on baggers._id = baggers_cities.bagger_id WHERE baggers_cities.city_id = ? GROUP BY baggers_tags.tag_id ORDER BY COUNT(baggers_tags.tag_id) DESC", new String[] {Long.toString(cityId)});
        return tags;
    }
}
