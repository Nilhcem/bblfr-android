package com.nilhcem.bblfr;

import com.nilhcem.bblfr.ui.BaseActivityTest;

import dagger.Module;

@Module(
        injects = {
                BaseActivityTest.TestBaseActivity.class
        },
        complete = false,
        library = true
)
public class TestBBLModule {
}
