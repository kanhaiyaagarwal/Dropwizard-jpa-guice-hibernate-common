package com.boomerang.workflowconnector.config;

import com.boomerang.workflowconnector.internal.repositories.*;
import com.boomerang.workflowconnector.internal.repositories.impl.*;
import com.boomerang.workflowconnector.internal.resource.CallbackResource;
import com.boomerang.workflowconnector.internal.resource.ProjectResource;
import com.boomerang.workflowconnector.internal.resource.SchedularResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiestacabin.dropwizard.quartz.SchedulerConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.jackson.Jackson;
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
        bind(CallbackResource.class).in(Singleton.class);
        bind(SchedularResource.class).in(Singleton.class);
        bind(SchedulerConfiguration.class).toInstance(new SchedulerConfiguration("sandbox"));
        bind(INodeExecutionRepository.class).to(NodeExecutionRepository.class).in(Singleton.class);
        bind(IEdgeMappingRepository.class).to(EdgeMappingRepository.class).in(Singleton.class);
        bind(IEdgeMappingInstanceRepository.class).to(EdgeMappingInstanceRepository.class).in(Singleton.class);
        bind(IProjectExecutionRepository.class).to(ProjectExecutionRepository.class).in(Singleton.class);
        bind(IProjectFlowNodeRepository.class).to(ProjectFlowNodeRepository.class).in(Singleton.class);
        bind(IProjectRepository.class).to(ProjectRepository.class).in(Singleton.class);
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
    public ObjectMapper provideObjectMapper() {
        return  Jackson.newObjectMapper();
    }

    @Provides
    @Singleton
    public AzkabanClientConfiguration providesAzkabanClientConfiguration(Provider<WorkflowConfiguration> provider) {
        return provider.get().getAzkabanClientConfiguration();
    }
}