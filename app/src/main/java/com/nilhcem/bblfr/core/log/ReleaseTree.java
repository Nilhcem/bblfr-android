package com.nilhcem.bblfr.core.log;

import android.util.Log;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        // Only log WARN, ERROR, and WTF.
        if (priority > Log.INFO) {
            Log.println(priority, tag, message);
        }
    }
}
