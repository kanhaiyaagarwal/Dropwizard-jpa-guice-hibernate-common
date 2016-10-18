package com.boomerang.workflowconnector.config;

import com.boomerang.workflowconnector.internal.resource.ProjectResource;
import com.fiestacabin.dropwizard.quartz.SchedulerConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by kanhaiya on 21/09/16.
 */
@Slf4j
public class WorkflowModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ProjectResource.class).in(Singleton.class);
        bind(SchedulerConfiguration.class).toInstance(new SchedulerConfiguration("sandbox"));

    }

    @Provides
    @Singleton
    Scheduler provideScheduler(Provider<WorkflowConfiguration> provider) throws SchedulerException {
        return new StdSchedulerFactory(provider.get().getQuartz()).getScheduler();
    }

    @Provides
    @Singleton
    public ModelMapper provideModelMapper() {
        return new ModelMapper();
    }

    @Provides
    @Singleton
    public AzkabanClientConfiguration providesAzkabanClientConfiguration(Provider<WorkflowConfiguration> provider) {
        return provider.get().getAzkabanClientConfiguration();
    }
}