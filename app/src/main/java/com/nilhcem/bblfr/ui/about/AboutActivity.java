package com.nilhcem.bblfr.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivity;

public class AboutActivity extends NavigationDrawerActivity {

    public static Intent createLaunchIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
    }
}
