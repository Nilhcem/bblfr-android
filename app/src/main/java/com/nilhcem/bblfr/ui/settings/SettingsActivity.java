package com.nilhcem.bblfr.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivity;

public class SettingsActivity extends NavigationDrawerActivity {

    public static Intent createLaunchIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
}
