package com.nilhcem.bblfr.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.splashscreen.ImportBaggersService;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.SecondActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SplashscreenActivity extends BaseActivity {

    @Inject ImportBaggersService mService;

    @InjectView(R.id.splash_subtitle) TextView mSubtitle;

    private Subscription mSubscription;

    public SplashscreenActivity() {
        super(R.layout.splashscreen_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubtitle.setText(Html.fromHtml(getString(R.string.splash_subtitle)));
        mSubscription = AppObservable.bindActivity(this, mService.importBaggers())
                .subscribeOn(Schedulers.io())
                .subscribe(success -> {
                    Timber.d("Import successful: " + success);
                    startActivity(new Intent(this, SecondActivity.class));

                });
    }

    @Override
    protected void onStop() {
        mSubscription.unsubscribe();
        super.onStop();
    }
}
