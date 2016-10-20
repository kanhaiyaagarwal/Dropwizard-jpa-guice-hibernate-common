package com.boomerang.workflowconnector.requestresponse;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * Created by kanhaiya on 18/10/16.
 */
@Data
@JsonSerialize
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CreateQuartzScheduleRequest {

    @NotNull
    String projectName;

    @NotNull
    String schedule;
}
