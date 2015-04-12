package com.nilhcem.bblfr.ui.splashscreen;

import android.os.Bundle;
import android.text.Html;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.core.db.Database;
import com.nilhcem.bblfr.core.prefs.Preferences;
import com.nilhcem.bblfr.jobs.splashscreen.checkdata.CheckDataService;
import com.nilhcem.bblfr.jobs.splashscreen.importdata.ImportService;
import com.nilhcem.bblfr.model.baggers.City;
import com.nilhcem.bblfr.ui.BaseActivity;
import com.nilhcem.bblfr.ui.baggers.cities.CitiesMapActivity;
import com.nilhcem.bblfr.ui.baggers.list.BaggersListActivity;

import java.util.Date;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.nilhcem.bblfr.core.utils.NetworkUtils.hasGooglePlayServices;
import static com.nilhcem.bblfr.core.utils.NetworkUtils.isNetworkAvailable;

public class SplashscreenActivity extends BaseActivity {

    // Download data at most once a day (1 * 24 * 60 * 60 * 1000).
    private static final long DOWNLOAD_DATA_INTERVAL = 86_400_000l;

    @Inject Preferences mPrefs;
    @Inject ImportService mImportService;
    @Inject CheckDataService mCheckDataService;

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

        // Animate logo
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.splashscreen_logo);
        mLogoContainer.clearAnimation();
        mLogoContainer.setAnimation(localAnimation);
        mLogoContainer.startAnimation(localAnimation);

        if (mShimmerContainer != null) {
            mShimmerContainer.setDuration(900);
            mShimmerContainer.setBaseAlpha(0.65f);
            mShimmerContainer.startShimmerAnimation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (shouldImportData(mPrefs.getLastDownloadDate(), isNetworkAvailable(this))) {
            mSubscription = AppObservable.bindActivity(this, mImportService.importData())
                    .subscribeOn(Schedulers.io())
                    .subscribe(r -> onAfterDataImported(true), throwable -> onImportError());
        } else {
            onAfterDataImported(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ErrorDialogFragment dialog = (ErrorDialogFragment) getSupportFragmentManager().findFragmentByTag(ErrorDialogFragment.TAG);
        if (dialog != null) {
            dialog.dismiss();
        }

        super.onSaveInstanceState(outState);
    }

    /**
     * DO NOT download latest data if it was already downloaded less than a day ago,
     * OR if user has no Internet connection and has already some data.
     */
    private boolean shouldImportData(long lastDownloadDate, boolean internetEnabled) {
        boolean importData = true;

        if (lastDownloadDate > 0) {
            if (internetEnabled) {
                long now = new Date().getTime();
                importData = (now - lastDownloadDate) > DOWNLOAD_DATA_INTERVAL;
            } else {
                importData = false;
            }
        }
        return importData;
    }

    /**
     * Verifies data, then directs to the appropriate activity.
     */
    private void onAfterDataImported(boolean saveDownloadDate) {
        boolean hasPlayServices = hasGooglePlayServices(this);

        unsubscribeSubscription();
        mSubscription = AppObservable.bindActivity(this, mCheckDataService.checkData(this))
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    Boolean hasValidData = result.first;
                    City city = result.second;

                    if (hasValidData) {
                        if (saveDownloadDate) {
                            Timber.d("Keep data download date");
                            mPrefs.setDownloadDate();
                        }

                        if (city == null) {
                            // First time the application is launched
                            CitiesMapActivity.launch(this, hasPlayServices);
                        } else {
                            // If user has already selected a city, or we found his nearest city
                            // via his location, directs the user to the baggers list for this city.
                            BaggersListActivity.launch(this, city);
                        }
                    } else {
                        onImportError();
                    }
                }, throwable -> onImportError());
    }

    private void onImportError() {
        Timber.w("Network is unreachable or data is corrupted");
        deleteDatabase(Database.NAME);
        mPrefs.reset();
        ErrorDialogFragment.create(!isNetworkAvailable(this)).show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }
}
