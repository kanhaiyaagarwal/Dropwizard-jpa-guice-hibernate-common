package com.boomerang.workflowconnector;

import com.google.inject.AbstractModule;

/**
 * Created by kanhaiya on 21/09/16.
 */
public class WorkflowModule extends AbstractModule {
    @Override
    protected void configure() {
//        bind(SessionFactory.class).toProvider(hibernateBundle::getSessionFactory);
    }

//    public final HibernateBundle<WorkflowConfiguration> getHibernateBundle() {
//        return hibernateBundle;
//    }
//
//    public final GuiceBundle<WorkflowConfiguration> getGuiceBundle() {
//        return guiceBundleBuilder;
//    }
//
//    private final HibernateBundle<WorkflowConfiguration> hibernateBundle =
//            new ScanningHibernateBundle<WorkflowConfiguration>("com.boomerang.workflowconnector.model") {
//        @Override
//        public DataSourceFactory getDataSourceFactory(WorkflowConfiguration configuration) {
//            return configuration.getDataSourceFactory();
//        }
//    };
//
//    private final GuiceBundle<WorkflowConfiguration> guiceBundleBuilder = GuiceBundle.<WorkflowConfiguration>newBuilder()
//        .addModule(this)
//        .enableAutoConfig("com.boomerang.workflowconnector")
//        .setConfigClass(WorkflowConfiguration.class)
//            .build();
}
