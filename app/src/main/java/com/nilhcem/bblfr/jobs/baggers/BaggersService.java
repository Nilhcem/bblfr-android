package com.nilhcem.bblfr.jobs.baggers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.Tag;
import com.nilhcem.bblfr.model.baggers.dao.BaggersDao;
import com.nilhcem.bblfr.model.baggers.dao.CitiesDao;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListEntry;
import com.nilhcem.bblfr.ui.baggers.list.filter.TagsListEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BaggersService {

    private final CitiesDao mCitiesDao;
    private final BaggersDao mBaggersDao;

    @Inject
    public BaggersService(CitiesDao citiesDao, BaggersDao baggersDao) {
        mCitiesDao = citiesDao;
        mBaggersDao = baggersDao;
    }

    public Observable<List<BaggersListEntry>> getBaggers(@NonNull Context context, @NonNull Long cityId, @NonNull List<String> selectedTags) {
        return Observable.create(subscriber -> {
            List<BaggersListEntry> entries = new ArrayList<>();
            List<Bagger> baggers = mBaggersDao.getBaggers(cityId, selectedTags);

            for (Bagger bagger : baggers) {
                entries.add(new BaggersListEntry(context, bagger));
            }
            subscriber.onNext(entries);
            subscriber.onCompleted();
        });
    }

    public Observable<List<TagsListEntry>> getSessionsTags(@NonNull Long cityId) {
        return Observable.create(subscriber -> {
            List<TagsListEntry> entries = new ArrayList<>();
            List<Tag> tags = mBaggersDao.getSessionsTags(cityId);

            for (Tag tag : tags) {
                entries.add(new TagsListEntry(tag));
            }
            subscriber.onNext(entries);
            subscriber.onCompleted();
        });
    }

    public Observable<List<City>> getBaggersCities() {
        return Observable.create(subscriber -> {
            subscriber.onNext(mCitiesDao.getCities());
            subscriber.onCompleted();
        });
    }
}
