package com.nilhcem.bblfr.jobs.baggers;

import android.content.Context;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.dao.BaggersDao;
import com.nilhcem.bblfr.model.baggers.dao.CitiesDao;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BaggersService {

    @Inject CitiesDao mCitiesDao;
    @Inject BaggersDao mBaggersDao;

    public Observable<List<BaggersListEntry>> getBaggers(Context context, Long cityId) {
        return Observable.defer(() -> {
            List<BaggersListEntry> entries = new ArrayList<>();
            List<Bagger> baggers = mBaggersDao.getBaggers(cityId);

            for (Bagger bagger : baggers) {
                entries.add(new BaggersListEntry(context, bagger));
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
