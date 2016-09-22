package com.boomerang.workflowconnector.util;

import com.boomerang.workflowconnector.WorkflowConfiguration;
import com.boomerang.workflowconnector.WorkflowModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.Validation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by kanhaiya on 21/09/16.
 */
public class StartHelper {

    private static Injector injector = null;
    public static final String JPA_UNIT = "d1";
    public static final String CONFIG_FILENAME_TEST = "src/main/resources/config.yaml";
    private static String configFilename;

    public static String getConfigFilename() {
        return configFilename;
    }

    public static void setConfigFilename(String configFilename) {
        StartHelper.configFilename = configFilename;
    }

    public static Properties createPropertiesFromConfiguration(WorkflowConfiguration localConfiguration) {
        List<String> propertiesList = new ArrayList<>();
        propertiesList.add("hibernate.dialect");
        propertiesList.add("hibernate.show_sql");
        propertiesList.add("hibernate.hbm2ddl.auto");
        propertiesList.add("hibernate.dialect");
        propertiesList.add("hibernate.archive.autodetection");
        propertiesList.add("hibernate.connection.driver_class");
        propertiesList.add("hibernate.username");
        propertiesList.add("hibernate.password");

        Properties properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.url", localConfiguration.getDataSourceFactory().getUrl());
        properties.setProperty("javax.persistence.jdbc.user", localConfiguration.getDataSourceFactory().getUser());
        for (String p : propertiesList) {
            String val = localConfiguration.getDataSourceFactory().getProperties().get(p);
            if (val != null) {
                properties.setProperty(p, val);
            }
        }

        return properties;
    }

    public static Injector getInjector() {
        if (injector == null) {
            throw new RuntimeException("call StartHelper.init() first!");
        }
        return injector;
    }

    public static void init() {
        init(CONFIG_FILENAME_TEST);
    }

    public static void init(String localConfigFilename) {
        configFilename = localConfigFilename;
        WorkflowConfiguration configuration = createConfiguration(localConfigFilename);
        Properties properties = createPropertiesFromConfiguration(configuration);
        JpaPersistModule jpaPersistModule = new JpaPersistModule(JPA_UNIT);
        jpaPersistModule.properties(properties);
        injector = Guice.createInjector(jpaPersistModule, new WorkflowModule());
        injector.getInstance(PersistService.class).start();
    }

    public static WorkflowConfiguration createConfiguration(String configFilename) {
        ConfigurationFactory<WorkflowConfiguration> factory =
                new ConfigurationFactory<>(
                        WorkflowConfiguration.class,
                        Validation.buildDefaultValidatorFactory().getValidator(),
                        Jackson.newObjectMapper(),
                        ""
                );
        WorkflowConfiguration configuration;
        try {
            configuration = factory.build(new File(configFilename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(ToStringBuilder.reflectionToString(configuration, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(configuration.getDataSourceFactory(), ToStringStyle.MULTI_LINE_STYLE));
        return configuration;
    }

    public static <T> T getInstance(Class<T> c) {
        return getInjector().getInstance(c);
    }

}
