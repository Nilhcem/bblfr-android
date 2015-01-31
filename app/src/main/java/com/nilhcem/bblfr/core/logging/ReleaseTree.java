package com.nilhcem.bblfr.core.logging;

import timber.log.Timber;

/**
 * A simple release tree that solely prints warning + error logs.
 */
public class ReleaseTree extends Timber.DebugTree {

    @Override
    public void v(String message, Object... args) {
        // Do not log
    }

    @Override
    public void v(Throwable t, String message, Object... args) {
        // Do not log
    }

    @Override
    public void d(String message, Object... args) {
        // Do not log
    }

    @Override
    public void d(Throwable t, String message, Object... args) {
        // Do not log
    }

    @Override
    public void i(String message, Object... args) {
        // Do not log
    }

    @Override
    public void i(Throwable t, String message, Object... args) {
        // Do not log
    }
}
