package com.nilhcem.bblfr.ui.baggers.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.jobs.baggers.BaggersService;
import com.nilhcem.bblfr.ui.BaseActivity;

import javax.inject.Inject;

public class BaggersSearchActivity extends BaseActivity {

    @Inject BaggersService mBaggersService;

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, BaggersSearchActivity.class);
        context.startActivity(intent);
    }

    public BaggersSearchActivity() {
        super(R.layout.baggers_search_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.baggers_search_toolbar_title);
    }
}
