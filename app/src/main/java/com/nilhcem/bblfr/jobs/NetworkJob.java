package com.nilhcem.bblfr.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;

public abstract class NetworkJob extends Job {

    protected EventBus mBus = EventBus.getDefault();

    protected NetworkJob() {
        super(new Params(Priority.MIDDLE));
    }

    @Override
    public void onAdded() {
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
