package com.nilhcem.bblfr.jobs.baggers;

import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.dao.BaggersDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BaggersService {

    @Inject BaggersDao mDao;

    public Observable<List<Bagger>> getBaggers() {
        return Observable.defer(() -> Observable.just(mDao.getBaggers()));
    }
}
