package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import icepick.Icepick;
import rx.Subscription;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;
    protected Subscription mSubscription;
    private final int mLayoutResId;

    @Optional @InjectView(R.id.toolbar) protected Toolbar mToolbar;

    protected BaseActivity(int layoutResId) {
        mLayoutResId = layoutResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        mApplication = BBLApplication.get(this);
        mApplication.inject(this);

        if (mLayoutResId != 0) {
            setContentView(mLayoutResId);
            ButterKnife.inject(this);

            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
    }

    @Override
    protected void onStop() {
        unsubscribeSubscription();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void unsubscribeSubscription() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
