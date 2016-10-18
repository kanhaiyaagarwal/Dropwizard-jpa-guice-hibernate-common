package com.boomerang.workflowconnector.exceptions;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by kanhaiya on 26/09/16.
 */
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException exception) {
        Status status = Status.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new Error(Status.BAD_REQUEST.toString(),exception.getMessage()));
        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}

