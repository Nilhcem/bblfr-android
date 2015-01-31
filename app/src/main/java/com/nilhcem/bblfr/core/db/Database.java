package com.nilhcem.bblfr.core.db;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;

import ollie.Ollie;

public final class Database {

    private static final String DB_NAME = "baggers.db";
    private static final int DB_VERSION = 1;

    private Database() {
        throw new UnsupportedOperationException();
    }

    public static void init(Context context) {
        Ollie.with(context)
                .setName(DB_NAME)
                .setVersion(DB_VERSION)
                .setLogLevel(BuildConfig.DEBUG ? Ollie.LogLevel.FULL : Ollie.LogLevel.NONE)
                .init();
    }
}
