package com.boomerang.workflowconnector.requestresponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by kanhaiya on 26/09/16.
 */
@Data
@JsonSerialize
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProjectRequest {

    @JsonIgnore
    Long id;

    @NotNull
    String name;

    @JsonIgnore
    Boolean active = Boolean.TRUE;

    String description;

    @JsonIgnore
    Integer version = 1;

    List<ProjectFlowNodeRequest> projectFlowNodeRequestList;
}
