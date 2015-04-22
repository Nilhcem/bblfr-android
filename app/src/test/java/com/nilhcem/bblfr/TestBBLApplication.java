package com.nilhcem.bblfr;

public class TestBBLApplication extends BBLApplication {

    @Override
    Object[] getModules() {
        return new Object[]{new BBLModule(this), new TestBBLModule()};
    }
}
