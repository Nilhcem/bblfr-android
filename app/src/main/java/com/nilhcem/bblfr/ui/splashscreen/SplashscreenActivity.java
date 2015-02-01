package com.nilhcem.bblfr.ui.splashscreen;

import android.os.Bundle;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.splashscreen.ImportBaggersService;
import com.nilhcem.bblfr.ui.BaseActivity;

import javax.inject.Inject;

import rx.android.app.AppObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SplashscreenActivity extends BaseActivity implements Action1<Boolean> {

    @Inject ImportBaggersService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);

        AppObservable.bindActivity(this, mService.importBaggers())
                .subscribeOn(Schedulers.io())
                .subscribe(this);
    }

    @Override
    public void call(Boolean success) {
        Timber.d("Import successful: " + success);
    }
}
