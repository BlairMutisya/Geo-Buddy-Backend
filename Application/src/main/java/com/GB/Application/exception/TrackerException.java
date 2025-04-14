package com.GB.Application.exception;

public class TrackerException extends RuntimeException {
    private final String errorCode;

    public TrackerException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}