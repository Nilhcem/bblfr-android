package com.nilhcem.bblfr.jobs;

import android.app.Application;
import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilhcem.bblfr.BBLApplication;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public abstract class NetworkJob extends Job {

    @Inject protected EventBus mBus;
    @Inject protected OkHttpClient mClient;
    @Inject protected ObjectMapper mMapper;

    protected NetworkJob(Context context) {
        super(new Params(Priority.MIDDLE));
        BBLApplication.get(context).inject(this);
    }

    @Override
    public void onAdded() {
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
