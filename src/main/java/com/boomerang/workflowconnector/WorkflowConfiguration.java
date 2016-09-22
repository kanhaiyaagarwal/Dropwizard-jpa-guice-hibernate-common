package com.boomerang.workflowconnector;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;
import org.glassfish.jersey.client.ClientConfig;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by kanhaiya on 20/09/16.
 */
@Data
public class WorkflowConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

//    @Valid
//    @NotNull
//    private ClientConfig httpClient = new ClientConfig();
//
//    public ClientConfig getJerseyClientConfiguration() {
//        return httpClient.getConfiguration();
//    }
//



}
