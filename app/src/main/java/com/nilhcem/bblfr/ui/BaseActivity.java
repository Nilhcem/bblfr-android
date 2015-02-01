package com.nilhcem.bblfr.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nilhcem.bblfr.BBLApplication;

public abstract class BaseActivity extends ActionBarActivity {

    protected BBLApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = BBLApplication.get(this);
        mApplication.inject(this);
    }
}
