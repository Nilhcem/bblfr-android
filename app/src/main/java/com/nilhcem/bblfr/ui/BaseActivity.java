package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.nilhcem.bblfr.BBLApplication;
import com.nilhcem.bblfr.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;
    private final int mLayoutResId;

    @Optional @InjectView(R.id.toolbar) protected Toolbar mToolbar;

    protected BaseActivity(int layoutResId) {
        mLayoutResId = layoutResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
