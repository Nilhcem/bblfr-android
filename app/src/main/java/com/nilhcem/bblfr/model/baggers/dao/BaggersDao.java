package com.nilhcem.bblfr.model.baggers.dao;

import android.support.annotation.NonNull;

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

    public boolean hasData() {
        return Select.from(Bagger.class).fetchSingle() != null;
    }

    /**
     * Gets all the baggers matching specified tags (if any) for a given city.
     */
    public List<Bagger> getBaggers(@NonNull Long cityId, @NonNull List<String> tagsIds) {
        List<String> args = new ArrayList<>();
        args.add(Long.toString(cityId));
        int nbTags = tagsIds.size();

        StringBuilder sql = new StringBuilder("SELECT DISTINCT baggers.* FROM baggers INNER JOIN baggers_cities ON baggers._id=baggers_cities.bagger_id");
        if (nbTags > 0) {
            sql.append(" INNER JOIN baggers_tags ON baggers._id=baggers_tags.bagger_id");
        }

        sql.append(" WHERE baggers_cities.city_id=?");

        if (nbTags > 0) {
            sql.append(" AND baggers_tags.tag_id IN (");
            boolean addSeparator = false;

            for (String tagId : tagsIds) {
                if (addSeparator) {
                    sql.append(",");
                } else {
                    addSeparator = true;
                }

                sql.append("?");
                args.add(tagId);
            }
            sql.append(")");
        }

        sql.append(" ORDER BY RANDOM()");

        List<Bagger> baggers = QueryUtils.rawQuery(Bagger.class, sql.toString(), args.toArray(new String[args.size()]));

        for (Bagger bagger : baggers) {
            fillBaggerData(bagger);
        }
        return baggers;
    }

    private void fillBaggerData(@NonNull Bagger bagger) {
        // cities
        List<City> cities = Select.from(City.class).innerJoin(BaggerCity.class).on("cities._id=baggers_cities.city_id").where("baggers_cities.bagger_id=?", bagger.id).fetch();
        bagger.cities = new ArrayList<>();
        for (City city : cities) {
            bagger.cities.add(city.name);
        }

        // sessions
        bagger.sessions = Select.from(Session.class).where("bagger_id=?", bagger.id).fetch();

        // tags
        List<Tag> tags = Select.from(Tag.class).innerJoin(BaggerTag.class).on("tags._id=baggers_tags.tag_id").where("baggers_tags.bagger_id=?", bagger.id).fetch();
        bagger.tags = new ArrayList<>();
        for (Tag tag : tags) {
            bagger.tags.add(tag.name);
        }

        // websites
        bagger.websites = Select.from(Website.class).where("bagger_id=?", bagger.id).fetch();
    }

    /**
     * Gets all the baggers tags for a specified city, sorted by tags popularity.
     */
    public List<Tag> getBaggersTags(@NonNull Long cityId) {
        String sql = "SELECT DISTINCT tags.* FROM baggers_tags INNER JOIN tags ON baggers_tags.tag_id=tags._id INNER JOIN baggers on baggers_tags.bagger_id=baggers._id INNER JOIN baggers_cities on baggers._id=baggers_cities.bagger_id WHERE baggers_cities.city_id=? GROUP BY baggers_tags.tag_id ORDER BY COUNT(baggers_tags.tag_id) DESC";
        return QueryUtils.rawQuery(Tag.class, sql, new String[]{Long.toString(cityId)});
    }
}
