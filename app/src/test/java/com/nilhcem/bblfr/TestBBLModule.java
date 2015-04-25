package com.nilhcem.bblfr;

import com.nilhcem.bblfr.ui.BaseActivityTest;
import com.nilhcem.bblfr.ui.baggers.list.filter.TagsListActivityTest;

import dagger.Module;

@Module(
        injects = {
                BaseActivityTest.TestBaseActivity.class,
                TagsListActivityTest.TestTagsListActivity.class
        },
        complete = false,
        library = true
)
public class TestBBLModule {
}
