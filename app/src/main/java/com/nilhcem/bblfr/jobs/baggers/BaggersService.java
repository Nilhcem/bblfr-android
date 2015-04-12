package com.nilhcem.bblfr.jobs.baggers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;

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

    @Inject CitiesDao mCitiesDao;
    @Inject BaggersDao mBaggersDao;

    public Observable<List<BaggersListEntry>> getBaggers(@NonNull Context context, @NonNull Long cityId, @NonNull List<String> selectedTags) {
        return Observable.defer(() -> {
            List<BaggersListEntry> entries = new ArrayList<>();
            List<Bagger> baggers = mBaggersDao.getBaggers(cityId, selectedTags);

            for (Bagger bagger : baggers) {
                entries.add(new BaggersListEntry(context, bagger));
            }
            return Observable.just(entries);
        });
    }

    public Observable<List<TagsListEntry>> getBaggersTags(@NonNull Long cityId) {
        return Observable.defer(() -> {
            List<TagsListEntry> entries = new ArrayList<>();
            List<Tag> tags = mBaggersDao.getBaggersTags(cityId);

            for (Tag tag : tags) {
                entries.add(new TagsListEntry(tag));
            }
            return Observable.just(entries);
        });
    }

    public Observable<List<City>> getBaggersCities() {
        return Observable.defer(() -> {
            List<City> cities = mCitiesDao.getCities();
            return Observable.just(cities);
        });
    }
}
