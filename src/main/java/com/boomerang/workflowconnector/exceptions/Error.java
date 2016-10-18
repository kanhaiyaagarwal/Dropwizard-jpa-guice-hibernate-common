package com.boomerang.workflowconnector.exceptions;

/**
 * Created by kanhaiya on 26/09/16.
 */
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error {

    private String code;
    private String message;

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
