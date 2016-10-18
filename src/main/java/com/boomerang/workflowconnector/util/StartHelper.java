package com.boomerang.workflowconnector.util;

import com.boomerang.workflowconnector.config.WorkflowConfiguration;
import com.boomerang.workflowconnector.config.WorkflowModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StartHelper {

    @Getter
    public static final String JPA_UNIT = "WorkflowPersistanceUnit";
    private static String configFilename = "src/main/resources/config.yaml";

    public static String getConfigFilename() {
        return configFilename;
    }

    public static void setConfigFilename(String configFilename) {
        StartHelper.configFilename = configFilename;
    }

    public static Properties createPropertiesFromConfiguration(WorkflowConfiguration localConfiguration) {
        DataSourceFactory dbConfig = localConfiguration.getDataSourceFactory();

        Properties properties = new Properties();
        properties.put("hibernate.properties", dbConfig.getProperties());
        properties.put("hbm2ddl.auto", dbConfig.getProperties().get("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", dbConfig.getProperties().get("hibernate.dialect"));
        properties.put("hibernate.connection.driver_class", dbConfig.getDriverClass());
        properties.put("hibernate.connection.url", dbConfig.getUrl());
        properties.put("hibernate.connection.username", dbConfig.getUser());
        properties.put("hibernate.connection.password", dbConfig.getPassword());
        properties.put("hibernate.connection.pool_size", "15");
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.cache.use_query_cache", "false");
        properties.put("hibernate.connection.isolation", "2");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.format_sql", "true");
//        properties.put("org.hibernate.envers.audit_table_suffix", "_history");
//        properties.put("org.hibernate.envers.audit_table_prefix", "");

        return properties;
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
        return configuration;
    }


}
