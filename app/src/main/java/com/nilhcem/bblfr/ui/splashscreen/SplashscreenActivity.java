package com.nilhcem.bblfr.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.splashscreen.ImportService;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.SecondActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SplashscreenActivity extends BaseActivity {

    @Inject ImportService mImportService;

    @InjectView(R.id.logo_container) ViewGroup mLogoContainer;
    @InjectView(R.id.splash_subtitle) TextView mSubtitle;

    private Subscription mSubscription;

    public SplashscreenActivity() {
        super(R.layout.splashscreen_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubtitle.setText(Html.fromHtml(getString(R.string.splash_subtitle)));
        mSubscription = AppObservable.bindActivity(this, mImportService.importData())
                .subscribeOn(Schedulers.io())
                .subscribe(success -> {
                    Timber.d("Import successful: %b - %b", success.first, success.second);
                    startActivity(new Intent(this, SecondActivity.class));

                });
        animateLogo();
    }

    @Override
    protected void onStop() {
        mSubscription.unsubscribe();
        super.onStop();
    }

    private void animateLogo() {
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.splashscreen_logo);
        mLogoContainer.clearAnimation();
        mLogoContainer.setAnimation(localAnimation);
        mLogoContainer.startAnimation(localAnimation);
    }
}
