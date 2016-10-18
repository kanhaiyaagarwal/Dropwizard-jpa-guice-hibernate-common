package com.boomerang.workflowconnector.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by kanhaiya on 26/09/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private List<Error> errors = Lists.newArrayList();
    public ErrorResponse addError(Error error) {
        errors.add(error);
        return this;
    }
}
