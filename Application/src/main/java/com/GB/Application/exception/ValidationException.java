package com.GB.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends TrackerException {
    public ValidationException(String field, String error) {
        super("VALIDATION_ERROR",
                String.format("Invalid %s: %s", field, error));
    }

    public ValidationException(String error) {
        super("VALIDATION_ERROR", error);
    }
}