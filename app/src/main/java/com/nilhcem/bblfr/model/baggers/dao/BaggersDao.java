package com.nilhcem.bblfr.model.baggers.dao;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.BaggerCity;
import com.nilhcem.bblfr.model.baggers.BaggerTag;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.model.baggers.Website;
import com.nilhcem.bblfr.model.locations.Audience;
import com.nilhcem.bblfr.model.locations.Interest;
import com.nilhcem.bblfr.model.locations.Location;
import com.nilhcem.bblfr.model.locations.LocationInterest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ollie.query.Select;

@Singleton
public class BaggersDao {

    @Inject
    public BaggersDao() {
    }

    public List<Bagger> getBaggers() {
        List<Bagger> baggers = Select.from(Bagger.class).fetch();
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
}
