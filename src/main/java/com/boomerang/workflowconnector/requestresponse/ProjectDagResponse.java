package com.boomerang.workflowconnector.requestresponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by kanhaiya on 28/09/16.
 */
@Data
@JsonSerialize
@JsonSnakeCase
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProjectDagResponse {

    Long id;

    String name;

    Boolean active;

    String description;

    Integer version = 1;

    List<ProjectFlowNodeResponse> projectFlowNodeRequestList;
}
