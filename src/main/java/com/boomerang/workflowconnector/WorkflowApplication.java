package com.boomerang.workflowconnector;

import com.boomerang.workflowconnector.resource.ProjectResource;
import com.boomerang.workflowconnector.util.StartHelper;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

/**
 * Created by kanhaiya on 20/09/16.
 */
@Slf4j
public class WorkflowApplication extends Application<WorkflowConfiguration>{

    private GuiceBundle<WorkflowConfiguration> guiceBundle;

    public static void main(String[] args)throws Exception {

        for (int i = 0; i < args.length; i++) {
            if (args[i].endsWith(".yaml")) {
                StartHelper.setConfigFilename(args[i]);
            }
        }
        new WorkflowApplication().run(args);
    }

//    final WorkflowModule guiceModule = new WorkflowModule();

    @Override
    public void initialize(Bootstrap<WorkflowConfiguration> bootstrap) {
        WorkflowConfiguration configuration = StartHelper.createConfiguration(StartHelper.getConfigFilename());
        Properties jpaProperties = StartHelper.createPropertiesFromConfiguration(configuration);

        JpaPersistModule jpaPersistModule = new JpaPersistModule(StartHelper.JPA_UNIT);
        jpaPersistModule.properties(jpaProperties);

        guiceBundle = GuiceBundle.<WorkflowConfiguration>newBuilder()
                .addModule(new WorkflowModule())
                .addModule(jpaPersistModule).enableAutoConfig("com.boomerang.workflowconnector")
                .setConfigClass(WorkflowConfiguration.class).build();

        bootstrap.addBundle(guiceBundle);

    }

    @Override
    public void run(WorkflowConfiguration workflowConfiguration, Environment environment) throws Exception {
        environment.servlets().addFilter("persistFilter", guiceBundle.getInjector()
                .getInstance(PersistFilter.class))
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        StartHelper.init(StartHelper.getConfigFilename());

        environment.jersey().register(guiceBundle.getInjector().getInstance(ProjectResource.class));

//        final ProjectDao dao = new ProjectDao(guiceModule.getHibernateBundle().getSessionFactory());
//        environment.jersey().register(new ProjectResource(dao));
    }
}
