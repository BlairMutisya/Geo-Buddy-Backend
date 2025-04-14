package com.GB.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImeiNotFoundException extends TrackerException {
    public ImeiNotFoundException(String imei) {
        super("IMEI_NOT_FOUND",
                String.format("IMEI %s is not registered in the system", imei));
    }
}