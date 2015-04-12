package com.nilhcem.bblfr.core.db;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;

import ollie.Ollie;

public final class Database {

    public static final String NAME = "baggers.db";
    private static final int VERSION = 1;

    private Database() {
        throw new UnsupportedOperationException();
    }

    public static void init(Context context) {
        Ollie.with(context)
                .setName(NAME)
                .setVersion(VERSION)
                .setLogLevel(BuildConfig.DEBUG ? Ollie.LogLevel.FULL : Ollie.LogLevel.NONE)
                .init();
    }
}
