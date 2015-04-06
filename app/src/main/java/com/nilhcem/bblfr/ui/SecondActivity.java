package com.nilhcem.bblfr.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.internal.VersionUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.nilhcem.bblfr.R;

import java.util.List;

import icepick.Icicle;

public class SecondActivity extends BaseActivity {

    private static final String ACTIVITY_TRANSITION_NAME = "SecondActivity:mToolbar";

    public SecondActivity() {
        super(R.layout.second_activity);
    }

    @Icicle boolean mTransitionFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleActivityTransition();
    }

    @Override
    public void onBackPressed() {
        // Disable exit activity transition (to avoid a glitch when exiting on API 21+)
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleActivityTransition() {
        if (!mTransitionFlag && VersionUtils.isAtLeastL()) {
            // Postpone the transition until the window's decor view has finished its layout.
            postponeEnterTransition();

            ViewCompat.setTransitionName(mToolbar, ACTIVITY_TRANSITION_NAME);

            // Toolbar title should appear after activity transition ends.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    mTransitionFlag = true;
                }
            });

            final View decor = getWindow().getDecorView();
            decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    decor.getViewTreeObserver().removeOnPreDrawListener(this);
                    startPostponedEnterTransition();
                    return true;
                }
            });
        }
    }

    public static void launch(@NonNull Activity activity, @NonNull View sharedElement) {
        // Avoid blinking status+navigation bars in activity transition by setting them as shared elements
        // See http://stackoverflow.com/a/26748694/711950
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                Pair.create(sharedElement, ACTIVITY_TRANSITION_NAME),
                Pair.create(activity.findViewById(android.R.id.statusBarBackground), Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME),
                Pair.create(activity.findViewById(android.R.id.navigationBarBackground), Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));

        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElement, ACTIVITY_TRANSITION_NAME);
        Intent intent = new Intent(activity, SecondActivity.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
