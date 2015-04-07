package com.nilhcem.bblfr.jobs.baggers;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.model.baggers.dao.BaggersDao;
import com.nilhcem.bblfr.model.baggers.dao.CitiesDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BaggersService {

    @Inject CitiesDao mCitiesDao;
    @Inject BaggersDao mBaggersDao;

    public Observable<List<Bagger>> getBaggers(Long cityId) {
        return Observable.defer(() -> {
            List<Bagger> baggers = mBaggersDao.getBaggers(cityId);
            return Observable.just(baggers);
        });
    }

    public Observable<List<City>> getBaggersCities() {
        return Observable.defer(() -> {
            List<City> cities = mCitiesDao.getCities();
            return Observable.just(cities);
        });
    }
}
