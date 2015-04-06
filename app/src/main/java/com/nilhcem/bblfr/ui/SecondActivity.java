package com.nilhcem.bblfr.ui;

import android.content.Context;
import android.content.Intent;

import com.nilhcem.bblfr.R;

public class SecondActivity extends BaseActivity {

    public SecondActivity() {
        super(R.layout.second_activity);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SecondActivity.class));
    }
}
