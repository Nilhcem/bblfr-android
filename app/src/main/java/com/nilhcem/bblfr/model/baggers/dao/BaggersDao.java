package com.nilhcem.bblfr.model.baggers.dao;

import android.support.annotation.NonNull;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.BaggerCity;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Contact;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.SessionTag;
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
            sql.append(" INNER JOIN sessions ON baggers._id=sessions.bagger_id INNER JOIN sessions_tags ON sessions._id=sessions_tags.session_id");
        }

        sql.append(" WHERE baggers_cities.city_id=?");

        if (nbTags > 0) {
            sql.append(" AND sessions_tags.tag_id IN (");
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
        // contacts
        bagger.contacts = Select.from(Contact.class).where("contacts.bagger_id=?", bagger.id).fetchSingle();

        // cities
        List<City> cities = Select.from(City.class).innerJoin(BaggerCity.class).on("cities._id=baggers_cities.city_id").where("baggers_cities.bagger_id=?", bagger.id).fetch();
        bagger.cities = new ArrayList<>();
        for (City city : cities) {
            bagger.cities.add(city.name);
        }

        // sessions
        bagger.sessions = Select.from(Session.class).where("bagger_id=?", bagger.id).fetch();

        // tags
        for (Session session : bagger.sessions) {
            List<Tag> tags = Select.from(Tag.class).innerJoin(SessionTag.class).on("tags._id=sessions_tags.tag_id").where("sessions_tags.session_id=?", session.id).fetch();
            session.tags = new ArrayList<>();
            for (Tag tag : tags) {
                session.tags.add(tag.name);
            }
        }

        // websites
        bagger.websites = Select.from(Website.class).where("bagger_id=?", bagger.id).fetch();
    }

    /**
     * Gets all the sessions tags for a specified city, sorted by tags popularity.
     */
    public List<Tag> getSessionsTags(@NonNull Long cityId) {
        String sql = "SELECT DISTINCT tags.* FROM sessions_tags INNER JOIN tags ON sessions_tags.tag_id=tags._id INNER JOIN sessions ON sessions_tags.session_id=sessions._id INNER JOIN baggers_cities ON sessions.bagger_id=baggers_cities.bagger_id WHERE baggers_cities.city_id=? GROUP BY sessions_tags.tag_id ORDER BY COUNT(sessions_tags.tag_id) DESC";
        return QueryUtils.rawQuery(Tag.class, sql, new String[]{Long.toString(cityId)});
    }
}
