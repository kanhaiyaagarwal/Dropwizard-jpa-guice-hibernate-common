package com.boomerang.workflowconnector.exceptions;

/**
 * Created by kanhaiya on 26/09/16.
 */
public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException() {
        super();
    }

    public ResourceConflictException(String message) {
        super(message);
    }

    public ResourceConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceConflictException(Throwable cause) {
        super(cause);
    }

    protected ResourceConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
