package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;

import butterknife.Bind;
import icepick.Icepick;
import rx.Subscription;

public abstract class BaseActivity extends AppCompatActivity {

    protected BBLApplication mApplication;
    protected Subscription mSubscription;

    @Nullable @Bind(R.id.toolbar) protected Toolbar mToolbar;

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
