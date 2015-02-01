package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nilhcem.bblfr.BBLApplication;

import butterknife.ButterKnife;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;
    private final int mLayoutResId;

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
        }
    }
}
