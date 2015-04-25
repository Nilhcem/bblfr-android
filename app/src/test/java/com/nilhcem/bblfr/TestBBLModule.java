package com.nilhcem.bblfr;

import com.nilhcem.bblfr.ui.BaseActivityTest;
import com.nilhcem.bblfr.ui.baggers.list.filter.TagsListActivityTest;
import com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerActivityTest;

import dagger.Module;

@Module(
        injects = {
                BaseActivityTest.TestBaseActivity.class,
                TagsListActivityTest.TestTagsListActivity.class,
                NavigationDrawerActivityTest.TestNavigationDrawerActivity.class
        },
        complete = false,
        library = true
)
public class TestBBLModule {
}
