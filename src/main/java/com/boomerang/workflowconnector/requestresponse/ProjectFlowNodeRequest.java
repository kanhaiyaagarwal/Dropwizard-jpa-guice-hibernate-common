package com.boomerang.workflowconnector.requestresponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kanhaiya on 26/09/16.
 */
@Data
@JsonSerialize
@JsonSnakeCase
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProjectFlowNodeRequest {

    @JsonIgnore
    Long id;

    @JsonIgnore
    Long projectId;

    String name;

    String url;

    HashMap<String, Object> attributes;

    Boolean isRoot = Boolean.FALSE;

    LocalTime startBy;

    List<String> ParentNodeNameList;
}
