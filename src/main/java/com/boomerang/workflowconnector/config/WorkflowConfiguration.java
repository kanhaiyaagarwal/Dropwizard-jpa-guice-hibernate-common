package com.boomerang.workflowconnector.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * Created by kanhaiya on 20/09/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

//    public DataSourceFactory getDataSourceFactory() {
//        return dataSourceFactory;
//    }

    @Valid
    @NotNull
    private AzkabanClientConfiguration azkabanClientConfiguration = new AzkabanClientConfiguration();

    @Valid
    @NotNull
    private Properties quartz = new Properties();

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
