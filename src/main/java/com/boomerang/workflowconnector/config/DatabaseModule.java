package com.boomerang.workflowconnector.config;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

import java.util.Properties;

/**
 * Created by kanhaiya on 04/10/16.
 */
public class DatabaseModule extends AbstractModule {
    private Properties properties;
    private String JPA_UNIT;

    public DatabaseModule(Properties properties, String JPA_UNIT){
        super();
        this.properties = properties;
        this.JPA_UNIT = JPA_UNIT;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(JPA_UNIT).properties(properties));
        bind(WorkflowDatabaseHealthCheck.class);
    }

}
