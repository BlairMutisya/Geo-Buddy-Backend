package com.GB.Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ImeiExistsException extends TrackerException {
    public ImeiExistsException(String imei) {
        super("IMEI_ALREADY_EXISTS",
                String.format("IMEI %s is already registered to another device", imei));
    }
}