package com.boomerang.workflowconnector;

import com.boomerang.workflowconnector.config.DatabaseModule;
import com.boomerang.workflowconnector.config.WorkflowConfiguration;
import com.boomerang.workflowconnector.config.WorkflowModule;
import com.boomerang.workflowconnector.util.StartHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Stage;
import com.google.inject.persist.PersistFilter;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by kanhaiya on 20/09/16.
 */
@Slf4j
public class WorkflowApplication extends Application<WorkflowConfiguration> {

    private GuiceBundle<WorkflowConfiguration> guiceBundle;

    public static void main(String[] args)throws Exception {

        for (int i = 0; i < args.length; i++) {
            if (args[i].endsWith(".yaml")) {
                StartHelper.setConfigFilename(args[i]);
            }
        }
        new WorkflowApplication().run(args);
    }


    @Override
    public void initialize(Bootstrap<WorkflowConfiguration> bootstrap) {
        guiceBundle = createGuiceBundleWithJpa();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new SwaggerBundle<WorkflowConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(WorkflowConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(WorkflowConfiguration workflowConfiguration, Environment environment) throws Exception {

        environment.servlets().addFilter("persistFilter", guiceBundle.getInjector()
                .getInstance(PersistFilter.class))
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");


        //todo: move this to startuphelper or somewhere more consistent
        setUnirestObjectMapper();
    }

    public GuiceBundle<WorkflowConfiguration> createGuiceBundleWithJpa(){

        WorkflowConfiguration configuration = StartHelper.createConfiguration(StartHelper.getConfigFilename());

        return GuiceBundle.<WorkflowConfiguration>newBuilder()
                .addModule(new WorkflowModule())
                .addModule(new DatabaseModule(StartHelper.createPropertiesFromConfiguration(configuration),
                        StartHelper.getJPA_UNIT()))
                .enableAutoConfig("com.boomerang.workflowconnector",
                        "com.fiestacabin.dropwizard.quartz")
                .setConfigClass(WorkflowConfiguration.class).build(Stage.DEVELOPMENT);
    }

    private void setUnirestObjectMapper() {

        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
