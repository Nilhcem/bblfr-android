package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;

import butterknife.InjectView;
import butterknife.Optional;
import icepick.Icepick;
import rx.Subscription;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;
    protected Subscription mSubscription;

    @Optional @InjectView(R.id.toolbar) protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        mApplication = BBLApplication.get(this);
    }

    @Override
    protected void onStop() {
        unsubscribe(mSubscription);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
