package com.boomerang.workflowconnector.config;

import com.boomerang.workflowconnector.util.StartHelper;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * Created by kanhaiya on 19/10/16.
 */
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new WorkflowModule());
        install(new JpaPersistModule(StartHelper.JPA_UNIT)
                .properties(StartHelper.createTestProperties()));
    }
}
