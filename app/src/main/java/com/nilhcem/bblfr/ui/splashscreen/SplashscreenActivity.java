package com.nilhcem.bblfr.ui.splashscreen;

import android.os.Bundle;
import android.text.Html;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.splashscreen.ImportService;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.baggers.search.BaggersSearchActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SplashscreenActivity extends BaseActivity {

    @Inject ImportService mImportService;

    @InjectView(R.id.splash_background) ViewGroup mBackground;
    @InjectView(R.id.splash_logo_container) ViewGroup mLogoContainer;
    @InjectView(R.id.splash_subtitle) TextView mSubtitle;
    @Optional @InjectView(R.id.splash_shimmer_container) ShimmerFrameLayout mShimmerContainer;

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
                    BaggersSearchActivity.launch(this);
                });
        animateLogo();
    }

    private void animateLogo() {
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.splashscreen_logo);
        mLogoContainer.clearAnimation();
        mLogoContainer.setAnimation(localAnimation);
        mLogoContainer.startAnimation(localAnimation);

        if (mShimmerContainer != null) {
            mShimmerContainer.setDuration(900);
            mShimmerContainer.setBaseAlpha(0.65f);
            mShimmerContainer.setRepeatCount(1);
            mShimmerContainer.startShimmerAnimation();
        }
    }
}
